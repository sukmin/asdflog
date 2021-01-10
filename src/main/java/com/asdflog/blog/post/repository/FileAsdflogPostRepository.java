package com.asdflog.blog.post.repository;

import com.asdflog.blog.post.exception.AsdflogPostFileException;
import com.asdflog.blog.post.exception.AsdflogPostNotExistException;
import com.asdflog.blog.post.model.AsdflogPost;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.IdGenerator;

@Slf4j
@Repository
public class FileAsdflogPostRepository implements AsdflogPostRepository {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyyMMddHHmmss");

  private final Gson gson;
  private final IdGenerator idGenerator;
  private final String savePath;

  @Autowired
  public FileAsdflogPostRepository(Gson gson, IdGenerator idGenerator,
      @Value("${asdflog.post.save.path}") String savePath) {
    this.gson = gson;
    this.idGenerator = idGenerator;
    this.savePath = savePath;

    checkSaveDirectoryAndCreate();
  }

  private void checkSaveDirectoryAndCreate() {
    LOGGER.info("savePath is {}", savePath);
    Path saveDirectoryPath = Path.of(savePath);
    if (Files.notExists(saveDirectoryPath)) {
      try {
        Files.createDirectories(saveDirectoryPath);
      } catch (Exception exception) {
        throw new AsdflogPostFileException(saveDirectoryPath, exception);
      }
    }

    if (!Files.isDirectory(saveDirectoryPath)) {
      throw new IllegalStateException(savePath + " is not directory.");
    }
  }

  @Override
  public String addAsdflogPost(AsdflogPost asdflogPost) {
    LocalDateTime createDate = asdflogPost.getCreateTime();
    UUID uuid = idGenerator.generateId();
    String id = createId(createDate, uuid);
    asdflogPost.setId(id);

    Path filePath = idToFilePath(id);
    String data = gson.toJson(asdflogPost);
    try {
      Files.writeString(filePath, data);
    } catch (IOException exception) {
      throw new AsdflogPostFileException(filePath, exception);
    }

    return id;
  }

  @Override
  public AsdflogPost getAsdflogPost(String id) {
    Path filePath = idToFilePath(id);
    if (Files.notExists(filePath)) {
      throw new AsdflogPostNotExistException(id, filePath);
    }

    try {
      String data = Files.readString(filePath);
      return gson.fromJson(data, AsdflogPost.class);
    } catch (IOException exception) {
      throw new AsdflogPostFileException(filePath, exception);
    }
  }

  @Override
  public List<AsdflogPost> getAllAsdflogPost() {
    Path saveDirectory = Path.of(savePath);
    List<Path> paths;
    try {
      paths = Files.list(saveDirectory).collect(Collectors.toList());
    } catch (IOException exception) {
      throw new AsdflogPostFileException(saveDirectory, exception);
    }

    List<AsdflogPost> asdflogPosts = new ArrayList<>();
    for (Path filePath : paths) {
      try {
        String data = Files.readString(filePath);
        AsdflogPost asdflogPost = gson.fromJson(data, AsdflogPost.class);
        asdflogPosts.add(asdflogPost);
      } catch (IOException exception) {
        throw new AsdflogPostFileException(filePath, exception);
      }
    }
    return asdflogPosts;
  }

  @Override
  public void modifyAsdflogPost(AsdflogPost asdflogPost) {
    String id = asdflogPost.getId();
    Path filePath = idToFilePath(id);
    if (Files.notExists(filePath)) {
      throw new AsdflogPostNotExistException(id, filePath);
    }

    try {
      LocalDateTime createTime = getCreateTime(filePath);
      asdflogPost.setCreateTime(createTime);
      String data = gson.toJson(asdflogPost);

      Files.delete(filePath);
      Files.writeString(filePath, data);
    } catch (IOException exception) {
      throw new AsdflogPostFileException(filePath, exception);
    }
  }

  private LocalDateTime getCreateTime(Path filePath) throws IOException {
    String beforeData = Files.readString(filePath);
    AsdflogPost beforeAsdflogPost = gson.fromJson(beforeData, AsdflogPost.class);
    return beforeAsdflogPost.getCreateTime();
  }

  @Override
  public void removeAsdflogPost(String id) {
    Path filePath = idToFilePath(id);
    if (Files.notExists(filePath)) {
      throw new AsdflogPostNotExistException(id, filePath);
    }

    try {
      Files.delete(filePath);
    } catch (IOException exception) {
      throw new AsdflogPostFileException(filePath, exception);
    }
  }

  private String createId(LocalDateTime createDate, UUID uuid) {
    return createDate.format(DATE_TIME_FORMATTER) + "-" + uuid.toString();
  }

  private Path idToFilePath(String id) {
    return Path.of(String.format("%s/%s.json", savePath, id));
  }
}

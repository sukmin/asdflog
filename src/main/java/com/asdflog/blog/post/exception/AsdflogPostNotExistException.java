package com.asdflog.blog.post.exception;

import java.nio.file.Path;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AsdflogPostNotExistException extends RuntimeException {

  private final String id;
  private final Path filePath;

  public AsdflogPostNotExistException(String id, Path filePath) {
    this.id = id;
    this.filePath = filePath;
  }

}

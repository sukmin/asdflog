package com.asdflog.blog.post.exception;

import java.nio.file.Path;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AsdflogPostFileException extends RuntimeException {

  private final Path filePath;

  public AsdflogPostFileException(Path filePath, Throwable throwable) {
    super(throwable);
    this.filePath = filePath;
  }

}

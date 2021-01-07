package com.asdflog.blog.article.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Article {

  private String id;
  private String title;
  private String body;
  private LocalDateTime createTime;

  public Article() {
  }

  public Article(String title, String body, LocalDateTime createTime) {
    this.title = title;
    this.body = body;
    this.createTime = createTime;
  }

}

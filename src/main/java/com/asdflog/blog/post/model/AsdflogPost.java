package com.asdflog.blog.post.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AsdflogPost {

  private String id;
  private String title;
  private String body;
  private LocalDateTime createTime;

  private AsdflogPost() {
  }

  public static AsdflogPost newAsdflogPost(String title, String body, LocalDateTime createTime) {
    AsdflogPost asdflogPost = new AsdflogPost();
    asdflogPost.title = title;
    asdflogPost.body = body;
    asdflogPost.createTime = createTime;
    return asdflogPost;
  }

  public static AsdflogPost modifyAsdflogPost(String id, String title, String body) {
    AsdflogPost asdflogPost = new AsdflogPost();
    asdflogPost.id = id;
    asdflogPost.title = title;
    asdflogPost.body = body;
    return asdflogPost;
  }

}

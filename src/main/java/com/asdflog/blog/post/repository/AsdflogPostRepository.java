package com.asdflog.blog.post.repository;

import com.asdflog.blog.post.model.AsdflogPost;
import java.util.List;

public interface AsdflogPostRepository {

  String addAsdflogPost(AsdflogPost asdflogPost);
  AsdflogPost getAsdflogPost(String id);
  List<AsdflogPost> getAllAsdflogPost();
  void modifyAsdflogPost(AsdflogPost asdflogPost);
  void removeAsdflogPost(String id);

}

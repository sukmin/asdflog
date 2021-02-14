package com.asdflog.blog.post.repository;

import com.asdflog.blog.post.model.AsdflogPost;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Disabled("로컬에서 기능 테스트용도")
class FileAsdflogPostRepositoryTest {

  @Autowired
  private FileAsdflogPostRepository fileAsdflogPostRepository;

  @Test
  void addAsdflogPost() {
    // given
    AsdflogPost asdflogPost = AsdflogPost.newAsdflogPost("테스트타이틀", "테스트내용", LocalDateTime.now());

    // when
    String id = fileAsdflogPostRepository.addAsdflogPost(asdflogPost);

    // then
    LOGGER.info(id);
  }

  @Test
  void getAsdflogPost() {
    // given
    AsdflogPost asdflogPost = AsdflogPost.newAsdflogPost("테스트타이틀", "테스트내용", LocalDateTime.now());
    String id = fileAsdflogPostRepository.addAsdflogPost(asdflogPost);

    // when
    AsdflogPost asdflogPos1t = fileAsdflogPostRepository.getAsdflogPost(id);

    // then
    LOGGER.info("id:{}, result:{}", id, asdflogPos1t);
  }

  @Test
  void getAllAsdflogPost() {
    // given
    AsdflogPost asdflogPost = AsdflogPost.newAsdflogPost("테스트타이틀", "테스트내용", LocalDateTime.now());
    String id = fileAsdflogPostRepository.addAsdflogPost(asdflogPost);

    // when
    List<AsdflogPost> asdflogPostList = fileAsdflogPostRepository.getAllAsdflogPost();

    // then
    LOGGER.info(asdflogPostList.toString());
  }

  @Test
  void modifyAsdflogPost() {
    // given
    AsdflogPost asdflogPost = AsdflogPost.newAsdflogPost("테스트타이틀", "테스트내용", LocalDateTime.now());
    String id = fileAsdflogPostRepository.addAsdflogPost(asdflogPost);
    AsdflogPost modifyAsdflogPost = AsdflogPost.modifyAsdflogPost(id, "수정타이틀", "수정내용");

    // when
    fileAsdflogPostRepository.modifyAsdflogPost(modifyAsdflogPost);

    // then
    AsdflogPost changeAsdflogPost = fileAsdflogPostRepository.getAsdflogPost(id);
    LOGGER.info(changeAsdflogPost.toString());
  }

  @Test
  void removeAsdflogPost() {
    // given
    AsdflogPost asdflogPost = AsdflogPost.newAsdflogPost("테스트타이틀", "테스트내용", LocalDateTime.now());
    String id = fileAsdflogPostRepository.addAsdflogPost(asdflogPost);
    LOGGER.info(id);

    // when
    fileAsdflogPostRepository.removeAsdflogPost(id);

    // then
    fileAsdflogPostRepository.getAsdflogPost(id);
  }
}
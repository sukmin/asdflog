package com.asdflog.blog.article.repository;

import com.asdflog.blog.article.model.Article;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.IdGenerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class MemoryArticleRepository implements ArticleRepository {

  private final ConcurrentHashMap<String, Article> articles = new ConcurrentHashMap<>();

  private final IdGenerator idGenerator;

  @Autowired
  public MemoryArticleRepository(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  @Override
  public Mono<String> insertArticle(Article article) {
    return Mono.just(article).map(oneArticle -> {
      String id = idGenerator.generateId().toString();
      oneArticle.setId(id);
      articles.put(id, oneArticle);
      LOGGER.info("insertArticle. article:{}", oneArticle);
      return oneArticle.getId();
    });
  }

  @Override
  public Flux<Article> selectArticles() {
    return Flux.fromIterable(articles.values());
  }
}

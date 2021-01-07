package com.asdflog.blog.article.repository;

import com.asdflog.blog.article.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleRepository {

  Mono<String> insertArticle(Article article);
  Flux<Article> selectArticles();

}

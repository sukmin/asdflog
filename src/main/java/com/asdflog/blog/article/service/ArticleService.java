package com.asdflog.blog.article.service;

import com.asdflog.blog.article.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleService {

  Mono<String> add(Mono<Article> article);

  Flux<Article> getAll();
}

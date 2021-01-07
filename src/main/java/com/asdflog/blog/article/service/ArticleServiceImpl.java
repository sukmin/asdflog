package com.asdflog.blog.article.service;

import com.asdflog.blog.article.model.Article;
import com.asdflog.blog.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public Mono<String> add(Mono<Article> article) {
    return article.flatMap(articleRepository::insertArticle);
  }

  @Override
  public Flux<Article> getAll() {
    return articleRepository.selectArticles();
  }
}

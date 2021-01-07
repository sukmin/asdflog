package com.asdflog.blog.article.router;

import com.asdflog.blog.article.model.Article;
import com.asdflog.blog.article.service.ArticleService;
import java.time.LocalDateTime;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class ArticleRouterConfig {

  public final ArticleService articleService;

  @Autowired
  public ArticleRouterConfig(ArticleService articleService) {
    this.articleService = articleService;
  }

  @Bean
  public RouterFunction<ServerResponse> articleRouter() {
    return RouterFunctions
        .nest(RequestPredicates.path("/articles"),
            RouterFunctions.route(RequestPredicates.GET(""), this::getAll)
                .andRoute(RequestPredicates.POST(""), this::add));
  }

  public Mono<ServerResponse> add(final ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(articleService.add(request.formData().map(
            map -> new Article(map.getFirst("title"), map.getFirst("body"), LocalDateTime.now()))),
            String.class);
  }

  public Mono<ServerResponse> getAll(final ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(articleService.getAll(), Article.class);
  }

}

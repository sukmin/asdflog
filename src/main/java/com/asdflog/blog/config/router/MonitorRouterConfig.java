package com.asdflog.blog.config.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MonitorRouterConfig {

  private static final String OK = "OK";

  @Bean
  public RouterFunction<ServerResponse> monitorRouter() {
    return RouterFunctions
        .route(RequestPredicates.GET("/monitor/l7check"), serverRequest ->
            ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .cacheControl(CacheControl.noStore())
                .body(BodyInserters.fromValue(OK)));
  }

  @Bean
  public RouterFunction<ServerResponse> errorRouter() {
    return RouterFunctions
        .route(RequestPredicates.GET("/monitor/error"), serverRequest -> {
          throw new RuntimeException("force error");
        });
  }

}

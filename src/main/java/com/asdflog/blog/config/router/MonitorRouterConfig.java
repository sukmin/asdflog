package com.asdflog.blog.config.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class MonitorRouterConfig {

  @Bean
  public RouterFunction<ServerResponse> monitorRouter() {
    return RouterFunctions
        .route(RequestPredicates.GET("/monitor/l7check"), MonitorHandler::ok);
  }

  static class MonitorHandler {

    private static final String OK = "OK";

    public static Mono<ServerResponse> ok(final ServerRequest request) {
      return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
          .cacheControl(CacheControl.noStore())
          .body(BodyInserters.fromValue(OK));
    }
  }

}

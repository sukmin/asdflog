package com.asdflog.blog.config.router;

import com.asdflog.blog.sample.handler.SampleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SampleRouterConfig {

  @Bean
  public RouterFunction<ServerResponse> sampleRoute(final SampleHandler sampleHandler) {
    return RouterFunctions
        .route(RequestPredicates.GET("/hello")
            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), sampleHandler::hello);
  }

}

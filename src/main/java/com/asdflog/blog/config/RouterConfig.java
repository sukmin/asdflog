package com.asdflog.blog.config;

import com.asdflog.blog.common.handler.MonitorHandler;
import com.asdflog.blog.sample.SampleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> route(final SampleHandler sampleHandler) {
    return RouterFunctions
        .route(RequestPredicates.GET("/hello")
            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), sampleHandler::hello);
  }

  @Bean
  public RouterFunction<ServerResponse> monitorRouter() {
    return RouterFunctions
        .route(RequestPredicates.GET("/monitor/l7check"), MonitorHandler::ok);
  }

}

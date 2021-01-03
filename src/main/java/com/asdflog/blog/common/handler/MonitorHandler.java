package com.asdflog.blog.common.handler;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class MonitorHandler {

  private static final String OK = "OK";

  public static Mono<ServerResponse> ok(final ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
        .cacheControl(CacheControl.noStore())
        .body(BodyInserters.fromValue(OK));
  }

}

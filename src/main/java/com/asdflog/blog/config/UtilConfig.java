package com.asdflog.blog.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

@Configuration
public class UtilConfig {

  @Bean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public IdGenerator idGenerator() {
    return new AlternativeJdkIdGenerator();
  }

}

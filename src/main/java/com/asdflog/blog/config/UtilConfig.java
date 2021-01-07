package com.asdflog.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

@Configuration
public class UtilConfig {

  @Bean
  public IdGenerator alternativeJdkIdGenerator() {
    return new AlternativeJdkIdGenerator();
  }

}

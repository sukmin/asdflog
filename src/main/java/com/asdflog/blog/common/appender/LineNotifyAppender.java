package com.asdflog.blog.common.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class LineNotifyAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

  private static final String SYSTEM_PROPERTY_LINE_NOTIFY_TOKEN = "asdflog.line.notify.token";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss SSS");
  private static final int LINE_NOTIFY_MESSAGE_MAX = 1000;

  private final WebClient webClient;


  public LineNotifyAppender() {

    String lineNotifyToken = System.getProperty(SYSTEM_PROPERTY_LINE_NOTIFY_TOKEN);
    if (StringUtils.isBlank(lineNotifyToken)) {
      throw new IllegalStateException(
          "In 'alpha','real' profile, VM option '-D" + SYSTEM_PROPERTY_LINE_NOTIFY_TOKEN
              + "' is required. " + SYSTEM_PROPERTY_LINE_NOTIFY_TOKEN + " is " + lineNotifyToken
              + ".");
    }

    this.webClient = WebClient.builder()
        .baseUrl("https://notify-api.line.me")
        .defaultHeader(HttpHeaders.AUTHORIZATION,
            "Bearer " + lineNotifyToken)
        .build();
  }

  @Override
  protected void append(ILoggingEvent iLoggingEvent) {
    var fullMessage = iLoggingEventToString(iLoggingEvent);
    var message = StringUtils.left(fullMessage, LINE_NOTIFY_MESSAGE_MAX);

    try {
      //"https://notify-api.line.me/api/notify"
      webClient.post()
          .uri("/api/notify")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(BodyInserters.fromFormData("message", message))
          .retrieve().bodyToMono(String.class).timeout(Duration.ofSeconds(3)).block();
    } catch (Exception exception) {
      System.out.println("line notify error : " + exception.getMessage());
    }
  }

  private static String iLoggingEventToString(ILoggingEvent iLoggingEvent) {
    var time = LocalDateTime.ofInstant(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp()),
        TimeZone.getDefault().toZoneId()).format(DATE_TIME_FORMATTER);
    var message = iLoggingEvent.getMessage();
    var exception = ThrowableProxyUtil.asString(iLoggingEvent.getThrowableProxy());

    return "\ntime:" + time + "\n" +
        "message:" + message + "\n" +
        "exception:" + exception;
  }

}

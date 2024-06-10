package com.example.shop.kafka;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.function.Consumer;
import org.slf4j.Logger;

@Configuration
public class KafkaBeansConfiguration
{
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup()
                                                                            .lookupClass());

  @Bean
  public Consumer<HttpTraceMessage> httpTraceRegistered()
  {
    return (msg) -> {
      LOGGER.info("Trace registered {}",msg);
    };
  }
}

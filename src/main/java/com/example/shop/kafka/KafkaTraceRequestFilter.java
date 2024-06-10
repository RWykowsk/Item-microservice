package com.example.shop.kafka;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.Include;
import org.springframework.boot.actuate.web.exchanges.servlet.HttpExchangesFilter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class KafkaTraceRequestFilter extends OncePerRequestFilter
{

  private final StreamBridge streamBridge;
  public KafkaTraceRequestFilter(StreamBridge streamBridge) {
    super();
    this.streamBridge = streamBridge;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain filterChain) throws
                                                                 ServletException,
                                                                 IOException
  {
    streamBridge.send("httpTraceRegisteredProducer-out-0",new HttpTraceMessage(request.getServletPath(), request.getMethod()));
    filterChain.doFilter(request, response);
  }
}

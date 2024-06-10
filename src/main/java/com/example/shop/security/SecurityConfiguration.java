package com.example.shop.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{

  @Bean
  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder,
                                                       @Value("${spring.security.user.name}") String userName,
                                                       @Value("${spring.security.user.password}") String password)
  {
    UserDetails user = User.withUsername(userName)
                           .password(passwordEncoder.encode(password))
                           .roles("USER")
                           .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws
                                                            Exception
  {
    return http.authorizeHttpRequests(request -> request.anyRequest()
                                                        .authenticated())
               .httpBasic(Customizer.withDefaults())
               .csrf(AbstractHttpConfigurer::disable)
               .build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}

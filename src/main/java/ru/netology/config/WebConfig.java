package ru.netology.config;

import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class WebConfig {

  @Bean
  public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    final var bean = new RequestMappingHandlerAdapter();
    bean.getMessageConverters().add(new GsonHttpMessageConverter());
    return bean;
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}

package com.hope.root.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceWebConfig implements WebMvcConfigurer  {
  final Environment environment;

  public ResourceWebConfig(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry
      .addResourceHandler("/src/content/**")
      .addResourceLocations("classpath:/");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**");
  }
}

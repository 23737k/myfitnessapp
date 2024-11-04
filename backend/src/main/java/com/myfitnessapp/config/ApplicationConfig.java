package com.myfitnessapp.config;

import com.myfitnessapp.utils.Bootstrap;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("MyFitnessApp")
            .version("v1.0")
            .description("A REST api for exercise and workout tracking"));
  }

  @Bean
  RestTemplate restTemplate(RestTemplateBuilder builder){
    return builder.build();
  }

  //Bootstrap
  @Bean
  public CommandLineRunner commandLineRunner (Bootstrap bootstrap){
    return args -> {
      //Carga inicial de la bd
      bootstrap.init();
    };
  }

}

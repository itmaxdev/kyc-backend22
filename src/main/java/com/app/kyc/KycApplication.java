package com.app.kyc;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.kyc.service.storage.FileStorageService;

@SpringBootApplication
public class KycApplication implements CommandLineRunner
{

   @Value("${spring.allow.cors.origins:*}")
   String allowOriginsConfig;

   @Resource
   FileStorageService storageService;

   public static void main(String[] args)
   {
      SpringApplication.run(KycApplication.class, args);
   }

   @Override
   public void run(String... arg) throws Exception
   {
      storageService.deleteAll();
      storageService.init();
   }

   @Bean
   public WebMvcConfigurer corsConfigurer()
   {
      return new WebMvcConfigurer()
      {
         @Override
         public void addCorsMappings(CorsRegistry registry)
         {
            registry.addMapping("/**").allowedOrigins(allowOriginsConfig).allowedMethods("*");
         }
      };
   }

}

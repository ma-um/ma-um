package com.spuit.maum.diaryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DiaryServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiaryServerApplication.class, args);
  }

}

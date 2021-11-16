package com.spuit.maum.musicserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MusicServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MusicServerApplication.class, args);
  }

}

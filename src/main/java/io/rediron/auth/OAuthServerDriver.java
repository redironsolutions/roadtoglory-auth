package io.rediron.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OAuthServerDriver {

    public static void main(String... args){
        SpringApplication.run(OAuthServerDriver.class, args);
    }

}
package io.rediron.auth.registered;

import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;

public interface Client {

    void configure(ClientDetailsServiceBuilder config);

}
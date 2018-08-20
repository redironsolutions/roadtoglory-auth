package io.rediron.auth.registered;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.stereotype.Component;

@Component
// TODO: Remove this after testing
public class ExampleClient implements Client {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceBuilder config) {
        config.withClient("clientid")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .resourceIds("roadtoglory");
    }

}

package io.rediron.auth.service;

import io.rediron.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;

    // TODO: Possibly add roles here?

    public UserSummary(User user){
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail());
    }

}

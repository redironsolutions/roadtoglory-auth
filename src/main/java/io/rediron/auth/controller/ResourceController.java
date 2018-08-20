package io.rediron.auth.controller;

import io.rediron.auth.domain.User;
import io.rediron.auth.service.CurrentUser;
import io.rediron.auth.service.OAuthUserDetailsService;
import io.rediron.auth.service.UserPrincipal;
import io.rediron.auth.service.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domain")
public class ResourceController {

    @Autowired
    private OAuthUserDetailsService userService;

    @RequestMapping(value ="/user", method = RequestMethod.GET)
    public UserSummary getUser(@CurrentUser UserPrincipal user){
        // Todo: Use this with @CurrentUser when @CurrentUser works correctly.
        //return new UserSummary(user);

        // until above works, use this implementation
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        } else {
            UserPrincipal principal = userService.loadUserByUsername(authentication.getPrincipal().toString());
            return new UserSummary(principal);
        }

        // Based on spring, also doesnt work.
        //UserPrincipal principal = authentication == null ? null : (UserPrincipal) authentication.getPrincipal();
        //return new UserSummary(principal);
    }

    /*@RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }*/

}
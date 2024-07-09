package com.userservice.oauth.controller;

import com.userservice.oauth.config.TokenValidator;
import com.userservice.oauth.model.AuthToken;
import com.userservice.oauth.model.LoginUser;
import com.userservice.oauth.model.User;
import com.userservice.oauth.model.UserDto;
import com.userservice.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserIdentificationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenValidator jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }



    @PreAuthorize("hasRole('ROOT')")
    @RequestMapping(value="/check-root-user", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Root Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/check-valid-user", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }

}

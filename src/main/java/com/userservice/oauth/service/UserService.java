package com.userservice.oauth.service;

import com.userservice.oauth.model.User;
import com.userservice.oauth.model.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}

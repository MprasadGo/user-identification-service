package com.userservice.oauth.service;

import com.userservice.oauth.model.Role;

public interface RoleService {
    Role findByName(String name);
}

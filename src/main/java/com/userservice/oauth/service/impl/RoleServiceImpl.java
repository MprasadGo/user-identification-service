package com.userservice.oauth.service.impl;

import com.userservice.oauth.repository.RoleRepository;
import com.userservice.oauth.model.Role;
import com.userservice.oauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
}

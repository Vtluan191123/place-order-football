package com.vtluan.place_order_football.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Role;
import com.vtluan.place_order_football.repository.RoleRepository;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Optional<Role> getRoleById(long id) {
        return this.repository.findById(id);
    }
}

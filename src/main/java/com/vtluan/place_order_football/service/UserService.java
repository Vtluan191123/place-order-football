package com.vtluan.place_order_football.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.Spec.SpecUser;
import com.vtluan.place_order_football.model.Role;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.FilterUser;
import com.vtluan.place_order_football.model.dto.request.ReqUser;
import com.vtluan.place_order_football.model.dto.response.ResUser;
import com.vtluan.place_order_football.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public Page<Users> getAllUsers(FilterUser filterUser) {

        Pageable pageable = PageRequest.of(filterUser.getPage() - 1, filterUser.getSize());
        Specification spec = SpecUser.searchFieldName(filterUser.getName());
        return this.userRepository.findAll(spec, pageable);
    }

    public Optional<Users> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public Users setUser(Users user) {

        return this.userRepository.save(user);
    }

    public Users saveOrUpdate(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Boolean exitsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public Users getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Users tranferReqUserToUser(ReqUser reqUser) {

        Users user = new Users();
        Optional<Role> role = this.roleService.getRoleById(reqUser.getRole());
        user.setRole(role.get());
        user.setEmail(reqUser.getEmail());
        user.setImage("default_avt.png");
        user.setName(reqUser.getName());
        user.setPassword(reqUser.getPassword());
        user.setPhoneNumber(reqUser.getPhoneNumber());

        return user;
    }

    public Users getUserByRefreshTokenAndEmail(String rfTojen, String email) {
        return this.userRepository.findByEmailAndRefreshToken(email, rfTojen);
    }
}

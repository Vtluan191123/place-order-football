package com.vtluan.place_order_football.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Role;
import com.vtluan.place_order_football.model.Users;
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

    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
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
        user.setImage(reqUser.getImage());
        user.setName(reqUser.getName());
        user.setPassword(reqUser.getPassword());
        user.setPhoneNumber(reqUser.getPhoneNumber());

        return user;
    }

    public Users getUserByRefreshTokenAndEmail(String rfTojen, String email) {
        return this.userRepository.findByEmailAndRefreshToken(email, rfTojen);
    }

    public List<ResUser> tranferResUserToUser(List<Users> users) {

        List<ResUser> resUsers = new ArrayList<>();

        for (Users item : users) {
            ResUser resUser = new ResUser();
            resUser.setId(item.getId());
            resUser.setEmail(item.getEmail());
            resUser.setImage(item.getImage());
            resUser.setName(item.getName());
            resUser.setPhone_number(item.getPhoneNumber());
            resUser.setRoleName(item.getRole().getName());
            resUsers.add(resUser);
        }
        return resUsers;
    }
}

package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    List<UserDTO> getUsersByRole(UserRole role);
    UserDTO updateUser(Long id, UserCreateRequest request);
    void deleteUser(Long id);
    void activateUser(Long id);
    void deactivateUser(Long id);
}

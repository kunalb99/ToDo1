package com.wiom.todo.service;

import com.wiom.todo.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    UserDto createUser(UserDto userDTO);
    UserDto updateUser(Long id, UserDto userDTO);
    void deleteUser(Long id);
}

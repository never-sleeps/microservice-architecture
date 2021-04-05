package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.UserRequestDto;
import com.microservicearchitecture.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> findAllUsers();

    UserResponseDto findUserById(Long id);

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto updateUserById(Long id, UserRequestDto request);

    void deleteUserById(Long id);
}

package com.playandhold.user_service.service;

import com.playandhold.user_service.dto.CreateUserRequest;
import com.playandhold.user_service.dto.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(UUID userId);

    UserResponse getUserByEmail(String email);

}

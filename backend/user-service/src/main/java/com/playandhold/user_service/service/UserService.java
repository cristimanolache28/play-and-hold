package com.playandhold.user_service.service;

import com.playandhold.user_service.dto.CreateUserRequest;
import com.playandhold.user_service.dto.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);


}

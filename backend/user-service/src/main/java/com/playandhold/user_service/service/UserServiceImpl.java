package com.playandhold.user_service.service;

import com.playandhold.user_service.dto.CreateUserRequest;
import com.playandhold.user_service.dto.UserResponse;
import com.playandhold.user_service.entity.User;
import com.playandhold.user_service.exception.EmailAlreadyExistsException;
import com.playandhold.user_service.exception.UserNotFoundException;
import com.playandhold.user_service.exception.UsernameAlreadyExistsException;
import com.playandhold.user_service.mapper.UserMapper;
import com.playandhold.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = userMapper.toEntity(request);
        User userSaved = userRepository.save(user);

        return userMapper.toDto(userSaved);

    }

    @Override
    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toDto(user);
    }


}

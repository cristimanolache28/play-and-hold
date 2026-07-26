package com.playandhold.user_service.mapper;

import com.playandhold.user_service.dto.CreateUserRequest;
import com.playandhold.user_service.dto.UserResponse;
import com.playandhold.user_service.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .gender(request.gender())
                .country(request.country())
                .city(request.city())
                .dateOfBirth(request.dateOfBirth())
                .build();
    }

    public UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getCountry(),
                user.getCity(),
                user.getDateOfBirth(),
                user.getRegisteredAt()
        );
    }
}





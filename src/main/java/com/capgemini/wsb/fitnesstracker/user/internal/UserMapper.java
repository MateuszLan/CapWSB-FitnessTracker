package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }
    BasicUserDto basicToDto(User user) {
        return new BasicUserDto(user.getId(), user.getFirstName(),
                user.getLastName());
    }

    BasicUserDtoEmail basicToDtoEmail(User user) {
        return new BasicUserDtoEmail(user.getId(), user.getEmail());
    }
    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}

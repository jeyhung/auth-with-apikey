package io.github.jeyhung.users.application;

import io.github.jeyhung.users.application.dto.UserCreateDto;
import io.github.jeyhung.users.application.dto.UserDto;

import java.util.Optional;

public interface UserService {
    UserDto findById(long id);

    Optional<UserDto> findByApikey(String apikey);

    String create(UserCreateDto userCreateDto);
}

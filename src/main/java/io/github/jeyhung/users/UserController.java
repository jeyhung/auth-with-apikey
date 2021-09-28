package io.github.jeyhung.users;

import io.github.jeyhung.users.application.UserService;
import io.github.jeyhung.users.application.dto.UserCreateDto;
import io.github.jeyhung.users.application.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @GetMapping(value = "/find-by-id/{id}")
    public UserDto findByApikey(@PathVariable Long id) {
        return userService.findById(id);
    }
}

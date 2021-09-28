package io.github.jeyhung;

import io.github.jeyhung.users.application.UserService;
import io.github.jeyhung.users.application.UserServiceImpl;
import io.github.jeyhung.users.application.dto.UserCreateDto;
import io.github.jeyhung.users.application.dto.UserDto;
import io.github.jeyhung.users.application.exception.UserNotFoundException;
import io.github.jeyhung.users.domain.UserRepository;
import io.github.jeyhung.users.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    private final long USER_ID = 1L;
    private final String API_KEY = "6adf738015be402faa709e106d1f4758";
    private final String FIRST_NAME = "Jeyhun";
    private final String LAST_NAME = "Gasimov";

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, modelMapper);
    }

    @Test
    public void findByIdThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findById(USER_ID);
        assertThrows(UserNotFoundException.class, () -> userService.findById(USER_ID));
    }

    @Test
    public void findByIdSuccessfully() {
        User user = User.builder()
                .id(USER_ID)
                .apikey(API_KEY)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .creationAt(LocalDateTime.now())
                .build();

        UserDto userDto = UserDto.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        doReturn(Optional.of(user))
                .when(userRepository)
                .findById(USER_ID);

        doReturn(userDto)
                .when(modelMapper)
                .map(user, UserDto.class);

        userDto = userService.findById(USER_ID);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
    }

    @Test
    public void findByApikeyThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findByApikey(API_KEY);
        assertEquals(Optional.empty(), userService.findByApikey(API_KEY));
    }

    @Test
    public void findByApikeySuccessfully() {
        User user = User.builder()
                .id(USER_ID)
                .apikey(API_KEY)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .creationAt(LocalDateTime.now())
                .build();

        UserDto userDto = UserDto.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        doReturn(Optional.of(user))
                .when(userRepository)
                .findByApikey(API_KEY);

        doReturn(userDto)
                .when(modelMapper)
                .map(user, UserDto.class);

        Optional<UserDto> optionalUserDto = userService.findByApikey(API_KEY);

        assertEquals(user.getId(), optionalUserDto.get().getId());
        assertEquals(user.getFirstName(), optionalUserDto.get().getFirstName());
        assertEquals(user.getLastName(), optionalUserDto.get().getLastName());
    }

    @Test
    public void createSuccessfully() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        doReturn(user)
                .when(modelMapper)
                .map(userCreateDto, User.class);

        doReturn(user)
                .when(userRepository)
                .save(user);

        String apikey = userService.create(userCreateDto);

        assertEquals(user.getApikey(), apikey);
    }
}
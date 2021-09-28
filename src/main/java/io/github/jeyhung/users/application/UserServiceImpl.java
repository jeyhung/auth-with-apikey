package io.github.jeyhung.users.application;

import io.github.jeyhung.users.application.dto.UserCreateDto;
import io.github.jeyhung.users.application.dto.UserDto;
import io.github.jeyhung.users.application.exception.UserNotFoundException;
import io.github.jeyhung.users.domain.UserRepository;
import io.github.jeyhung.users.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Optional<UserDto> findByApikey(String apikey) {
        Optional<User> optionalUser = userRepository.findByApikey(apikey);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return Optional.of(modelMapper.map(user, UserDto.class));
        }

        return Optional.empty();
    }

    @Override
    public String create(UserCreateDto userCreateDto) {
        User user = modelMapper.map(userCreateDto, User.class);
        user.setCreationAt(LocalDateTime.now());
        user.setApikey(UUID.randomUUID().toString().replaceAll("-", ""));

        userRepository.save(user);
        return user.getApikey();
    }
}

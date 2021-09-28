package io.github.jeyhung.users.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDto {
    @NotBlank(message = "First name is required!")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    private String lastName;
}

package com.example.lowes.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;

    @NotBlank(message = "user name should not be blank")
    @Size(min = 5, max = 50)
    private String name;

    @NotBlank
    @Email
    @Size(min = 10, max = 30)
    private String email;
}

package com.example.betrimex.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters long")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}

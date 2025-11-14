package com.example.betrimex.model.dto.response;

import com.example.betrimex.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String id;

    private String name;

    private String email;

    private String username;

    private Role role;
}

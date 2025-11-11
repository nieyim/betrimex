package com.example.betrimex.controller;

import com.example.betrimex.model.dto.request.AuthRequest;
import com.example.betrimex.model.dto.response.AuthResponse;
import com.example.betrimex.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        System.out.println("Request received: " + authRequest);

        return ResponseEntity.ok(authService.login(authRequest));
    }
}

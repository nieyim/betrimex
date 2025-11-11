package com.example.betrimex.service;

import com.example.betrimex.model.dto.request.AuthRequest;
import com.example.betrimex.model.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}

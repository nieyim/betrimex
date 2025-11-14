package com.example.betrimex.service;

import com.example.betrimex.model.dto.request.UserRequest;
import com.example.betrimex.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    void update(String id, UserRequest userRequest);

    void delete(String id);

    UserResponse findById(String id);
}

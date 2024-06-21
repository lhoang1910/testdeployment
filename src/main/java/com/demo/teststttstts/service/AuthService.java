package com.demo.teststttstts.service;


import com.demo.teststttstts.dto.request.CreateUserRequest;
import com.demo.teststttstts.dto.request.LoginRequest;
import com.demo.teststttstts.dto.response.LoginSuccessResponse;
import com.demo.teststttstts.dto.response.WrapResponse;

public interface AuthService {
    WrapResponse<?> createUser(CreateUserRequest user);
    WrapResponse<LoginSuccessResponse> login(LoginRequest loginRequest);

}

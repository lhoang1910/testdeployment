package com.demo.teststttstts.controller;

import javax.validation.Valid;

import com.demo.teststttstts.dto.request.CreateUserRequest;
import com.demo.teststttstts.dto.request.LoginRequest;
import com.demo.teststttstts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authService.createUser(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}

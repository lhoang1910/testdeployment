package com.demo.teststttstts.service.Impl;

import com.demo.teststttstts.component.constant.UserRole;
import com.demo.teststttstts.dto.request.CreateUserRequest;
import com.demo.teststttstts.dto.request.LoginRequest;
import com.demo.teststttstts.dto.response.LoginSuccessResponse;
import com.demo.teststttstts.dto.response.WrapResponse;
import com.demo.teststttstts.entity.UserEntity;
import com.demo.teststttstts.repository.UserRepository;
import com.demo.teststttstts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public WrapResponse<?> createUser(CreateUserRequest request) {
        Optional<UserEntity> existUser = userRepository.findByUsername(request.getUsername());

        if (existUser.isPresent()){
            return new WrapResponse<>(false, HttpStatus.CONFLICT.value(), "Tên đăng nhập đã tồn tại");
        }

        UserEntity newUser= new UserEntity();
        newUser.setId(String.valueOf(UUID.randomUUID()));
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.ROLE_NHAN_VIEN);
        newUser.setDeleted(false);

        long numOfUser = userRepository.quantityOfUser() + 1;
        String userCode = String.format("U%03d",numOfUser);

        newUser.setUserCode(userCode);
        userRepository.save(newUser);

        return new WrapResponse<>(true, HttpStatus.OK.value(), "Đăng ký thành công");
    }

    @Override
    public WrapResponse<LoginSuccessResponse> login(LoginRequest request) {

        Optional<UserEntity> loginedUser = userRepository.findByUsername(request.getUsername());

        if (loginedUser.isEmpty()){
            return new WrapResponse<>(false, HttpStatus.NOT_FOUND.value(), "Tài khoản không tồn tại");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginSuccessResponse response = new LoginSuccessResponse();

        response.setUsername(loginedUser.get().getUsername());
        response.setUserCode(loginedUser.get().getUserCode());
        response.setRole(response.setRoleName(loginedUser.get().getRole()));
        response.setDeleted(loginedUser.get().getDeleted());

        return new WrapResponse<>(true, HttpStatus.OK.value(), "Đăng nhập thành công", response);
    }
}

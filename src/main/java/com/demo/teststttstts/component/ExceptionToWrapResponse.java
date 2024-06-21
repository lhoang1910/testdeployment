package com.demo.teststttstts.component;

import com.demo.teststttstts.dto.response.WrapResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class ExceptionToWrapResponse {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<WrapResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        return buildResponseEntity(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public static ResponseEntity<WrapResponse<?>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<String> errors = new HashSet<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
        return buildResponseEntity(errors);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public static ResponseEntity<WrapResponse<?>> handleNotFoundExceptions(NotFoundException ex) {
        return buildResponseEntity(Collections.singleton(ex.getMessage()));
    }

    private static ResponseEntity<WrapResponse<?>> buildResponseEntity(Set<String> errors) {
        WrapResponse<?> response = WrapResponse.builder()
                .isSuccess(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(String.join(", ", errors))
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}

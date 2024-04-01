package org.orgless.carboncellassignment.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orgless.carboncellassignment.service.AuthService;
import org.orgless.carboncellassignment.utils.AuthorizationDto;
import org.orgless.carboncellassignment.utils.LoginDto;
import org.orgless.carboncellassignment.utils.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationDto> loginUser(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(authService.loginUser(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(authService.registerUser(registerDto));
    }

    @GetMapping("/index")
    public String index() {
        return "Hello, world!";
    }
}

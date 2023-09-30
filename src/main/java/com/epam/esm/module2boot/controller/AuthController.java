package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.LoginRequestDTO;
import com.epam.esm.module2boot.dto.LoginResponseDTO;
import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody UserDTO loginRequest) {
        return ResponseEntity.ok(authService.register(loginRequest));
    }


}

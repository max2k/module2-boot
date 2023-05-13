package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.LoginRequestDTO;
import com.epam.esm.module2boot.dto.LoginResponseDTO;
import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        val user = userService.getUserByEmail(loginRequest.getUsername());
        val jwtToken = jwtService.generateToken(user);
        return LoginResponseDTO.builder()
                .accessToken(jwtToken)
                .build();

    }

    public LoginResponseDTO register(UserDTO registerRequest) {
        try {

            userService.createUser(registerRequest);
            val user = userService.getUserByEmail(registerRequest.getEmail());

            val jwtToken = jwtService.generateToken(user);
            return LoginResponseDTO.builder()
                    .accessToken(jwtToken)
                    .build();

        } catch (DataBaseConstrainException e) {
            throw new BadRequestException("User with this email already exists", e);
        }


    }
}

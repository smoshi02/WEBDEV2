package com.roi.cartoh.controller.api;

import com.roi.cartoh.dto.AuthRequest;
import com.roi.cartoh.dto.AuthResponse;
import com.roi.cartoh.dto.RegisterRequest;
import com.roi.cartoh.service.JwtTokenService;
import com.roi.cartoh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController("apiAuthController")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtTokenService.generateToken(authentication);
        Long expiresAt = jwtTokenService.extractExpirationTime(token);

        return new AuthResponse(token, authentication.getName(), expiresAt);
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request.username(), request.password());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtTokenService.generateToken(authentication);
        Long expiresAt = jwtTokenService.extractExpirationTime(token);

        return new AuthResponse(token, request.username(), expiresAt);
    }


    @GetMapping("/validate")
    public String validateToken() {
        return "Token is valid";
    }
}
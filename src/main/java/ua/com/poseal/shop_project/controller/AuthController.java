package ua.com.poseal.shop_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.poseal.shop_project.dto.AuthResponse;
import ua.com.poseal.shop_project.dto.RegisterRequest;
import ua.com.poseal.shop_project.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "New user registration")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        AuthResponse response = new AuthResponse(
                "The user is successfully registered",
                request.username()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

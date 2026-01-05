package shrowd.beeper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shrowd.beeper.dto.request.LoginRequest;
import shrowd.beeper.dto.request.RegisterRequest;
import shrowd.beeper.dto.response.TokenResponse;
import shrowd.beeper.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        authService.validateCredentials(request.email(), request.password());
        TokenResponse jwtToken = new TokenResponse(authService.getJwtToken(request.email()));

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.registerUser(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

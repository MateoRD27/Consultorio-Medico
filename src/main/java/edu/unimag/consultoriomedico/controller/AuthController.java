package edu.unimag.consultoriomedico.controller;


import edu.unimag.consultoriomedico.dto.LoginJwtResponse;
import edu.unimag.consultoriomedico.dto.LoginRequest;
import edu.unimag.consultoriomedico.dto.MessageResponse;
import edu.unimag.consultoriomedico.dto.SignupRequest;
import edu.unimag.consultoriomedico.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginJwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
            LoginJwtResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest dto) {
        MessageResponse messageResponse = authService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }
}

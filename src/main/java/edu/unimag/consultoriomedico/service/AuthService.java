package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.LoginJwtResponse;
import edu.unimag.consultoriomedico.dto.LoginRequest;
import edu.unimag.consultoriomedico.dto.MessageResponse;
import edu.unimag.consultoriomedico.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginJwtResponse login(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signupRequest);
}

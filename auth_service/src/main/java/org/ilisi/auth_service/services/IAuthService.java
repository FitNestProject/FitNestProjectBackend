package org.ilisi.auth_service.services;


import org.ilisi.auth_service.dto.AuthenticationResponse;
import org.ilisi.auth_service.dto.AuthenticationRequest;
import org.ilisi.auth_service.dto.RegisterRequest;

public interface IAuthService {

    void checkIfUserOrEmailExists(String email, String username);

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void sendPasswordResetToken(String username);

    void resetPassword(String token, String newPassword);
}
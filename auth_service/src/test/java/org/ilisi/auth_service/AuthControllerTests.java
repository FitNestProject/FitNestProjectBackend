package org.ilisi.auth_service;/*package fitnest.auth_service;

import fitnest.auth_service.controllers.AuthController;
import fitnest.auth_service.dto.*;
import fitnest.auth_service.services.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTests {

    @InjectMocks
    private AuthController authController;

    @Mock
    private IAuthService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse response = new AuthenticationResponse("jwt-token");

        when(authenticationService.register(registerRequest)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("jwt-token", result.getBody().getToken());
    }

    @Test
    void testAuthenticate() {
        AuthenticationResquest authRequest = new AuthenticationResquest();
        AuthenticationResponse response = new AuthenticationResponse("jwt-token");

        when(authenticationService.authenticate(authRequest)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authController.authenticate(authRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("jwt-token", result.getBody().getToken());
    }

    @Test
    void testRequestPasswordReset() {
        PasswordResetRequest resetRequest = new PasswordResetRequest();
        resetRequest.setUsername("testUser");

        doNothing().when(authenticationService).sendPasswordResetToken(resetRequest.getUsername());

        ResponseEntity<String> result = authController.requestPasswordReset(resetRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Password reset token sent.", result.getBody());
    }

    @Test
    void testResetPassword() {
        PasswordUpdateRequest updateRequest = new PasswordUpdateRequest();
        updateRequest.setToken("reset-token");
        updateRequest.setNewPassword("newPassword123");

        doNothing().when(authenticationService).resetPassword(updateRequest.getToken(), updateRequest.getNewPassword());

        ResponseEntity<String> result = authController.resetPassword(updateRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Password has been reset successfully.", result.getBody());
    }
}
*/
package org.ilisi.auth_service.controllers;

import org.ilisi.auth_service.services.IAccountService; // Import de l'interface
import org.ilisi.auth_service.dto.ErrorResponse;  // Import de ErrorResponse
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private IAccountService accountService;

    // Récupérer le compte par ID
    @GetMapping("/id")
    public ResponseEntity<?> getUserAccountById(@RequestParam Long id) {
        try {
            return accountService.findAccountById(id);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Error occurred while fetching account: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);  // Retourner une réponse avec l'objet ErrorResponse
        }
    }

    // Récupérer un compte par le nom d'utilisateur (ici, email)
    @GetMapping("/username")
    public ResponseEntity<?> getAccountByUsername(@RequestParam String email) {
        try {
            return accountService.findAccountByUsername(email);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Error occurred while fetching account: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);  // Retourner une réponse avec l'objet ErrorResponse
        }
    }

    // Récupérer tous les comptes
    @GetMapping("/all")
    public ResponseEntity<?> getAllAccounts() {
        try {
            return accountService.retrieveAllAccounts();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Error occurred while fetching accounts: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);  // Retourner une réponse avec l'objet ErrorResponse
        }
    }

}

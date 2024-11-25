package org.ilisi.auth_service.services.ImplServices;


import org.ilisi.auth_service.Repos.AccountRepo;
import org.ilisi.auth_service.Repos.UserRepo;
import org.ilisi.auth_service.dto.AuthenticationResponse;
import org.ilisi.auth_service.dto.AuthenticationRequest;
import org.ilisi.auth_service.dto.RegisterRequest;
import org.ilisi.auth_service.entities.Account;
import org.ilisi.auth_service.entities.User;
import org.ilisi.auth_service.exceptions.UserAlreadyExistsException;
import org.ilisi.auth_service.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(AccountRepo accountRepo, UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void checkIfUserOrEmailExists(String email, String username) {
        if (accountRepo.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (accountRepo.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        accountRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Rechercher le compte en fonction du login (nom d'utilisateur ou email)
        Account account = accountRepo.findByUsername(request.getLogin())
                .orElseGet(() -> accountRepo.findByEmail(request.getLogin())
                        .orElseThrow(() -> new RuntimeException("User not found")));

        // Authentification
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getUsername(), request.getPassword())
        );

        // Génération du token JWT
        var jwtToken = jwtService.generateToken(account);

        // Récupération de l'utilisateur associé à ce compte
        User user = userRepo.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("No user associated with this account"));
        System.out.println("Authenticating user: " + request.getLogin());
        System.out.println("Generated token: " + jwtToken);
        System.out.println("User ID: " + user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())  // Vérifiez que cette ligne utilise bien user.getId()
                .build();

    }



    @Override
    public void sendPasswordResetToken(String username) {
        var user = accountRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var resetToken = jwtService.generateToken(user);
        // Logic to send the token can be added here
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        var username = jwtService.extractUsername(token);
        var user = accountRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        accountRepo.save(user);
    }
}

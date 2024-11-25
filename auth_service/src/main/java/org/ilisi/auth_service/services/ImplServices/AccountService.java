package org.ilisi.auth_service.services.ImplServices;

import org.ilisi.auth_service.Repos.AccountRepo;
import org.ilisi.auth_service.entities.Account;
import org.ilisi.auth_service.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public ResponseEntity<Account> findAccountById(Long id) {
        return accountRepo.findById(id)
                .map(ResponseEntity::ok)  // If user is found, return 200 with user
                .orElseGet(() -> ResponseEntity.notFound().build());  // Else return 404
    }

    @Override
    public ResponseEntity<Account> findAccountByUsername(String email) {
        return accountRepo.findByUsername(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Account>> retrieveAllAccounts() {
        List<Account> users = accountRepo.findAll();
        return ResponseEntity.ok(users);
    }
}

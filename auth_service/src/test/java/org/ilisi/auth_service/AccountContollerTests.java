package org.ilisi.auth_service;/*package fitnest.auth_service;

import fitnest.auth_service.controllers.AccountController;
import fitnest.auth_service.entities.Account;
import fitnest.auth_service.services.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountContollerTests {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private IAccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserAccountById() {
        Account account = new Account();
        account.setId(1L);

        when(accountService.findAccountById(1L)).thenReturn(ResponseEntity.of(Optional.of(account)));

        ResponseEntity<Account> result = (ResponseEntity<Account>) accountController.getUserAccountById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void testGetAccountByUsername() {
        Account account = new Account();
        account.setUsername("test123");

        when(accountService.findAccountByUsername("test@example.com")).thenReturn(ResponseEntity.of(Optional.of(account)));

        ResponseEntity<Account> result = (ResponseEntity<Account>) accountController.getAccountByUsername("test@example.com");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("test@example.com", result.getBody().getUsername());
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());

        when(accountService.retrieveAllAccounts()).thenReturn(ResponseEntity.ok(accounts));

        ResponseEntity<List<Account>> result = (ResponseEntity<List<Account>>) accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
    }
}
*/
package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CreateAccountRequest;
import com.fintech.cms.card_management_system.dto.UpdateAccountRequest;
import com.fintech.cms.card_management_system.model.Account;
import com.fintech.cms.card_management_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        Account account = new Account();
        account.setStatus(request.getStatus());
        account.setBalance(request.getBalance());
        return accountService.createAccount(account);
    }

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get account by ID
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable UUID id) {
        return accountService.getAccountById(id);
    }

    // Update account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountRequest request) {
        Account account = new Account();
        account.setStatus(request.getStatus());
        account.setBalance(request.getBalance());
        return accountService.updateAccount(id, account);
    }

    // Delete account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return "Account deleted successfully";
    }
}

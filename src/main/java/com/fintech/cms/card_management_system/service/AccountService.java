package com.fintech.cms.card_management_system.service;

import com.fintech.cms.card_management_system.exception.ResourceNotFoundException;
import com.fintech.cms.card_management_system.model.Account;
import com.fintech.cms.card_management_system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }


    public Account updateAccount(UUID id, Account accountDetails) {
        Account account = getAccountById(id);
        account.setStatus(accountDetails.getStatus());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(UUID id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}

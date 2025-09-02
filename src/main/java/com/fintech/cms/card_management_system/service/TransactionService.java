package com.fintech.cms.card_management_system.service;

import com.fintech.cms.card_management_system.exception.InvalidOperationException;
import com.fintech.cms.card_management_system.exception.ResourceNotFoundException;
import com.fintech.cms.card_management_system.model.Account;
import com.fintech.cms.card_management_system.model.Card;
import com.fintech.cms.card_management_system.model.Transaction;
import com.fintech.cms.card_management_system.repository.AccountRepository;
import com.fintech.cms.card_management_system.repository.CardRepository;
import com.fintech.cms.card_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    public Transaction createTransaction(Transaction transaction, UUID accountId, UUID cardId) {
        // Fetch account or throw ResourceNotFoundException
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Card card = null;
        if (cardId != null) {
            card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

            // Validate card
            if (!"ACTIVE".equalsIgnoreCase(card.getStatus()) ||
                    card.getExpiry().isBefore(LocalDateTime.now().toLocalDate())) {
                throw new InvalidOperationException("Card is invalid or expired");
            }
        }

        // Validate account status
        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new InvalidOperationException("Account is inactive");
        }

        BigDecimal amount = transaction.getTransactionAmount();

        // Process transaction
        if ("D".equalsIgnoreCase(transaction.getTransactionType())) { // Debit
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InvalidOperationException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else if ("C".equalsIgnoreCase(transaction.getTransactionType())) { // Credit
            account.setBalance(account.getBalance().add(amount));
        } else {
            throw new InvalidOperationException("Invalid transaction type");
        }

        // Update account and save transaction
        accountRepository.save(account);
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }
}

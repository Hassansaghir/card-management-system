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

    public Transaction createTransaction(Transaction transaction, UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        if (!"ACTIVE".equalsIgnoreCase(card.getStatus()) ||
                card.getExpiry().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new InvalidOperationException("Card is invalid or expired");
        }

        Account account = card.getAccount();
        if (account == null) {
            throw new InvalidOperationException("Card is not linked to an account");
        }

        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new InvalidOperationException("Account is inactive");
        }

        BigDecimal amount = transaction.getTransactionAmount();

        if ("D".equalsIgnoreCase(transaction.getTransactionType())) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InvalidOperationException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else if ("C".equalsIgnoreCase(transaction.getTransactionType())) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            throw new InvalidOperationException("Invalid transaction type");
        }

        accountRepository.save(account);
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

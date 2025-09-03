package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CreateTransactionRequest;
import com.fintech.cms.card_management_system.model.Transaction;
import com.fintech.cms.card_management_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Create transaction
    @PostMapping("/{cardId}")
    public Transaction createTransaction(@PathVariable UUID cardId,
                                         @RequestBody CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(request.getTransactionAmount());
        transaction.setTransactionType(request.getTransactionType());

        return transactionService.createTransaction(transaction, cardId);
    }


    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }
}

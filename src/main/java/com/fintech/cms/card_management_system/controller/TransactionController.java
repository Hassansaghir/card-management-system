package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CreateTransactionRequest;
import com.fintech.cms.card_management_system.model.Transaction;
import com.fintech.cms.card_management_system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;




@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;
    private final ModelMapper modelMapper;
    private final Maplist maplist;

    // Create transaction
    @PostMapping("/transactions/{cardNumber}")
    public ResponseEntity<CreateTransactionRequest> createTransaction(
            @PathVariable("cardNumber") String cardNumber,
            @Valid @RequestBody CreateTransactionRequest request) {

        Transaction transaction = transactionService.createTransaction(request, cardNumber);
        log.info("Created transaction with id {} for card {}", transaction.getId(), cardNumber);

        CreateTransactionRequest response = modelMapper.map(transaction, CreateTransactionRequest.class);
        return ResponseEntity.ok(response);
    }


    // Get all transactions
    @GetMapping
    public ResponseEntity<List<CreateTransactionRequest>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        log.info("Fetched {} transactions", transactions.size());
        return ResponseEntity.ok(maplist.mapList(transactions, CreateTransactionRequest.class));
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<CreateTransactionRequest> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = transactionService.getTransactionById(id);
        log.info("Fetched transaction with id {}", id);
        return ResponseEntity.ok(modelMapper.map(transaction, CreateTransactionRequest.class));
    }
}

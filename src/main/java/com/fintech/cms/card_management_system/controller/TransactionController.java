package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CreateTransactionRequest;
import com.fintech.cms.card_management_system.dto.TransactionResponse;
import com.fintech.cms.card_management_system.model.Transaction;
import com.fintech.cms.card_management_system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
    @PostMapping("/{cardNumber}")
    public ResponseEntity<TransactionResponse> createTransaction(
            @PathVariable("cardNumber") String cardNumber,
            @Valid @RequestBody CreateTransactionRequest request) {

        Transaction transaction = transactionService.createTransaction(request, cardNumber);
        log.info("Created transaction with id {} for card {}", transaction.getId(), cardNumber);

        TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
        response.setBalance(transaction.getCard().getAccount().getBalance());
        return ResponseEntity.ok(response);
    }


    // Get all transactions
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionResponse> response = maplist.mapList(transactions, TransactionResponse.class);
        response.sort(Comparator.comparing(TransactionResponse::getCreatedAt).reversed());
        return ResponseEntity.ok(response);

    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = transactionService.getTransactionById(id);
        TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
        return ResponseEntity.ok(response);

    }
}

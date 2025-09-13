package com.fintech.cms.card_management_system.dto;

import com.fintech.cms.card_management_system.model.Type;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {
    private UUID id;
    private BigDecimal transactionAmount;
    private Type transactionType; // CREDIT or DEBIT
    private String cardNumber;
    private LocalDateTime createdAt = LocalDateTime.now();
    private BigDecimal balance;
}

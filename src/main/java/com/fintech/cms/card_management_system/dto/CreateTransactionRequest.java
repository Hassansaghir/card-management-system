package com.fintech.cms.card_management_system.dto;

import com.fintech.cms.card_management_system.model.Type;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateTransactionRequest {

    @NotNull(message = "Transaction amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Transaction amount must be greater than 0")
    private BigDecimal transactionAmount;

    @NotNull(message = "Transaction type is required")
    private Type transactionType; // CREDIT or DEBIT

    @NotNull(message = "CardNumber is required")
    private String CardNumber;
}

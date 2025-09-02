package com.fintech.cms.card_management_system.dto;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    private BigDecimal transactionAmount;
    private String transactionType; // C = Credit, D = Debit
    private String cardId; // optional

    public BigDecimal getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
}

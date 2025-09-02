package com.fintech.cms.card_management_system.dto;

import java.time.LocalDate;

public class CreateCardRequest {

    private String cardNumber;
    private LocalDate expiry;

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public LocalDate getExpiry() { return expiry; }
    public void setExpiry(LocalDate expiry) { this.expiry = expiry; }
}

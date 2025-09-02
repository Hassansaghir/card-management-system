package com.fintech.cms.card_management_system.dto;

import java.math.BigDecimal;

public class CreateAccountRequest {

    private String status; // ACTIVE or INACTIVE
    private BigDecimal balance;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}

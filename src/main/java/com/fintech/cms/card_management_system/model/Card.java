package com.fintech.cms.card_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue
    private UUID id;

    private String cardNumber; // store securely
    private String status; // ACTIVE, INACTIVE
    private LocalDate expiry;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getExpiry() { return expiry; }
    public void setExpiry(LocalDate expiry) { this.expiry = expiry; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}

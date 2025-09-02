package com.fintech.cms.card_management_system.service;

import com.fintech.cms.card_management_system.exception.ResourceNotFoundException;
import com.fintech.cms.card_management_system.model.Account;
import com.fintech.cms.card_management_system.model.Card;
import com.fintech.cms.card_management_system.repository.AccountRepository;
import com.fintech.cms.card_management_system.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Card createCard(Card card, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        card.setAccount(account);
        card.setStatus("INACTIVE"); // default status
        return cardRepository.save(card);
    }

    public Card activateCard(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setStatus("ACTIVE");
        return cardRepository.save(card);
    }

    public Card deactivateCard(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        card.setStatus("INACTIVE");
        return cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(UUID cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    public boolean isCardValid(Card card) {
        return "ACTIVE".equalsIgnoreCase(card.getStatus()) &&
                !card.getExpiry().isBefore(LocalDate.now());
    }
}

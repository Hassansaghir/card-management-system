package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CreateCardRequest;
import com.fintech.cms.card_management_system.model.Card;
import com.fintech.cms.card_management_system.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // Create a new card for an account
    @PostMapping("/{accountId}")
    public Card createCard(@RequestBody CreateCardRequest request, @PathVariable UUID accountId) {
        Card card = new Card();
        card.setCardNumber(request.getCardNumber());
        card.setExpiry(request.getExpiry());
        return cardService.createCard(card, accountId);
    }

    // Get all cards
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // Get card by ID
    @GetMapping("/{id}")
    public Card getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    // Activate card
    @PutMapping("/{id}/activate")
    public Card activateCard(@PathVariable UUID id) {
        return cardService.activateCard(id);
    }

    // Deactivate card
    @PutMapping("/{id}/deactivate")
    public Card deactivateCard(@PathVariable UUID id) {
        return cardService.deactivateCard(id);
    }
}

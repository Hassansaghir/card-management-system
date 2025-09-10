package com.fintech.cms.card_management_system.controller;

import com.fintech.cms.card_management_system.dto.CardDto;
import com.fintech.cms.card_management_system.dto.CreateCardRequest;
import com.fintech.cms.card_management_system.model.Card;
import com.fintech.cms.card_management_system.repository.AccountRepository;
import com.fintech.cms.card_management_system.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;

    private final ModelMapper modelMapper;

    private final Maplist maplist;

    // Create a new card for an account
    @PostMapping("/{accountId}")
    public ResponseEntity<CreateCardRequest> createCard(@Valid @RequestBody CreateCardRequest request,
                                                        @PathVariable UUID accountId) {
        Card savedCard = cardService.createCard(request, accountId);
        log.info("Created card with id {} for account {}", savedCard.getId(), accountId);
        return ResponseEntity.ok(modelMapper.map(savedCard,CreateCardRequest.class));
    }
    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        log.info("Fetched {} cards", cards.size());

        // Map entities to DTOs
        List<CardDto> cardRequests = maplist.mapList(cards, CardDto.class);

        // Set account_id for each DTO
        for (int i = 0; i < cards.size(); i++) {
            cardRequests.get(i).setAccount_id(cards.get(i).getAccount().getId());
        }

        return ResponseEntity.ok(cardRequests);
    }


    // Get card by CardNumber
    @GetMapping("/{CardNumber}")
    public ResponseEntity<CardDto> getCardById(@PathVariable String CardNumber) {
        Card card = cardService.getCardByCardNumber(CardNumber);
        log.info("Fetched card with number{}", CardNumber);
        CardDto cardDto = modelMapper.map(card,CardDto.class);
        cardDto.setAccount_id(card.getAccount().getId());
        return ResponseEntity.ok(cardDto);
    }

    // Activate card
    @PutMapping("/{id}/activate")
    public ResponseEntity<CreateCardRequest> activateCard(@PathVariable UUID id) {
        Card card = cardService.activateCard(id);
        log.info("Activated card with id {}", id);
        return ResponseEntity.ok(modelMapper.map(card,CreateCardRequest.class));
    }

    // Deactivate card
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<CreateCardRequest> deactivateCard(@PathVariable UUID id) {
        Card card = cardService.deactivateCard(id);
        log.info("Deactivated card with id {}", id);
        return ResponseEntity.ok(modelMapper.map(card,CreateCardRequest.class));
    }
}

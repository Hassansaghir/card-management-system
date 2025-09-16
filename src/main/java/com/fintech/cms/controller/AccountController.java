package com.fintech.cms.controller;

import com.fintech.cms.dto.AccountDTO;
import com.fintech.cms.dto.CardDto;
import com.fintech.cms.dto.CreateAccountRequest;
import com.fintech.cms.dto.UpdateAccountRequest;
import com.fintech.cms.model.Account;
import com.fintech.cms.repository.AccountRepository;
import com.fintech.cms.repository.CardRepository;
import com.fintech.cms.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final Maplist maplist;
    private final CardRepository cardRepository;

    // Create Account
    @PostMapping
    public ResponseEntity<CreateAccountRequest> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account created = accountService.createAccount(request);
        log.info("Account created with id {}", created.getId());
        return ResponseEntity.ok(modelMapper.map(created, CreateAccountRequest.class));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        log.info("Fetched {} accounts", accounts.size());

        // map Account -> AccountDTO (cards inside will be mapped too)
        List<AccountDTO> accountDTOS = maplist.mapList(accounts, AccountDTO.class);

        for (int i = 0; i < accounts.size(); i++) {
            UUID accountId = accounts.get(i).getId();

            List<CardDto> cardDtos = accountDTOS.get(i).getCards();
            if (cardDtos != null) {
                for (CardDto cardDto : cardDtos) {
                    cardDto.setAccount_id(accountId);
                }
            }
        }

        return ResponseEntity.ok(accountDTOS);
    }



    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        Account account = accountService.getAccountById(id);
        log.info("Fetched account with id {}", id);

        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        // Set account_id for each card
        if (accountDTO.getCards() != null) {
            for (CardDto card : accountDTO.getCards()) {
                card.setAccount_id(account.getId());
            }
        }

        return ResponseEntity.ok(accountDTO);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccountPartially(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAccountRequest request) {

        Account updated = accountService.updateAccountPartially(id, request);
        log.info("Patched account with id {}", id);
        AccountDTO accountDTO = modelMapper.map(updated, AccountDTO.class);
        List<CardDto> cardDtoList = new ArrayList<CardDto>();
        for (int i = 0; i < accountDTO.getCards().size(); i++) {
            cardDtoList.add(accountDTO.getCards().get(i));
            cardDtoList.get(i).setAccount_id(updated.getId());

        }
        return ResponseEntity.ok(accountDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        log.info("Deleted account with id {}", id);
        return ResponseEntity.ok("Account deleted successfully");
    }

}

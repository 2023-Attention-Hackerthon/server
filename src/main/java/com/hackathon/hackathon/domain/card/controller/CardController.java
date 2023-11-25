package com.hackathon.hackathon.domain.card.controller;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class CardController {

    private final CardService cardService;

    @PostMapping("/{walletId}/create")
    public ResponseEntity<?> createCard(@RequestBody CardCreateRequestDto cardCreateRequestDto,
                                        @PathVariable Long walletId) {
        return cardService.createCard(cardCreateRequestDto, walletId);
    }
}
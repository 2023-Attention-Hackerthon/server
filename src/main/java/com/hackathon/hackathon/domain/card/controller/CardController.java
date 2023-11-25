package com.hackathon.hackathon.domain.card.controller;

import com.hackathon.hackathon.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class CardController {

    private final CardService cardService;


}

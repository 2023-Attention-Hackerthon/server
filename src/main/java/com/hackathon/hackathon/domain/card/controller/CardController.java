package com.hackathon.hackathon.domain.card.controller;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.service.CardService;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.global.S3.S3Service;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final AuthentiatedUserUtils authentiatedUserUtils;

    @PostMapping("/{walletId}/create")
    public ResponseEntity<?> createCard(@RequestBody CardCreateRequestDto cardCreateRequestDto,
                                        @PathVariable Long walletId) {
        return cardService.createCard(cardCreateRequestDto, walletId);
    }


    @PatchMapping("/cards/{cardId}/delete")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        User currentUser = authentiatedUserUtils.getCurrentUser();
        return cardService.deleteCard(currentUser, cardId);
    }

    @GetMapping("cards/{cardId}")
    public ResponseEntity<?> getCardInfo(@PathVariable Long cardId) throws Exception {
        User currentUser = authentiatedUserUtils.getCurrentUser();
        return cardService.getCardInfo(currentUser, cardId);
    }

    @Operation(description = "명함")
    @PostMapping(value = "/image/{walletId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuccessResponse<Object> uploadImage(@RequestParam("file") MultipartFile file, Long walletId) throws IOException {
        try {
            String imageUrl = cardService.uploadImage(file, walletId);
            SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200,imageUrl);
            return successResponse;
        } catch (IOException e) {
            throw  new IllegalArgumentException("오류");
        }
    }
}
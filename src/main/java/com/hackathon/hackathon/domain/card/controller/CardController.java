package com.hackathon.hackathon.domain.card.controller;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.service.CardService;
import com.hackathon.hackathon.global.S3.S3Service;
import com.hackathon.hackathon.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
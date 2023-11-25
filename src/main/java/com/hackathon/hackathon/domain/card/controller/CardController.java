package com.hackathon.hackathon.domain.card.controller;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.service.CardService;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@SecurityRequirement(name = "access-token")
public class CardController {

    private final CardService cardService;
    private final AuthentiatedUserUtils authentiatedUserUtils;

    @PostMapping("/{walletId}/create")
    public ResponseEntity<?> createCard(@RequestBody CardCreateRequestDto cardCreateRequestDto,
                                        @PathVariable Long walletId) {
        User currentUser = authentiatedUserUtils.getCurrentUser();
        return cardService.createCard(currentUser, cardCreateRequestDto, walletId);
    }


    @PatchMapping("/{cardId}/delete")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) throws Exception {
        User currentUser = authentiatedUserUtils.getCurrentUser();
        return cardService.deleteCard(currentUser, cardId);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCardInfo(@PathVariable Long cardId) throws Exception {
        User currentUser = authentiatedUserUtils.getCurrentUser();
        return cardService.getCardInfo(currentUser, cardId);
    }

    @Operation(description = "명함")
    @PostMapping(value = "/image/{walletId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public SuccessResponse<Object> uploadImage(@RequestParam("file") MultipartFile file, Long walletId)
            throws IOException {
        try {
            String imageUrl = cardService.uploadImage(file, walletId);
            SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200, imageUrl);
            return successResponse;
        } catch (IOException e) {
            throw new IllegalArgumentException("오류");
        }
    }
}
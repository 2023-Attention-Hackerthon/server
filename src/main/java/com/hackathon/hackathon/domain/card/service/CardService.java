package com.hackathon.hackathon.domain.card.service;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.dto.response.CardCreateResponseDto;
import com.hackathon.hackathon.domain.card.entity.Card;
import com.hackathon.hackathon.domain.card.repository.CardRepository;
import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.domain.wallet.service.WalletService;
import com.hackathon.hackathon.global.S3.S3Service;
import com.hackathon.hackathon.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final S3Service s3Service;

    public ResponseEntity<?> createCard(CardCreateRequestDto cardCreateRequestDto, Long walletId) {
        Card card = Card.builder().
                nickname(cardCreateRequestDto.getNickname()).
                contact(cardCreateRequestDto.getContact()).
                gender(cardCreateRequestDto.getGender()).
                instagramId(cardCreateRequestDto.getInstagramId()).
                blogUrl(cardCreateRequestDto.getBlogUrl()).
                youtubeUrl(cardCreateRequestDto.getYoutubeUrl()).
                githubId(cardCreateRequestDto.getGithubId()).
                wallet(walletRepository.findById(walletId).orElse(null)).
                build();
        Card saveCard = cardRepository.save(card);
        Long saveId = saveCard.getId();
        String saveNickname = saveCard.getNickname();
        CardCreateResponseDto cardCreateResponseDto = CardCreateResponseDto.builder().
                id(saveId).
                name(saveNickname).
                build();

        SuccessResponse apiResponse = SuccessResponse.builder().
                code(200).
                message("카드 생성에 성공했습니다.").
                data(cardCreateResponseDto).
                build();

        return ResponseEntity.ok(apiResponse);
    }

    @Transactional
    public String uploadImage(MultipartFile file, Long walletId) throws IOException {
        String imageUrl = s3Service.upload(file);
        Wallet wallet = walletRepository.findById(walletId).get();
        wallet.addImageUrl(imageUrl);
        return imageUrl;
    }
}

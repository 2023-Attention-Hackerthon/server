package com.hackathon.hackathon.domain.card.service;

import static com.hackathon.hackathon.domain.card.enums.CardStatus.ACTIVE;
import static com.hackathon.hackathon.domain.card.enums.CardStatus.DEACTIVE;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.dto.response.CardCreateResponseDto;
import com.hackathon.hackathon.domain.card.dto.response.CardDeleteResponseDto;
import com.hackathon.hackathon.domain.card.entity.Card;
import com.hackathon.hackathon.domain.card.repository.CardRepository;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.global.S3.S3Service;
import com.hackathon.hackathon.global.response.SuccessResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final WalletRepository walletRepository;
    private final S3Service s3Service;

    @Transactional
    public ResponseEntity<?> createCard(CardCreateRequestDto cardCreateRequestDto, Long walletId) {
        Card card = Card.builder().
                nickname(cardCreateRequestDto.getNickname()).
                contact(cardCreateRequestDto.getContact()).
                gender(cardCreateRequestDto.getGender()).
                instagramId(cardCreateRequestDto.getInstagramId()).
                blogUrl(cardCreateRequestDto.getBlogUrl()).
                youtubeUrl(cardCreateRequestDto.getYoutubeUrl()).
                githubId(cardCreateRequestDto.getGithubId()).
                cardStatus(ACTIVE).
                wallet(walletRepository.findById(walletId).orElse(null)).
                build();
        Card saveCard = cardRepository.save(card);

        SuccessResponse apiResponse = getSuccessResponse(saveCard);

        return ResponseEntity.ok(apiResponse);
    }

    private static SuccessResponse getSuccessResponse(Card saveCard) {
        CardCreateResponseDto cardCreateResponseDto = CardCreateResponseDto.builder().
                id(saveCard.getId()).
                nickname(saveCard.getNickname()).
                contact(saveCard.getContact()).
                gender(saveCard.getGender()).
                instagramId(saveCard.getInstagramId()).
                blogUrl(saveCard.getBlogUrl()).
                youtubeUrl(saveCard.getYoutubeUrl()).
                githubId(saveCard.getGithubId()).
                build();

        SuccessResponse apiResponse = SuccessResponse.builder().
                code(200).
                message("카드 생성에 성공했습니다.").
                data(cardCreateResponseDto).
                build();
        return apiResponse;
    }

    @Transactional
    public ResponseEntity<?> deleteCard(Long cardId) {
        Optional<Card> findCard = cardRepository.findById(cardId);
        Card updateCard = findCard.get().updateCardStatus(DEACTIVE);

        CardDeleteResponseDto cardDeleteResponseDto = CardDeleteResponseDto.builder().
                id(updateCard.getId()).
                cardStatus(updateCard.getCardStatus())
                .build();

        SuccessResponse apiResponse = SuccessResponse.builder().
                code(200).
                message("카드 삭제에 성공했습니다.").
                data(cardDeleteResponseDto).
                build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> getCardInfo(Long cardId) {
        Optional<Card> findCard = cardRepository.findById(cardId);
        CardCreateResponseDto cardCreateResponseDto = CardCreateResponseDto.builder().
                id(findCard.get().getId()).
                nickname(findCard.get().getNickname()).
                contact(findCard.get().getContact()).
                gender(findCard.get().getGender()).
                instagramId(findCard.get().getInstagramId()).
                blogUrl(findCard.get().getBlogUrl()).
                youtubeUrl(findCard.get().getYoutubeUrl()).
                githubId(findCard.get().getGithubId()).
                build();

        SuccessResponse apiResponse = SuccessResponse.builder().
                code(200).
                message("카드 생성에 성공했습니다.").
                data(cardCreateResponseDto).
                build();

        return ResponseEntity.ok(apiResponse);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String imageUrl = s3Service.upload(file);
        return imageUrl;
    }
}

package com.hackathon.hackathon.domain.card.service;

import static com.hackathon.hackathon.domain.card.enums.CardStatus.DEACTIVE;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.dto.response.CardCreateResponseDto;
import com.hackathon.hackathon.domain.card.dto.response.CardDeleteResponseDto;
import com.hackathon.hackathon.domain.card.entity.Card;
import com.hackathon.hackathon.domain.card.enums.CardStatus;
import com.hackathon.hackathon.domain.card.repository.CardRepository;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.global.response.SuccessResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final WalletRepository walletRepository;

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
}

package com.hackathon.hackathon.domain.card.service;

import static com.hackathon.hackathon.domain.card.enums.CardStatus.ACTIVE;
import static com.hackathon.hackathon.domain.card.enums.CardStatus.DEACTIVE;
import static com.hackathon.hackathon.domain.card.enums.CardSuccessMessage.CARD_DELETE_SUCCESS_MESSAGE;
import static com.hackathon.hackathon.domain.card.enums.CardSuccessMessage.CARD_GET_SUCCESS_MESSAGE;
import static com.hackathon.hackathon.domain.card.enums.HttpStatusCode.OK;
import static com.hackathon.hackathon.domain.card.exception.constants.CardErrorMessage.ACCESS_DENIED_OTHER_CARD;

import com.hackathon.hackathon.domain.card.dto.request.CardCreateRequestDto;
import com.hackathon.hackathon.domain.card.dto.response.CardCreateResponseDto;
import com.hackathon.hackathon.domain.card.dto.response.CardDeleteResponseDto;
import com.hackathon.hackathon.domain.card.entity.Card;
import com.hackathon.hackathon.domain.card.exception.CardException;
import com.hackathon.hackathon.domain.card.repository.CardRepository;
import com.hackathon.hackathon.domain.chatGPT.dto.request.ChatGptRequestDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.request.QuestionRequestDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.response.ChatGptColorResponseDTO;
import com.hackathon.hackathon.domain.chatGPT.service.ChatGptService;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.global.S3.S3Service;
import com.hackathon.hackathon.global.response.SuccessResponse;
import java.io.IOException;
import java.util.List;
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
    private final ChatGptService chatGptService;

    @Transactional
    public ResponseEntity<?> createCard(User currentUser, CardCreateRequestDto cardCreateRequestDto, Long walletId) {

        String chatGptRequestSentence = generateAdjectiveSentence(cardCreateRequestDto.getAdjective());
        //초은이가 해줄것
        ChatGptColorResponseDTO result = chatGptService.askQuestion(chatGptRequestSentence);
        //String result = "#sffsgsdgf";

        Card card = Card.builder().
                nickname(cardCreateRequestDto.getNickname()).
                contact(cardCreateRequestDto.getContact()).
                gender(cardCreateRequestDto.getGender()).
                instagramId(cardCreateRequestDto.getInstagramId()).
                blogUrl(cardCreateRequestDto.getBlogUrl()).
                youtubeUrl(cardCreateRequestDto.getYoutubeUrl()).
                githubId(cardCreateRequestDto.getGithubId()).
                cardName(cardCreateRequestDto.getCardName()).
                introduce(cardCreateRequestDto.getIntroduce()).
                age(cardCreateRequestDto.getAge()).
                adjective(cardCreateRequestDto.getAdjective()).
                mbti(cardCreateRequestDto.getMbti()).
                cardStatus(ACTIVE).
                colorCode(result.getColor()).
                wallet(walletRepository.findById(walletId).orElse(null)).
                build();
        Card saveCard = cardRepository.save(card);

        SuccessResponse apiResponse = getSuccessResponse(saveCard);

        return ResponseEntity.ok(apiResponse);
    }

    public String generateAdjectiveSentence(List<String> adjectives) {
        String adjectiveSentence = "[" + String.join(", ", adjectives) + "]";
        return adjectiveSentence + " " + "와 어울리는 색을 #FFB3B3,#FFD1B3,#FFF0B3,#F0FFB3,#D1FFB3,#B3FFB3,#B3FFD1,#B3FFF0,#B3F0FF,#B3D1FF,#B3B3FF,#D1B3FF,#F0B3FF,#FFB3F0,#FFB3D1 중에 하나 골라. 답변은 한 단어로. 답 예시 : #B3F0FF";
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
                cardName(saveCard.getCardName()).
                introduce(saveCard.getIntroduce()).
                age(saveCard.getAge()).
                adjective(saveCard.getAdjective()).
                mbti(saveCard.getMbti()).
                colorCode(saveCard.getColorCode()).
                walletId(saveCard.getWallet().getId()).
                build();



        return SuccessResponse.builder().
                code(OK.getValue()).
                message(CARD_GET_SUCCESS_MESSAGE.getMessage()).
                data(cardCreateResponseDto).
                build();
    }

    @Transactional
    public ResponseEntity<?> deleteCard(User currentUser, Long cardId) throws Exception {
        checkUserPrivilege(currentUser, cardId);
        Optional<Card> findCard = cardRepository.findById(cardId);
        Card updateCard = findCard.get().updateCardStatus(DEACTIVE);

        CardDeleteResponseDto cardDeleteResponseDto = CardDeleteResponseDto.builder().
                id(updateCard.getId()).
                cardStatus(updateCard.getCardStatus())
                .build();

        SuccessResponse<Object> apiResponse = SuccessResponse.builder().
                code(OK.getValue()).
                message(CARD_DELETE_SUCCESS_MESSAGE.getMessage()).
                data(cardDeleteResponseDto).
                build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> getCardInfo(User currentUser, Long cardId) throws Exception {
        //checkUserPrivilege(currentUser, cardId);
        Optional<Card> findCard = cardRepository.findById(cardId);
        User user = findCard.get().getWallet().getUser();
        if (user.getId() != currentUser.getId()) {
            throw new IllegalArgumentException("wrong user");
        }
        CardCreateResponseDto cardCreateResponseDto = CardCreateResponseDto.builder().
                id(findCard.get().getId()).
                cardName(findCard.get().getCardName()).
                introduce(findCard.get().getIntroduce()).
                age(findCard.get().getAge()).
                mbti(findCard.get().getMbti()).
                adjective(findCard.get().getAdjective()).
                nickname(findCard.get().getNickname()).
                contact(findCard.get().getContact()).
                gender(findCard.get().getGender()).
                instagramId(findCard.get().getInstagramId()).
                blogUrl(findCard.get().getBlogUrl()).
                youtubeUrl(findCard.get().getYoutubeUrl()).
                githubId(findCard.get().getGithubId()).
                colorCode(findCard.get().getColorCode()).
                build();

        SuccessResponse<Object> apiResponse = SuccessResponse.builder().
                code(OK.getValue()).
                message(CARD_GET_SUCCESS_MESSAGE.getMessage()).
                data(cardCreateResponseDto).
                build();

        return ResponseEntity.ok(apiResponse);
    }

    private static void checkUserPrivilege(User currentUser, Long cardId) throws Exception {
        if (!currentUser.getId().equals(cardId)) {
            throw CardException.of(ACCESS_DENIED_OTHER_CARD);
        }
    }

    @Transactional
    public String uploadImage(MultipartFile file, Long walletId) throws IOException {
        String imageUrl = s3Service.upload(file);
        Wallet wallet = walletRepository.findById(walletId).get();
        wallet.addImageUrl(imageUrl);
        return imageUrl;
    }
}

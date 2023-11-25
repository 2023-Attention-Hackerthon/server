package com.hackathon.hackathon.domain.card.entity;

import com.hackathon.hackathon.domain.card.enums.CardStatus;
import com.hackathon.hackathon.domain.card.enums.Gender;
import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import com.hackathon.hackathon.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nickname;
    private String cardName;

    private String contact;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private String mbti;
    private List<String> adjective;
    private String introduce;

    private String instagramId;

    private String blogUrl;

    private String youtubeUrl;

    private String githubId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CardStatus cardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    public Card updateCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
        return this;
    }
}


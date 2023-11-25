package com.hackathon.hackathon.domain.wallet.entity;

import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.wallet.enums.WalletStatus;
import com.hackathon.hackathon.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
public class Wallet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    protected WalletStatus status;

    @ElementCollection
    private List<String> imageUrls;

    public Wallet() {
        // 기본 생성자
        this.status = WalletStatus.ACTIVE;  // 생성자에서도 ACTIVE로 초기화
    }

//    public void updateUserStatus
    @Transactional
    public void updateStatus(WalletStatus newStatus) {
        this.status = newStatus;
    }

    @Transactional
    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }
}

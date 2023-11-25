package com.hackathon.hackathon.domain.wallet.enums;

import jakarta.persistence.Entity;
import lombok.*;

@RequiredArgsConstructor
public enum WalletStatus {
    ACTIVE("ACTIVE"),
    DEACTIVE("DEACTIVE");

    private final String status;

    public String getStatus() {return status;}
}

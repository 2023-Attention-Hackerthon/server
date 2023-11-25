package com.hackathon.hackathon.domain.wallet.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "WalletRequest")
public class WalletRequestDTO {

    @Schema(description = "지갑명", example = "내지갑")
    private String name;

    @Schema(description = "유저ID", example = "1")
    private Long userId;
}

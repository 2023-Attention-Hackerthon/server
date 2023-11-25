package com.hackathon.hackathon.domain.wallet.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WalletResponseDTO {

    @Schema(description = "지갑ID", example = "1")
    private Long id;

    @Schema(description = "지갑명", example = "내지갑")
    private String name;

    @Schema(description = "유저ID", example = "1")
    private Long userId;
}

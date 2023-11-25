package com.hackathon.hackathon.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    @NotNull
    @Schema(defaultValue = "이름", example = "이한비", required = true)
    private String name;
    @NotNull
    @Schema(defaultValue = "대학명", example = "홍익대학교", required = true)
    private String universityName;

}

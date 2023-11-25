package com.hackathon.hackathon.domain.user.controller;


import com.hackathon.hackathon.domain.user.dto.response.AccountTokenDto;
import com.hackathon.hackathon.domain.user.dto.response.IdTokenDto;
import com.hackathon.hackathon.domain.user.usecase.LoginUseCase;
import com.hackathon.hackathon.domain.user.usecase.RefreshTokenUseCase;
import com.hackathon.hackathon.domain.user.usecase.RequestIdTokenUseCase;
import com.hackathon.hackathon.domain.user.usecase.SignUpUseCase;
import com.hackathon.hackathon.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
@Tag(name = "인증 및 로그인 API", description = "인증 및 로그인 관련 API")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RequestIdTokenUseCase requestIdTokenUseCase;
    private final SignUpUseCase signUpUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @Operation(summary = "id token을 발급받습니다.")
    @GetMapping("/{logintype}/idtoken")
    public SuccessResponse<IdTokenDto> getIdToken(
            @RequestParam("code") String code,
            @PathVariable("logintype") String loginType){

        IdTokenDto idTokenDto = requestIdTokenUseCase.execute(loginType,code);
        SuccessResponse<IdTokenDto> successResponse = SuccessResponse.onSuccess(200, idTokenDto);
        return successResponse;
    }
    @Operation(summary = "id token으로 로그인 합니다.")
    @PostMapping("/{logintype}/login")
    public SuccessResponse<Object> login(
            @RequestParam("idtoken") String idToken,
            @PathVariable("logintype") String loginType){

        AccountTokenDto accountTokenDto = loginUseCase.execute(loginType,idToken);
        SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200, accountTokenDto);
        return successResponse;
    }

    @Operation(summary = "id token으로 회원가입 합니다.")
    @PostMapping("/{logintype}/signup")
    public SuccessResponse<Object> signUp(
            @RequestParam("idtoken") String idToken,
            @PathVariable("logintype") String loginType){

        AccountTokenDto accountTokenDto = signUpUseCase.execute(loginType,idToken);
        SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200, accountTokenDto);
        return successResponse;
    }

    @Operation(summary = "refresh token으로 access token을 재발급합니다.")
    @PostMapping("/refresh")
    public SuccessResponse<Object> refreshToken(
            @RequestParam("token") String refreshToken){

        AccountTokenDto accountTokenDto = refreshTokenUseCase.execute(refreshToken);
        SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200, accountTokenDto);
        return successResponse;
    }
}
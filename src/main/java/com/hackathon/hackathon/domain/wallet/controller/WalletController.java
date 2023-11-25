package com.hackathon.hackathon.domain.wallet.controller;

import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.wallet.dto.request.WalletRequestDTO;
import com.hackathon.hackathon.domain.wallet.service.WalletService;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
@SecurityRequirement(name = "access-token")
public class WalletController {

    private final WalletService walletService;
    private final AuthentiatedUserUtils authentiatedUserUtils;

    @Operation(description = "내가 만든 지갑 전체 조회")
    @GetMapping
    public SuccessResponse<Object> getAllWallets(){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.getWallets(user);
    }

    @Operation(description = "내가 만든 지갑 지갑 아이디로 조회")
    @GetMapping("/{walletId}")
    public SuccessResponse<Object> getCertoinWallet(@RequestParam Long walletId){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.getWallet(user, walletId);
    }

    @Operation(description = "지갑 생성")
    @PostMapping("/create")
    public SuccessResponse<Object> createWallet(@RequestBody WalletRequestDTO walletRequestDTO){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.createWallet(user, walletRequestDTO);
    }

    @Operation(description = "지갑 삭제")
    @PatchMapping("/{walletId}")
    public SuccessResponse<Object> deleteWallet(@RequestParam Long walletId){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.deleteWallet(user, walletId);
    }

    @Operation(description = "내가 만든 카드들(이미지) 지갑별로 보기")
    @GetMapping("/cards/{walletId}")
    public SuccessResponse<Object> getCardUrls(@RequestParam Long walletId){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.getAllCardLinks(walletId);
    }

    @Operation(description = "내가 만든 카드들 다 보기")
    @GetMapping("/cards")
    public SuccessResponse<Object> getCards(){
        User user = authentiatedUserUtils.getCurrentUser();
        return walletService.getAllCard(user);
    }


//    @Operation(description = "지갑에 여러 이미지 url 올리기")
//    @PostMapping("/{walletId}")
//    public List<String> uploadCard(@RequestParam Long walletId) {
//
//    }






}

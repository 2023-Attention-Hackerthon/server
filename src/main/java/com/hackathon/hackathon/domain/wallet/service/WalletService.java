package com.hackathon.hackathon.domain.wallet.service;

import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.repositroy.UserRepository;
import com.hackathon.hackathon.domain.wallet.dto.request.WalletRequestDTO;
import com.hackathon.hackathon.domain.wallet.dto.response.WalletResponseDTO;
import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;
    private final AuthentiatedUserUtils authentiatedUserUtils;

    // 지갑 만들기
    public ResponseEntity<?> createWallet(String userEmail, WalletRequestDTO walletRequestDTO) {


        User user = authentiatedUserUtils.getCurrentUser(); // 유저 정보 가져오기


        Wallet wallet = Wallet.builder()
                .name(walletRequestDTO.getName())
                .user(user)
                .build();

        walletRepository.save(wallet);

        WalletResponseDTO walletResponseDTO = WalletResponseDTO.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .name(wallet.getName())
                .build();

        SuccessResponse apiResponse = SuccessResponse.builder()
                .code(200)
                .message("success")
                .data(walletResponseDTO)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}

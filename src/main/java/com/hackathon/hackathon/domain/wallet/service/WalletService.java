package com.hackathon.hackathon.domain.wallet.service;

import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.repositroy.UserRepository;
import com.hackathon.hackathon.domain.wallet.dto.request.WalletRequestDTO;
import com.hackathon.hackathon.domain.wallet.dto.response.WalletResponseDTO;
import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import com.hackathon.hackathon.domain.wallet.enums.WalletStatus;
import com.hackathon.hackathon.domain.wallet.repository.WalletRepository;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hackathon.hackathon.domain.wallet.enums.WalletStatus.ACTIVE;
import static com.hackathon.hackathon.domain.wallet.enums.WalletStatus.DEACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;
    // 지갑 만들기
    @Transactional
    public SuccessResponse<Object> createWallet(User user, WalletRequestDTO walletRequestDTO) {
        Wallet wallet = Wallet.builder()
                .name(walletRequestDTO.getName())
                .user(user)
                .status(ACTIVE)
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

        return apiResponse;
    }

    public SuccessResponse<Object> getWallet(User user, Long walletId) {
        List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
        Optional<Wallet> wallet =  wallets.stream().filter(w -> walletId.equals(w.getId())).findFirst();
        return SuccessResponse.onSuccess(200,wallet.get());
    }

    public SuccessResponse<Object> getWallets(User user) {
        List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
        // status가 ACTIVE인 Wallet만 필터링
        List<Wallet> activeWallets = wallets.stream()
                .filter(wallet -> WalletStatus.ACTIVE.equals(wallet.getStatus()))
                .collect(Collectors.toList());
        return SuccessResponse.onSuccess(200,activeWallets);
    }


    @Transactional
    public SuccessResponse<Object> deleteWallet(User user,Long walletId) {
        List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
        Optional<Wallet> wallet =  wallets.stream().filter(w -> walletId.equals(w.getId())).findFirst();
        Wallet responseWallet = updateWalletStatus(wallet.get());
        return SuccessResponse.onSuccess(200,responseWallet);
    }


    private Wallet updateWalletStatus(Wallet wallet){
        wallet.updateStatus(DEACTIVE);
        walletRepository.save(wallet);
        return wallet;
    }

    public SuccessResponse<Object> getAllCardLinks(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).get();
        List<String> wallets = wallet.getImageUrls();
        return SuccessResponse.onSuccess(200,wallets);
    }

}

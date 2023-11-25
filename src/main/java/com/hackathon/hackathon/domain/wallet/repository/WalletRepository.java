package com.hackathon.hackathon.domain.wallet.repository;

import com.hackathon.hackathon.domain.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByUserId(Long id);

}
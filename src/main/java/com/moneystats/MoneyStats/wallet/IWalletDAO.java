package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletDAO extends JpaRepository<WalletEntity, Integer> {
    List<WalletEntity> findWalletsByCategoryId(Integer id);

    List<WalletEntity> findAllByUserId(Long userId);

    WalletEntity findWalletsById(Integer id);
}

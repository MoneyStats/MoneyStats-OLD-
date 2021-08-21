package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletDAO extends JpaRepository<Wallet, Integer> {
    List<Wallet> findWalletsByCategoryId(Integer id);

    List<Wallet> findAllByUserId(int userId);

    Wallet findWalletsById(int id);
}

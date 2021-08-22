package com.moneystats.MoneyStats.statement.DTO;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StatementDTO implements Serializable {

@NotNull private String date;
@NotNull private Double value;
@NotNull
    private User user;
@NotNull
    private WalletEntity walletEntity;

    public StatementDTO(@NotNull String date, @NotNull Double value, @NotNull User user, @NotNull WalletEntity walletEntity) {
        this.date = date;
        this.value = value;
        this.user = user;
        this.walletEntity = walletEntity;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    @NotNull
    public Double getValue() {
        return value;
    }

    public void setValue(@NotNull Double value) {
        this.value = value;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    @NotNull
    public WalletEntity getWalletEntity() {
        return walletEntity;
    }

    public void setWalletEntity(@NotNull WalletEntity walletEntity) {
        this.walletEntity = walletEntity;
    }
}

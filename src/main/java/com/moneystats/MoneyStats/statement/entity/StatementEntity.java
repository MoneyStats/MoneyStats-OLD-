package com.moneystats.MoneyStats.statement.entity;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;

import javax.persistence.*;

@Entity
@Table(name = "statements")
public class StatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;

    public StatementEntity(int id, String date, Double value, User user, WalletEntity wallet) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.user = user;
        this.wallet = wallet;
    }
    public StatementEntity(String date, Double value, User user, WalletEntity wallet) {
        this.date = date;
        this.value = value;
        this.user = user;
        this.wallet = wallet;
    }

    @Deprecated
    public StatementEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }
}

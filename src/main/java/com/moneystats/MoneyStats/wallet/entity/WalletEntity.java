package com.moneystats.MoneyStats.wallet.entity;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.model.Category;
import com.moneystats.MoneyStats.model.Statement;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wallets")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Transient
    private List<Statement> statementList;

    public WalletEntity(int id, String name, Category category, User user, List<Statement> statementList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.user = user;
        this.statementList = statementList;
    }
@Deprecated
    public WalletEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    public void setStatementList(List<Statement> statementList) {
        this.statementList = statementList;
    }
}

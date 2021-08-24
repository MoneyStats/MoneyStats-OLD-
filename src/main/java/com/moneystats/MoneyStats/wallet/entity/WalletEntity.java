package com.moneystats.MoneyStats.wallet.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.authentication.entity.AuthCredentialEntity;

@Entity
@Table(name = "wallets")
public class WalletEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private AuthCredentialEntity user;
	@Transient
	private List<StatementEntity> statementList;

	public WalletEntity(Integer id, String name, CategoryEntity category, AuthCredentialEntity user,
			List<StatementEntity> statementList) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.user = user;
		this.statementList = statementList;
	}

	@Deprecated
	public WalletEntity() {
	}

	public WalletEntity(String name, CategoryEntity category, AuthCredentialEntity user,
			List<StatementEntity> statementList) {
		this.name = name;
		this.category = category;
		this.user = user;
		this.statementList = statementList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public AuthCredentialEntity getUser() {
		return user;
	}

	public void setUser(AuthCredentialEntity user) {
		this.user = user;
	}

	public List<StatementEntity> getStatementList() {
		return statementList;
	}

	public void setStatementList(List<StatementEntity> statementList) {
		this.statementList = statementList;
	}
}

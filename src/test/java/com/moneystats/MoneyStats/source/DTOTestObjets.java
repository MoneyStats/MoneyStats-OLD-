package com.moneystats.MoneyStats.source;

import java.util.List;

import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;

public class DTOTestObjets {

	public static List<CategoryDTO> categoryDTOList = List.of(new CategoryDTO("Credit Card"),
			new CategoryDTO("Debit Card"), new CategoryDTO("Cash"), new CategoryDTO("Investment"));
	public static List<CategoryEntity> categoryEntities = List.of(new CategoryEntity(1, "Credit Card"),
			new CategoryEntity(2, "Debit Card"), new CategoryEntity(3, "Cash"), new CategoryEntity(4, "Investment"));

	public static List<WalletDTO> walletDTOS = List.of(
			new WalletDTO("My-Wallet-Name", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletDTO("My-Wallet-Name1", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletDTO("My-Wallet-Name2", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletDTO("My-Wallet-Name3", new CategoryEntity(1, "Credit Card"), null, null));
	public static List<WalletEntity> walletEntities = List.of(
			new WalletEntity(1, "My-Wallet-Name", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletEntity(2, "My-Wallet-Name1", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletEntity(3, "My-Wallet-Name2", new CategoryEntity(1, "Credit Card"), null, null),
			new WalletEntity(4, "My-Wallet-Name3", new CategoryEntity(1, "Credit Card"), null, null));

	public static WalletDTO walletDTO = new WalletDTO("My-Wallet-Name", new CategoryEntity(1, "Credit Card"), null,
			null);
}

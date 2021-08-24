package com.moneystats.MoneyStats.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;

@SpringBootTest
public class WalletServiceTest {

	@Mock
	private IWalletDAO walletDAO;
	@Mock
	private UtenteCRUD userDao;
	@InjectMocks
	private WalletService walletService;
	@Captor
	private ArgumentCaptor<Principal> principalCaptor;

	@Test
	void test_walletDTOlist_shouldReturnTheList() throws Exception {
		List<WalletDTO> walletDTO = DTOTestObjets.walletDTOS;
		List<WalletEntity> walletEntities = DTOTestObjets.walletEntities;
		Principal principal = principal();
		String username = principal.getName();
		User utente = new User(username, "my-password", null);

		Mockito.when(walletDAO.findAll()).thenReturn(walletEntities);

		Mockito.when(walletService.getAll(principal)).thenReturn(walletDTO);

		List<WalletDTO> actual = walletService.getAll(principal);
		for (int i = 0; i < actual.size(); i++) {
			assertEquals(walletDTO.get(i), actual.get(i));
		}
	}

	private Principal principal() {
		Principal principal = new Principal() {

			String name = "my-test-name";

			@Override
			public String getName() {
				return name;
			}
		};
		return principal;
	}

}

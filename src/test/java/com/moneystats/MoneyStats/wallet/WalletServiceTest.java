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

import com.moneystats.MoneyStats.source.DTOTestObjets;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import com.moneystats.authentication.DTO.TokenDTO;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = WalletService.class)
public class WalletServiceTest {

	private static final String TOKEN_JWT = "my-token-jwt";
	private static final String WRONG_TOKEN_JWT = "my-wrong-token-jwt";
	private static final TokenDTO USER_TOKEN_DTO = new TokenDTO(TOKEN_JWT);

	@Mock
	private IWalletDAO walletDAO;
	@InjectMocks
	private WalletService walletService;
	@Captor
	private ArgumentCaptor<Principal> principalCaptor;

	@Test
	void test_walletDTOlist_shouldReturnTheList() throws Exception {
		List<WalletDTO> walletDTO = DTOTestObjets.walletDTOS;
		List<WalletEntity> walletEntities = DTOTestObjets.walletEntities;

		Mockito.when(walletDAO.findAll()).thenReturn(walletEntities);

		Mockito.when(walletService.getAll(USER_TOKEN_DTO)).thenReturn(walletDTO);

		List<WalletDTO> actual = walletService.getAll(USER_TOKEN_DTO);
		for (int i = 0; i < actual.size(); i++) {
			assertEquals(walletDTO.get(i), actual.get(i));
		}
	}

}

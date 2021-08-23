package com.moneystats.MoneyStats.wallet;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponse;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WalletControllerTest {

	@MockBean
	private WalletService walletService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private Principal principal;

	@Test
	public void testGetAllWalletList_OK() throws Exception {
		List<WalletDTO> walletDTOS = DTOTestObjets.walletDTOS;
		String walletAsString = objectMapper.writeValueAsString(walletDTOS);
		Mockito.when(walletService.getAll(Mockito.any())).thenReturn(walletDTOS);

		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(walletAsString));
	}

	@Test
	public void testGetAllWalletList_shouldBeMappedUserNotFound() throws Exception {
		Mockito.when(walletService.getAll(Mockito.any()))
				.thenThrow(new WalletException(WalletException.Type.USER_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testGetAllWalletList_shouldBeMappedWalletDtoNull() throws Exception {
		Mockito.when(walletService.getAll(Mockito.any()))
				.thenThrow(new WalletException(WalletException.Type.WALLETDTO_NULL));

		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testAddWallet_OK() throws Exception {
		int idCategory = 1;
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		WalletResponseDTO response = new WalletResponseDTO(WalletResponse.Type.WALLET_ADDED.toString());

		Mockito.when(walletService.addWalletEntity(principal, idCategory, walletDTO)).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet/" + idCategory))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

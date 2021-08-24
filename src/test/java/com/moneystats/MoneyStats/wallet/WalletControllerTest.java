package com.moneystats.MoneyStats.wallet;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponse;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WalletControllerTest {

	@MockBean
	private WalletService walletService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Captor
	private ArgumentCaptor<Principal> principalCaptor;
	@Captor
	private ArgumentCaptor<WalletDTO> walletDTOCaptor;
	@Captor
	private ArgumentCaptor<Integer> idCategoryCaptor;

	@Test
	public void testGetAllWalletList_OK() throws Exception {
		List<WalletDTO> walletDTOS = DTOTestObjets.walletDTOS;
		String walletAsString = objectMapper.writeValueAsString(walletDTOS);
		Mockito.when(walletService.getAll(principalCaptor.capture())).thenReturn(walletDTOS);

		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(walletAsString));
	}

	@Test
	public void testAddWallet_OK() throws Exception {
		Integer idCategory = 1;
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		String walletDTOasString = objectMapper.writeValueAsString(walletDTO);
		WalletResponseDTO response = new WalletResponseDTO(WalletResponse.Type.WALLET_ADDED.toString());

		Mockito.when(walletService.addWalletEntity(principalCaptor.capture(), idCategoryCaptor.capture(),
				walletDTOCaptor.capture())).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet/" + idCategory)
				.contentType(MediaType.APPLICATION_JSON).content(walletDTOasString))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testAddWallet_shouldBeMappedOnError() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		walletDTO.setName(null);
		Integer idCategory = 1;
		Principal principal = null;

		Mockito.when(walletService.addWalletEntity(principal, idCategory, walletDTO))
				.thenThrow(new WalletException(WalletException.Type.INVALID_WALLET_DTO));

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet/" + idCategory))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testAddWallet_shouldBeMappedUserNotFound() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		Integer idCategory = 1;
		Principal principal = null;

		Mockito.when(walletService.addWalletEntity(principal, idCategory, walletDTO))
				.thenThrow(new WalletException(WalletException.Type.USER_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testAddWalletList_shouldBeMappedWalletDtoNull() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		Integer idCategory = null;
		Principal principal = null;

		Mockito.when(walletService.addWalletEntity(principal, idCategory, walletDTO))
				.thenThrow(new WalletException(WalletException.Type.CATEGORY_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testDeleteWallet_OK() throws Exception {
		Integer idWallet = 1;
		WalletResponseDTO response = new WalletResponseDTO(WalletResponse.Type.WALLET_DELETED.toString());
		String responseAsString = objectMapper.writeValueAsString(response);

		Mockito.when(walletService.deleteWalletEntity(idWallet)).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.delete("/wallet/delete/" + idWallet))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(responseAsString));
	}

	@Test
	public void testDeleteWallet_shouldReturnWalletNotFound() throws Exception {
		Integer idWallet = 1;

		Mockito.when(walletService.deleteWalletEntity(idWallet))
				.thenThrow(new WalletException(WalletException.Type.WALLET_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.delete("/wallet/delete/" + idWallet))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testDeleteWallet_shouldReturnCategoryNotFound() throws Exception {
		Integer idWallet = 1;

		Mockito.when(walletService.deleteWalletEntity(idWallet))
				.thenThrow(new WalletException(WalletException.Type.CATEGORY_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.delete("/wallet/delete/" + idWallet))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}

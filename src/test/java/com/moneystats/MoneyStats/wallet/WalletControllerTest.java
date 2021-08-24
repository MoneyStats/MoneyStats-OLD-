package com.moneystats.MoneyStats.wallet;

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
import com.moneystats.authentication.DTO.TokenDTO;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = WalletController.class)
@AutoConfigureMockMvc
public class WalletControllerTest {

	private static final String TOKEN_JWT = "my-token-jwt";
	private static final String WRONG_TOKEN_JWT = "my-wrong-token-jwt";
	private static final TokenDTO USER_TOKEN_DTO = new TokenDTO(TOKEN_JWT);

	@MockBean
	private WalletService walletService;
	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();
	@Captor
	private ArgumentCaptor<WalletDTO> walletDTOCaptor;
	@Captor
	private ArgumentCaptor<Integer> idCategoryCaptor;

	@Test
	public void testGetAllWalletList_OK() throws Exception {
		List<WalletDTO> walletDTOS = DTOTestObjets.walletDTOS;
		String walletAsString = objectMapper.writeValueAsString(walletDTOS);
		Mockito.when(walletService.getAll(USER_TOKEN_DTO)).thenReturn(walletDTOS);

		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list").header("Authorization", "Bearer " + USER_TOKEN_DTO))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(walletAsString));
	}

	@Test
	public void testAddWallet_OK() throws Exception {
		Integer idCategory = 1;
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		String walletDTOasString = objectMapper.writeValueAsString(walletDTO);
		WalletResponseDTO response = new WalletResponseDTO(WalletResponse.Type.WALLET_ADDED.toString());

		Mockito.when(
				walletService.addWalletEntity(USER_TOKEN_DTO, idCategoryCaptor.capture(), walletDTOCaptor.capture()))
				.thenReturn(response);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/wallet/postWallet/" + idCategory).contentType(MediaType.APPLICATION_JSON)
						.content(walletDTOasString).header("Authorization", "Bearer " + USER_TOKEN_DTO))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testAddWallet_shouldBeMappedOnError() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		walletDTO.setName(null);
		Integer idCategory = 1;

		Mockito.when(walletService.addWalletEntity(USER_TOKEN_DTO, idCategory, walletDTO))
				.thenThrow(new WalletException(WalletException.Type.INVALID_WALLET_DTO));

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet/" + idCategory))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testAddWallet_shouldBeMappedUserNotFound() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		Integer idCategory = 1;

		Mockito.when(walletService.addWalletEntity(USER_TOKEN_DTO, idCategory, walletDTO))
				.thenThrow(new WalletException(WalletException.Type.USER_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.post("/wallet/postWallet"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testAddWalletList_shouldBeMappedWalletDtoNull() throws Exception {
		WalletDTO walletDTO = DTOTestObjets.walletDTO;
		Integer idCategory = null;

		Mockito.when(walletService.addWalletEntity(USER_TOKEN_DTO, idCategory, walletDTO))
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

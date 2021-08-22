package com.moneystats.MoneyStats.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @MockBean private WalletService walletService;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllWalletList_OK() throws Exception{
        List<WalletDTO> walletDTOS = DTOTestObjets.walletDTOS;
        String walletAsString = objectMapper.writeValueAsString(walletDTOS);
        Mockito.when(walletService.getAll(Mockito.any())).thenReturn(walletDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(walletAsString));
    }
}

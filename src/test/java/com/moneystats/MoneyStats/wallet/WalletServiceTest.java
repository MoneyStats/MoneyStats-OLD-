package com.moneystats.MoneyStats.wallet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WalletServiceTest {

	@Mock
	IWalletDAO walletDAO;
	@InjectMocks
	WalletService walletService;

}

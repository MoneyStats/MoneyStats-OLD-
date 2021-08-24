package com.moneystats.MoneyStats.wallet;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import com.moneystats.authentication.AuthenticationException;
import com.moneystats.authentication.DTO.TokenDTO;
import org.springframework.web.bind.annotation.*;

import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Inject
	private WalletService walletService;

	@GetMapping("/list")
	public List<WalletDTO> getAll(@RequestHeader(value = "Authorization") String jwt) throws WalletException, AuthenticationException {
		TokenDTO tokenDTO = new TokenDTO(jwt);
		return walletService.getAll(tokenDTO);
	}

	@PostMapping("/postWallet/{idCategory}")
	public WalletResponseDTO addWallet(@RequestHeader(value = "Authorization") String jwt, @PathVariable int idCategory,
			@RequestBody WalletDTO walletDTO) throws WalletException, AuthenticationException {
		TokenDTO tokenDTO = new TokenDTO(jwt);
		return walletService.addWalletEntity(tokenDTO, idCategory, walletDTO);
	}

	@DeleteMapping("/delete/{idWallet}")
	public WalletResponseDTO deleteWallet(@PathVariable int idWallet) throws WalletException {
		return walletService.deleteWalletEntity(idWallet);
	}
}

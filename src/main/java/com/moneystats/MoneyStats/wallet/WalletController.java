package com.moneystats.MoneyStats.wallet;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Inject
	private WalletService walletService;

	@GetMapping("/list")
	public List<WalletDTO> getAll(Principal principal) throws WalletException {
		return walletService.getAll(principal);
	}

	@PostMapping("/postWallet/{idCategory}")
	public WalletResponseDTO addWallet(Principal principal, @PathVariable int idCategory,
			@RequestBody WalletDTO walletDTO) throws WalletException {
		Integer idCategoryService = idCategory;
		return walletService.addWalletEntity(principal, idCategoryService, walletDTO);
	}

	@DeleteMapping("/delete/{idWallet}")
	public WalletResponseDTO deleteWallet(@PathVariable int idWallet) throws WalletException {
		return walletService.deleteWalletEntity(idWallet);
	}
}

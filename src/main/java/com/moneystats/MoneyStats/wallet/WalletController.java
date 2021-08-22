package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.category.ICategoryDAO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.statement.IStatementDAO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Inject WalletService walletService;

    @GetMapping("/list")
    public List<WalletDTO> getAll(Principal principal) throws WalletException {
        return walletService.getAll(principal);
    }

    @PostMapping("/postWallet/{idCategory}")
    public WalletResponseDTO addWallet(Principal principal, @PathVariable int idCategory, @RequestBody WalletDTO walletDTO) throws WalletException {
        return walletService.addWalletEntity(principal, idCategory, walletDTO);
    }

    @DeleteMapping("/delete/{id}")
    public WalletResponseDTO deleteWallet(@PathVariable int idWallet) throws WalletException {
        return walletService.deleteWalletEntity(idWallet);
    }
}

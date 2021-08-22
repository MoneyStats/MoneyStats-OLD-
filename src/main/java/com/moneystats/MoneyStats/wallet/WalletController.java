package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.category.ICategoryDAO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.statement.IStatementDAO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Inject
    IWalletDAO walletDAO;

    @Inject
    ICategoryDAO categoryDAO;

    @Inject
    IStatementDAO statementDAO;

    @Inject
    UtenteCRUD utenteCRUD;

    @GetMapping("/list")
    public List<WalletEntity> getAll(Principal principal){
        String username = principal.getName();
        User utente = (User) utenteCRUD.findByUsername(username).orElse(null);
        return walletDAO.findAllByUserId(utente.getId());
    }

    @PostMapping("/postWallet/{idcategory}")
    public void addWallet(Principal principal, @PathVariable int idcategory, @RequestBody WalletEntity wallet){
        String username = principal.getName();
        User utente = (User) utenteCRUD.findByUsername(username).orElse(null);
        wallet.setUser(utente);
        CategoryEntity category = categoryDAO.findById(idcategory).orElse(null);
        wallet.setCategory(category);
        if (wallet != null){
            WalletEntity wallet1 = walletDAO.save(wallet);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWallet(@PathVariable int id){
        WalletEntity wallet = walletDAO.findById(id).orElse(null);
        wallet.setStatementList(statementDAO.findStatementByWalletId(wallet.getId()));
        if (wallet.getStatementList() != null){
            for (StatementEntity s : wallet.getStatementList()) {
                statementDAO.deleteById(s.getId());
            }
        }
        walletDAO.deleteById(id);
    }
}

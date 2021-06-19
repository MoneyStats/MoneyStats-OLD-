package com.moneystats.MoneyStats.controller;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.model.Category;
import com.moneystats.MoneyStats.model.Statement;
import com.moneystats.MoneyStats.model.Wallet;
import com.moneystats.MoneyStats.repositoryCRUD.ICategoryCRUD;
import com.moneystats.MoneyStats.repositoryCRUD.IStatementCRUD;
import com.moneystats.MoneyStats.repositoryCRUD.IWalletCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class ControllerWallet {

    @Autowired
    IWalletCRUD walletGEST;

    @Autowired
    ICategoryCRUD categoryGEST;

    @Autowired
    IStatementCRUD statementCRUD;

    @Autowired
    UtenteCRUD utenteCRUD;

    @GetMapping("/list")
    public List<Wallet> getAll(Principal principal){
        String username = principal.getName();
        User utente = (User) utenteCRUD.findByUsername(username).orElse(null);
        return walletGEST.findAllByUserId(utente.getId());
    }

    @PostMapping("/postWallet/{idcategory}")
    public void addWallet(Principal principal, @PathVariable int idcategory, @RequestBody Wallet wallet){
        String username = principal.getName();
        User utente = (User) utenteCRUD.findByUsername(username).orElse(null);
        wallet.setUser(utente);
        Category category = categoryGEST.findById(idcategory).orElse(null);
        wallet.setCategory(category);
        if (wallet != null){
            Wallet wallet1 = walletGEST.save(wallet);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWallet(@PathVariable int id){
        Wallet wallet = walletGEST.findById(id).orElse(null);
        wallet.setStatementList(statementCRUD.findStatementByWalletId(wallet.getId()));
        if (wallet.getStatementList() != null){
            for (Statement s : wallet.getStatementList()) {
                statementCRUD.deleteById(s.getId());
            }
        }
        walletGEST.deleteById(id);
    }
}

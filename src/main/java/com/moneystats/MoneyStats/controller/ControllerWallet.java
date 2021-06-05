package com.moneystats.MoneyStats.controller;

import com.moneystats.MoneyStats.model.Category;
import com.moneystats.MoneyStats.model.Wallet;
import com.moneystats.MoneyStats.repositoryCRUD.ICategoryCRUD;
import com.moneystats.MoneyStats.repositoryCRUD.IWalletCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class ControllerWallet {

    @Autowired
    IWalletCRUD walletGEST;

    @Autowired
    ICategoryCRUD categoryGEST;

    @GetMapping("/list")
    public List<Wallet> getAll(){
        return walletGEST.findAll();
    }

    @PostMapping("/postWallet/{idcategory}")
    public void addWallet(@PathVariable int idcategory, @RequestBody Wallet wallet){
        Category category = categoryGEST.findById(idcategory).orElse(null);
        wallet.setCategory(category);
        Wallet wallet1 = walletGEST.save(wallet);
    }
}

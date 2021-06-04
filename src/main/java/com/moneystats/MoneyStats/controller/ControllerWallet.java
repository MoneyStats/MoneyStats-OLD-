package com.moneystats.MoneyStats.controller;

import com.moneystats.MoneyStats.model.Wallet;
import com.moneystats.MoneyStats.repositoryCRUD.IWalletCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class ControllerWallet {

    @Autowired
    IWalletCRUD walletGEST;

    @GetMapping("/list")
    public List<Wallet> getAll(){
        return walletGEST.findAll();
    }
}

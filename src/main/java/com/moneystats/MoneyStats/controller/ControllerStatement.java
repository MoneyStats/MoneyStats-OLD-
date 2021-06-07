package com.moneystats.MoneyStats.controller;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.model.Statement;
import com.moneystats.MoneyStats.model.Wallet;
import com.moneystats.MoneyStats.repositoryCRUD.IStatementCRUD;
import com.moneystats.MoneyStats.repositoryCRUD.IWalletCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/statement")
public class ControllerStatement {

    @Autowired
    UtenteCRUD utenteGEST;

    @Autowired
    IStatementCRUD statementGEST;

    @Autowired
    IWalletCRUD walletGEST;


    @PostMapping("/post")
    public void addStatement(Principal principal, @RequestBody Statement statement){
        String username = principal.getName();
        User utente = (User) utenteGEST.findByUsername(username).orElse(null);
        statement.setUser(utente);
        Wallet wallet = walletGEST.findById(statement.getWallet().getId()).orElse(null);
        statement.setWallet(wallet);
        if (statement != null){
            Statement stat = statementGEST.save(statement);
        }
    }

    @GetMapping("/datestatement")
     public List<String> listDate(Principal principal){
        String username = principal.getName();
        User utente = (User) utenteGEST.findByUsername(username).orElse(null);
        for (int i = 0; i < statementGEST.selectdistinctstatement(utente.getId()).size(); i++){
            System.out.println(statementGEST.selectdistinctstatement(utente.getId()));
            return statementGEST.selectdistinctstatement(utente.getId());
        }
        return null;
    }

    @GetMapping("/statementbydate/{date}")
    public List<Statement> listByDate(Principal principal, @PathVariable String date){
        String username = principal.getName();
        User utente = (User) utenteGEST.findByUsername(username).orElse(null);

        List<Statement> statementList = statementGEST.findAllByUserIdAndDate(utente.getId(), date);
        return statementList;
    }
}

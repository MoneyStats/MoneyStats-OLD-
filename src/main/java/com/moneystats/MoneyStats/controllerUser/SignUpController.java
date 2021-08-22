package com.moneystats.MoneyStats.controllerUser;

import com.moneystats.MoneyStats.auth.Authservice;
import com.moneystats.MoneyStats.auth.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class SignUpController {

    @Inject
    private Authservice authService;

    /*@PostMapping("/signup/add")
    public void singup(@RequestParam String nome, @RequestParam String cognome, @RequestParam String datadinascita, @RequestParam String email, @RequestParam String username, @RequestParam String password){
        authService.signup(nome, cognome, datadinascita, email, username, password);
    }*/
    @PostMapping("/signup/add")
    public void singup(@RequestBody User utente) {
        System.out.println("sono dentro");
        authService.signup(utente.getNome(), utente.getCognome(), utente.getDatadinascita(), utente.getEmail(), utente.getUsername(), utente.getPassword());
    }
}

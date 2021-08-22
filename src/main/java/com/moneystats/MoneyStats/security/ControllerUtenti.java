package com.moneystats.MoneyStats.security;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ControllerUtenti {

    @Inject
    UtenteCRUD utenteGEST;


    @GetMapping("/utenti")
    public List<User> elencoutenti() {
        List<User> elenco = utenteGEST.findByRuolo("USER");
        return elenco;
    }

    @GetMapping("/utenti/{id}")
    User user(@PathVariable int id) {

        return utenteGEST.findById(id).orElse(null);

    }

    @PutMapping("/utenti/modifica")
    public void modifica(@RequestBody User usermodifica) {
        User user = utenteGEST.findById(usermodifica.getId()).orElse(null);
        usermodifica.setPassword(user.getPassword());
        usermodifica.setRuolo(user.getRuolo());
        utenteGEST.save(usermodifica);
    }

}
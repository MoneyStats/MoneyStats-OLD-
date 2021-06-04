package com.moneystats.MoneyStats.controllerUser;

import com.moneystats.MoneyStats.auth.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secured")
public class SecuredController {

    @GetMapping
    public User detail(@AuthenticationPrincipal User utente) {
        return utente;
    }
}

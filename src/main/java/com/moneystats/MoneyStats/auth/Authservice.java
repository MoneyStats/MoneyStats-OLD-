package com.moneystats.MoneyStats.auth;

import com.moneystats.MoneyStats.security.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class Authservice implements UserDetailsService {

    private final UtenteCRUD dao;
    private final PasswordEncoder passwordEncoder;

    @Inject
    public Authservice(UtenteCRUD dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<? extends UserDetails> user = dao.findByUsername(username);

        if (user.isPresent()) return user.get();

        throw new UsernameNotFoundException("Utente inesistente");
    }

    public void signup(String nome, String cognome, String ddn, String email, String username, String password) {

        User newUser = new User();
        newUser.setNome(nome);
        newUser.setCognome(cognome);
        newUser.setDatadinascita(ddn);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRuolo(Roles.USER);

        try {
            dao.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserId(Authentication authentication, int id) {

        User utente = (User) dao.findByUsername(authentication.getName()).orElse(null);

        if (utente != null) {
            return (utente.getId() == id);
        } else {
            return false;
        }
    }

    public Optional<User> findById(int id) {
        return dao.findById(id);
    }

}

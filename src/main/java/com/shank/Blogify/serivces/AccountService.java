package com.shank.Blogify.serivces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shank.Blogify.models.Account;
import com.shank.Blogify.models.Authority;
import com.shank.Blogify.repositories.AccountRepository;
import com.shank.Blogify.util.constants.Roles;

@Service                                                                          //^ Ye annotation indicate karta hai ki ye class ek service component hai jo business logic ko handle karta hai. Helps in making the class a Spring bean. Controller aur Repository ke beech mein kaam karta hai.    
public class AccountService implements UserDetailsService{
    @Autowired                                                          //^ Ye annotation Spring ko batata hai ki wo is dependency ko automatically inject kare. ek instance of AccountRepository provide kare with the help of Spring bean container.
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account account) {

        Optional<Account> existing = accountRepository
                .findOneByEmailIgnoreCase(account.getEmail());
        // 🚫 Email uniqueness check
        if (existing.isPresent()) {
            // NEW account trying to use existing email
            if (account.getId() == null) {
                throw new RuntimeException("Email already exists");
            }
            // UPDATE but email belongs to someone else
            if (!existing.get().getId().equals(account.getId())) {
                throw new RuntimeException("Email already exists");
            }
        }
        // 🔐 Encode password only if needed
        if (account.getPassword() != null &&
            !account.getPassword().startsWith("$2a$")) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        // 🎭 Default role
        if (account.getRole() == null) {
            account.setRole(Roles.USER.getRole());
        }
        // 🖼 Default photo
        if (account.getPhoto() == null) {
            account.setPhoto("/images/default.png");
        }
        return accountRepository.save(account);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(username);
        if(!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account = optionalAccount.get();

        List<GrantedAuthority> authorities = new ArrayList<>();                     //& GrantedAuthority represent karta hai ek authority ya role jo user ke paas hai.
        authorities.add(new SimpleGrantedAuthority(account.getRole()));              //& Ye line account ke role ko authorities list mein add karti hai.    

        for(Authority _auth : account.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(_auth.getName()));              //& Ye loop account ke har authority ko authorities list mein add karta hai.
        }

        return new User(
            account.getEmail(), account.getPassword(), authorities);
    }

    public Optional<Account> findOneByEmail(String email) {
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByPasswordResetToken(String password_reset_token) {
        return accountRepository.findByPasswordResetToken(password_reset_token);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    public long count() {
        return accountRepository.count();
    }
}

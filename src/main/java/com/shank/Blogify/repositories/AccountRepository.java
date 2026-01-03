package com.shank.Blogify.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shank.Blogify.models.Account;

//^ Ye annotation indicate karta hai ki ye interface ek Spring Data repository hai.
//^ Repository database se baat karta hai. SQL queries ko handle karta hai.

@Repository                                                                 
public interface AccountRepository extends JpaRepository<Account, Long> {        //^ JpaRepository<Account, Long> ka matlab hai ki ye repository Account entity ke liye hai jiska primary key Long type ka hai.
    Optional<Account> findOneByEmailIgnoreCase(String email);                   //^ Ye method database mein email ke basis par account ko find karta hai, case insensitive way mein.

    //^ Optional ka matlab hai ki method ya to ek Account return karega ya kuch bhi nahi (null) agar account nahi mila.saves from null pointer exceptions.

    Optional<Account> findByPasswordResetToken(String passwordResetToken);

}

package com.shank.Blogify.serivces;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shank.Blogify.models.Authority;
import com.shank.Blogify.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    
    @Autowired
    private AuthorityRepository authorityRepository;

    @SuppressWarnings("null")
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @SuppressWarnings("null")
    public Optional<Authority> findById (Long id) {
        return authorityRepository.findById(id);
    }    
}

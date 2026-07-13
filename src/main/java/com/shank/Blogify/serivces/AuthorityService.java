package com.shank.Blogify.serivces;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shank.Blogify.models.Authority;
import com.shank.Blogify.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    
    private final AuthorityRepository authorityRepository;

    AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @SuppressWarnings("null")
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @SuppressWarnings("null")
    public Optional<Authority> findById (Long id) {
        return authorityRepository.findById(id);
    }    
}

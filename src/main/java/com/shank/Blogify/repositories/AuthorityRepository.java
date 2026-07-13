package com.shank.Blogify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shank.Blogify.models.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
} 

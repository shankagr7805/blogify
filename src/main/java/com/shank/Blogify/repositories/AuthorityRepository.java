package com.shank.Blogify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shank.Blogify.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
} 

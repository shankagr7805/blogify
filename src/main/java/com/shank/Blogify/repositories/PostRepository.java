package com.shank.Blogify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shank.Blogify.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}

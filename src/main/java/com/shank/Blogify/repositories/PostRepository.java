package com.shank.Blogify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shank.Blogify.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}

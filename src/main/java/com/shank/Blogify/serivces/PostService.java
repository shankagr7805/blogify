package com.shank.Blogify.serivces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.shank.Blogify.models.Post;
import com.shank.Blogify.repositories.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;  

    public Optional<Post> getbyId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return postRepository.findById(id);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Page<Post> findAllPosts(int offset, int pageSize, String field) {
        return postRepository.findAll(PageRequest.of(offset, pageSize).withSort(Direction.ASC, field));
    }

    @SuppressWarnings("null")
    public void delete (Post post) {
       postRepository.delete(post);
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }   

    public Post save(Post post) {
        if(post.getId()==null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
}    

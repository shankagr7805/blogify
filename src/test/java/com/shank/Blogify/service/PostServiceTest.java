package com.shank.Blogify.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shank.Blogify.models.Post;
import com.shank.Blogify.repositories.PostRepository;
import com.shank.Blogify.serivces.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void getById_shouldReturnEmpty_whenIdIsNull() {
        Optional<Post> result = postService.getbyId(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldSetCreatedAt_whenNewPost() {
        Post post = new Post();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.save(post);

        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getUpdatedAt());
    }

    @Test
    void save_shouldNotOverwriteCreatedAt_whenExistingPost() {
        Post post = new Post();
        post.setId(1L);
        LocalDateTime original = LocalDateTime.now().minusDays(1);
        post.setCreatedAt(original);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.save(post);

        assertEquals(original, post.getCreatedAt());
    }
}

package com.shank.Blogify.Controller;

import com.shank.Blogify.models.Post;
import com.shank.Blogify.serivces.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class PostRestController {

    @Autowired
    private PostService postService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ── GET /api/v1/posts  (paginated, Redis-cached) ──────────
    @SuppressWarnings("unchecked")
    @GetMapping("/posts")
    public Map<String, Object> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int per_page,
            @RequestParam(defaultValue = "createdAt") String sort_by) {

        String cacheKey = "posts::page=" + page + "&per_page=" + per_page + "&sort=" + sort_by;

        // 1. Try Redis first
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            log.info("CACHE HIT  — {}", cacheKey);
            return (Map<String, Object>) cached;
        }

        // 2. Miss — query DB with pagination
        log.info("CACHE MISS — {}", cacheKey);
        Page<Post> postsPage = postService.findAllPosts(page - 1, per_page, sort_by);
        List<Post> posts = postsPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("total_pages", postsPage.getTotalPages());
        response.put("total_elements", postsPage.getTotalElements());
        response.put("current_page", page);

        // 3. Write to Redis with 10 min TTL
        redisTemplate.opsForValue().set(cacheKey, response, 10, TimeUnit.MINUTES);
        return response;
    }

    // ── Cache eviction on write ────────────────────────────────
    @GetMapping("/posts/evict-cache")
    public String evictCache() {
        redisTemplate.delete(redisTemplate.keys("posts::*"));
        log.info("Posts cache evicted");
        return "Cache evicted";
    }
}

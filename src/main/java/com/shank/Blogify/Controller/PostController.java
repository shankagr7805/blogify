package com.shank.Blogify.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import com.shank.Blogify.models.Account;
import com.shank.Blogify.models.Post;
import com.shank.Blogify.serivces.AccountService;
import com.shank.Blogify.serivces.PostService;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    public AccountService accountService;

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> optionalpost = postService.getbyId(id);
        String authUser = "email";
        if (optionalpost.isPresent()) {
            Post post = optionalpost.get();
            model.addAttribute("post", post);
            
            //& To get the logged-in user's email we can do this
            //& String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            if(principal != null) {
                authUser = principal.getName();
            }
            if (authUser.equals(post.getAccount().getEmail())) {
                model.addAttribute("isOwner", true);
            } else {
                model.addAttribute("isOwner", false);
            }
            return "post_views/post";
        } else {
            return "404";
        }
    }

    @GetMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model, Principal principal) {
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        } else {
            return "redirect:/";
        } 
    }

    @PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "post_views/post_add";
        }
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0) {
            return "redirect:/?error";
        }
        postService.save(post);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/edit_post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model) {
        Optional<Post> optionalpost = postService.getbyId(id);
        if (optionalpost.isPresent()) {
            Post post = optionalpost.get();
            model.addAttribute("post", post);
            return "post_views/post_edit";    
        } else {
            return "404";
        }         
    }

    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@Valid @ModelAttribute Post post, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "post_views/post_edit";
        }
        Optional<Post> optionalpost = postService.getbyId(id);
        if(optionalpost.isPresent()) {
            Post existingPost = optionalpost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            postService.save(existingPost);                 // Save the updated post
        }
        return "redirect:/post/" + post.getId();
    }  
    
    @GetMapping("/post/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id) {
        Optional<Post> optionalpost = postService.getbyId(id);
        if(optionalpost.isPresent()) {
            Post post = optionalpost.get();
            postService.delete(post);
            return "redirect:/";
        } else {
            return "redirect:/?error";
        }
    }
}

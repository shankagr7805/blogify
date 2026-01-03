package com.shank.Blogify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shank.Blogify.serivces.AccountService;
import com.shank.Blogify.serivces.PostService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        model.addAttribute("users", accountService.findAll());
        return "admin_views/admin";
    }

    // Delete ANY post
    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/admin";
    }

    // Promote user to ADMIN
    @GetMapping("/user/{id}/make-admin")
    public String makeAdmin(@PathVariable long id) {
        accountService.findById(id).ifPresent(acc -> {
            acc.setRole("ROLE_ADMIN");
            accountService.save(acc);
        });
        return "redirect:/admin";
    }
}

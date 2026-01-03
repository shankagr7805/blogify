package com.shank.Blogify.config;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shank.Blogify.models.Account;
import com.shank.Blogify.models.Authority;
import com.shank.Blogify.models.Post;
import com.shank.Blogify.serivces.AccountService;
import com.shank.Blogify.serivces.AuthorityService;
import com.shank.Blogify.serivces.PostService;
import com.shank.Blogify.util.constants.Privilages;
import com.shank.Blogify.util.constants.Roles;

@Component                                                             //^Spring component banaya taaki ye app start hote hi scan ho jaye
public class SeedData implements CommandLineRunner {                    //^App start hote hi default data insert hoga db me
    @Autowired                                                           //^CommandLineRunner interface implement kiya taaki run method override kar saku
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) {

        // 1. Seed authorities safely
        for (Privilages priv : Privilages.values()) {
            authorityService.findById(priv.getId()).orElseGet(() -> {
                Authority authority = new Authority();
                authority.setId(priv.getId());
                authority.setName(priv.getAuthorityString());
                return authorityService.save(authority);
            });
        }

        // 2. Seed accounts safely
        Account user = accountService.findOneByEmail("user@user.com")
                .orElseGet(() -> {
                    Account acc = new Account();
                    acc.setEmail("user@user.com");
                    acc.setPassword("pass987");
                    acc.setFirstname("user");
                    acc.setLastname("lastname");
                    acc.setAge(25);
                    acc.setDob(LocalDate.parse("1998-05-20"));
                    acc.setGender("Male");
                    return accountService.save(acc);
                });

        Account admin = accountService.findOneByEmail("admin@admin.com")
                .orElseGet(() -> {
                    Account acc = new Account();
                    acc.setEmail("admin@admin.com");
                    acc.setPassword("pass987");
                    acc.setFirstname("admin");
                    acc.setLastname("lastname");
                    acc.setRole(Roles.ADMIN.getRole());
                    acc.setAge(30);
                    acc.setDob(LocalDate.parse("1993-03-15"));
                    acc.setGender("Male");
                    return accountService.save(acc);
                });

        // 3. Seed posts safely
        if (postService.findAllPosts().isEmpty()) {
            Post post1 = new Post();
            post1.setTitle("Welcome to Blogify");
            post1.setBody("<img src=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg/250px-An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg\" decoding=\"async\" width=\"190\" height=\"254\" class=\"mw-file-element\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg/330px-An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg 1.5x, //upload.wikimedia.org/wikipedia/commons/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg 2x\" data-file-width=\"376\" data-file-height=\"502\"> <p>This is the first post by user.</p>");
            post1.setAccount(user);
            postService.save(post1);

            Post post2 = new Post();
            post2.setTitle("Welcome to Blogify");
            post2.setBody("<img src=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg/250px-An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg\" decoding=\"async\" width=\"190\" height=\"254\" class=\"mw-file-element\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg/330px-An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg 1.5x, //upload.wikimedia.org/wikipedia/commons/d/d6/An_Oberoi_Hotel_employee_doing_Namaste%2C_New_Delhi.jpg 2x\" data-file-width=\"376\" data-file-height=\"502\"> <p>This is the first post by admin.</p>");
            post2.setAccount(admin);
            postService.save(post2);            
        }
    }
}
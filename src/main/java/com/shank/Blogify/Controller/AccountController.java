package com.shank.Blogify.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shank.Blogify.models.Account;
import com.shank.Blogify.serivces.AccountService;
import com.shank.Blogify.serivces.EmailService;
import com.shank.Blogify.util.email.EmailDetails;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Cloudinary cloudinary;

    @Value("${password.token.reset.timeout.minutes}")
    private int password_token_timeout;

    @Value("${site.domain}")
    private String site_domain;

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();    
        model.addAttribute("account", account);
        return "account_views/register";
    }  
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute Account account , BindingResult result) {
        if(result.hasErrors()) {
            return "account_views/register";
        }
        accountService.save(account);
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        return "account_views/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model , Principal principal) {
        String authUser = "email";
        if(principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account" , account);
            model.addAttribute("photo" , account.getPhoto());
            return "account_views/profile";
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String post_profile(@Valid @ModelAttribute Account account , BindingResult bindingResult, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "account_views/profile";
        }
        String authUser = "email";
        if(principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()) {
            Account account_by_id = accountService.findById(account.getId()).get();
            account_by_id.setFirstname(account.getFirstname());
            account_by_id.setLastname(account.getLastname());
            account_by_id.setGender(account.getGender());
            account_by_id.setAge(account.getAge());
            account_by_id.setDob(account.getDob());
            account_by_id.setPassword(account.getPassword());

            accountService.save(account_by_id);
            new SecurityContextLogoutHandler().logout(request, response, null);
            return "redirect:/";
        }

        return "redirect:/?error";
    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")
    public String updatePhoto(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attributes,
            Principal principal
    ) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "No file uploaded");
            return "redirect:/profile";
        }

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "blogify/profile",
                                    "resource_type", "image"
                            )
                    );

            String imageUrl = uploadResult.get("secure_url").toString();

            Account account = accountService.findOneByEmail(principal.getName())
                    .orElseThrow();

            account.setPhoto(imageUrl);
            accountService.save(account);

            attributes.addFlashAttribute("message", "Photo updated successfully");
            return "redirect:/profile";

        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Photo upload failed");
            return "redirect:/profile";
        }
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile file) {

        if (file.isEmpty()) {
            return Map.of(
                "uploaded", false,
                "error", Map.of("message", "Empty file")
            );
        }

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "blogify/posts",
                                    "resource_type", "image"
                            )
                    );

            String imageUrl = uploadResult.get("secure_url").toString();

            return Map.of(
                "uploaded", true,
                "url", imageUrl
            );

        } catch (Exception e) {
            return Map.of(
                "uploaded", false,
                "error", Map.of("message", "Image upload failed")
            );
        }
    }

    @GetMapping("/forgot-password")
    public String forgot_password(Model model) {
        return "account_views/forgot_password";
    }

    @PostMapping("/reset-password")
    public String reset_password(@RequestParam("email") String _email, RedirectAttributes attributes, Model model) {
        Optional<Account> optional_account = accountService.findOneByEmail(_email);
        if(optional_account.isPresent()){
            Account account = accountService.findById(optional_account.get().getId()).get();
            String reset_token = UUID.randomUUID().toString();
            account.setPasswordResetToken(reset_token);
            account.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
            accountService.save(account);

            String reset_message = "This is the reset password link: "+site_domain+"change-password?token="+reset_token;
            EmailDetails emailDetails = new EmailDetails(account.getEmail(), reset_message, "Reset Password Demo");

            if(emailService.sendSimpleEmail(emailDetails) == false) {
                attributes.addFlashAttribute("message" , "Error while sending email , contact admin.");
                return "redirect:/forgot-password";
            }
            attributes.addFlashAttribute("message", "Password reset email sent");
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("message" , "No user found with the provided email.");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/change-password")
    public String change_password(@RequestParam("token") String token, RedirectAttributes attributes, Model model) {
        if(token.equals("")) {
            attributes.addFlashAttribute("error" , "Invalid token");
            return "redirect:/forgot-password";
        }
        
        Optional<Account> optional_account = accountService.findByPasswordResetToken(token);

        if(optional_account.isPresent()) {
            Account account = accountService.findById(optional_account.get().getId()).get();
            
            LocalDateTime now = LocalDateTime.now();
            if(now.isAfter(optional_account.get().getPassword_reset_token_expiry())) {
                attributes.addFlashAttribute("error" , "Token expired");
                return "redirect:/forgot-password";
            }
            model.addAttribute("account", account);
            return "account_views/change_password";
        }
        attributes.addFlashAttribute("error" , "Invalid token");
        return "redirect:/forgot-password";
    }

    @PostMapping("/change-password")
    public String post_change_password(@ModelAttribute Account account, RedirectAttributes attributes) {
        Account account_by_id = accountService.findById(account.getId()).get();
        account_by_id.setPassword(account.getPassword());
        account_by_id.setPasswordResetToken("");
        accountService.save(account_by_id);
        attributes.addFlashAttribute("message", "Password updated");
        return "redirect:/login";
    }
    
  
}

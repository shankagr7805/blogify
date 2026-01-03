package com.shank.Blogify.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    @Column(columnDefinition = "TEXT")                      //& Ye annotation specify karta hai ki body field ko database mein TEXT type ke column ke roop mein store karna chahiye, jo large text data ko handle kar sakta hai.
    @NotBlank(message = "Body is required.")
    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id" , referencedColumnName = "id" , nullable = false)
    private Account account;
}

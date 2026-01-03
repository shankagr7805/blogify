package com.shank.Blogify.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity                                                                               //^ This annotation specifies that the class is an entity and is mapped to a database table.(Ye database table ka Java representation hai)
@Getter
@Setter
@NoArgsConstructor                                                                  //^ This annotation generates a no-argument constructor for the class.
public class Account {

    //& Har object = DB ka ek row
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)                             //^ JPA / Hibernate ko bolta hai ki primary key values ek SEQUENCE se generate karo.
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email.")                                      //^ Validation annotation jo ensure karta hai ki email field valid email format mein ho.
    @NotEmpty(message = "Email is missing.")                            //^ Validation annotation jo ensure karta hai ki email field empty na ho.
    private String email;               

    @NotEmpty(message = "Password is missing.")
    private String password;

    @NotEmpty(message = "Firstname is missing.")
    private String firstname;

    @NotEmpty(message = "Lastname is missing.")
    private String lastname;

    private String gender;

    @Min(value = 18)                                                                      //^ Validation annotation jo ensure karta hai ki age field ki value kam se kam 18 ho.
    @Max(value = 100)                                                                     //^ Validation annotation jo ensure karta hai ki age field ki value 100 se zyada na ho.
    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")                                      //^ Ye annotation date field ke format ko specify karta hai.
    private LocalDate dob;

    private String photo;

    private String role;

    @OneToMany(mappedBy = "account")                                                //^ OneToMany relationship i.e ek account ke paas kai posts ho sakte hain.
    private List<Post> posts;

    @Column(name="token")
    private String passwordResetToken;
    
    private LocalDateTime password_reset_token_expiry;

    @ManyToMany                                                                             //^ ManyToMany relationship i.e ek account ke paas kai authorities ho sakti hain aur ek authority kai accounts ke paas ho sakti hai.    
    @JoinTable(                                                                             //^ Ye annotation join table ko define karta hai jo accounts aur authorities ke beech relationship ko manage karta hai.
        name = "account_authority",
        joinColumns = {                                                                  //^ Ye specify karta hai ki join table mein account ka foreign key kaunsa hoga.
            @JoinColumn(name = "account_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {                                                          //^ Ye specify karta hai ki join table mein authority ka foreign key kaunsa hoga.       
            @JoinColumn(name = "authority_id", referencedColumnName = "id")
        })
    private Set<Authority> authorities = new HashSet<>();
}

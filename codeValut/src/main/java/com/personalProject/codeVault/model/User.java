package com.personalProject.codeVault.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
        updateAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updateAt= LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user")
    private List<Snippet> snippets;

}
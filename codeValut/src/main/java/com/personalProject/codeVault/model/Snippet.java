package com.personalProject.codeVault.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column()
    private String description;

    @Lob
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String language;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isPublic;

    private String shareToken;

    @PrePersist
    public void onCreate(){
    createdAt=LocalDateTime.now();
    updatedAt= LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}

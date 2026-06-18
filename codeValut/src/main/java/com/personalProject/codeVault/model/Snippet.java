package com.personalProject.codeVault.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Lob
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String language;

    @ElementCollection(fetch = FetchType.EAGER) //tags = lazy collection (default)
    private List<String> tags;

    @Column(unique = true)
    private String shareToken;

    private LocalDateTime expiryTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "snippet")
    private List<SnippetVersion> snippetVersions;

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

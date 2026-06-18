package com.personalProject.codeVault.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int versionNumber;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Lob
    @Column(nullable = false)
    private String Code;

    @Column(nullable = false)
    private String language;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "snippet_id")
    private Snippet snippet;

    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
    }

}

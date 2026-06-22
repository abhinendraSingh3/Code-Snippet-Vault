//DB table mapped to java
package com.personalProject.codeVault.model;
import jakarta.persistence.*;
import java.util.List;

@User
public class Snippet {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Lob
    @Column(nullable = false, columnDefinition="TEXT")
    private String code;

    @Column(nullable = false)
    private String language;

    private List<String> tags;













}
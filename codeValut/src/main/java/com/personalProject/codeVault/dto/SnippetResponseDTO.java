package com.personalProject.codeVault.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnippetResponseDTO implements Serializable
{
    private Long id;

    private String title;

    private String description;

    private String code;

    private String language;

    private List<String> tags;

    private String shareToken;

    private int versions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}

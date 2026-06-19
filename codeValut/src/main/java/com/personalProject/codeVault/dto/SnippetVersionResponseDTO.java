package com.personalProject.codeVault.dto;

import com.personalProject.codeVault.model.Snippet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetVersionResponseDTO {

    private Long id;
    private int versionNumber;
    private String title;
    private String description;
    private String Code;
    private String language;
    private List<String> tags;
    private LocalDateTime createdAt;
    private Long snippetId;
}

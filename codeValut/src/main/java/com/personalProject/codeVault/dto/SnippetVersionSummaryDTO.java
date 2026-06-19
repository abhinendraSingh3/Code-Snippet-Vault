package com.personalProject.codeVault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetVersionSummaryDTO {

    private String title;
    private int versionNumber;
    private LocalDateTime createdAt;

}

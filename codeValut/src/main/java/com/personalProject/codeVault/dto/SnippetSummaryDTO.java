//for list views — lighter version without code (useful for "list all my snippets" endpoint where you don't want to send full code every time

package com.personalProject.codeVault.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnippetSummaryDTO {

    private Long id;

    private String title;

    private String language;

    private String description;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}

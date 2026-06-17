//why this interface? because the controller should be dependent on the interface not directly on the controller


package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.SnippetRequestDTO;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.SnippetSummaryDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface SnippetService {

    SnippetResponseDTO createSnippet(SnippetRequestDTO request);

    SnippetResponseDTO getSnippetById(Long id);

    Page<SnippetSummaryDTO> getAllSnippets(int page);//--

    SnippetResponseDTO updateSnippet(Long id, SnippetRequestDTO request);

    void deleteSnippet(Long id);

    List<SnippetSummaryDTO> getByLanguage(String language);//---

    List<SnippetSummaryDTO> getByTitle(String title);//---

    List<SnippetSummaryDTO> getByTitleOrLanguage(String keyword);//---
}

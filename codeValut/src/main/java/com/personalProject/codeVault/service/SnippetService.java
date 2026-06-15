//why this interface? because the controller should be dependent on the interface not directly on the controller


package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.SnippetRequestDTO;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.SnippetSummaryDTO;
import jdk.jshell.Snippet;

import java.util.List;

public interface SnippetService {

    SnippetResponseDTO createSnippet(SnippetRequestDTO request);
    SnippetResponseDTO getSnippetById(Long id);
    List<SnippetSummaryDTO> getAllSnippets();
    SnippetResponseDTO updateSnippet(Long id, SnippetRequestDTO request);
    void deleteSnippet(Long id);
}

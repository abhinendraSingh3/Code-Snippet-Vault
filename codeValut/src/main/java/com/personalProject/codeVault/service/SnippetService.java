//why this interface? because the controller should be dependent on the interface not directly on the controller

package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.*;
import org.hibernate.internal.util.Optional;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SnippetService {

    SnippetResponseDTO createSnippet(SnippetRequestDTO request);

    SnippetResponseDTO getSnippetById(Long id);

    Page<SnippetSummaryDTO> getAllSnippets(int page);//--

    SnippetResponseDTO updateSnippet(Long id, SnippetRequestDTO request);

    void deleteSnippet(Long id);

    Page<SnippetSummaryDTO> getByLanguage(String language,int page);//---

    Page<SnippetSummaryDTO> getByTitle(String title,int page);//---

    Page<SnippetSummaryDTO> getByTitleOrLanguage(String keyword,int page);//---

    ShareTokenResponseDTO generateShareToken(Long id);

    SnippetResponseDTO getSharedSnippetByToken(String token);

    // Get all versions of a snippet
    List<SnippetVersionSummaryDTO> getSnippetsVersions(Long id);

    //Get a particular version
    SnippetVersionResponseDTO getSnippetByIdAndVersionNumber(Long id, int versionNumber);

//    Restore current snippet by a previous version
    SnippetResponseDTO replaceCurrentVersion(Long id, int versionNumber);

}

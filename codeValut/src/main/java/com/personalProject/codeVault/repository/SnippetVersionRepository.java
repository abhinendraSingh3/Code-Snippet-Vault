package com.personalProject.codeVault.repository;

import com.personalProject.codeVault.model.Snippet;
import com.personalProject.codeVault.model.SnippetVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetVersionRepository extends JpaRepository<SnippetVersion,Long> {

List<SnippetVersion> findBySnippetId(Long id);

SnippetVersion findBySnippetIdAndVersionNumber(Long id, int versionNumber);

}

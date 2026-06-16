package com.personalProject.codeVault.repository;
import com.personalProject.codeVault.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    //search by title
    List<Snippet>findByTitleContainingIgnoreCase(String title);

    //search by language
    List<Snippet> findByLanguage(String language);

    //search by tag+language
    List<Snippet> findByTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(String language, String title);

    //search by keyword
}

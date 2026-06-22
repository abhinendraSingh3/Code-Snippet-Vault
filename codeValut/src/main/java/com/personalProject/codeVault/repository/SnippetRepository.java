package com.personalProject.codeVault.repository;
import com.personalProject.codeVault.model.Snippet;
import java.util.Optional;

import com.personalProject.codeVault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    //search by title
    Page<Snippet>findByTitleContainingIgnoreCase(String title,Pageable pageable);

    //search by language
    Page<Snippet> findByLanguage(String language, Pageable pageable);

    //search by tag+language
    Page<Snippet> findByTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(String language, String title,Pageable pageable);

    //search by token
    Optional<Snippet> findByShareToken(String token);

   Page<Snippet> findByUser(Pageable pageable, User user);
}

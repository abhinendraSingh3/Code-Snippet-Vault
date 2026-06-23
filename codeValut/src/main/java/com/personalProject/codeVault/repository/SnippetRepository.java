package com.personalProject.codeVault.repository;
import com.personalProject.codeVault.model.Snippet;
import java.util.Optional;

import com.personalProject.codeVault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    //search by title
    Page<Snippet>findByUserAndTitleContainingIgnoreCase(User user,String title,Pageable pageable);

    //search by language
    Page<Snippet> findByLanguageAndUser(String language,User user, Pageable pageable);

    //search by tag+language

    @Query(
            """
            Select s
            from Snippet s
            Where s.user =:user
            AND(
            lower(s.title)like lower(concat("%",:title,"%"))
                        or
            lower(s.language)like lower(concat("%",:title,"%"))
             )
            """
    )
    Page<Snippet> findByUserAndTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(User user,String language, String title,Pageable pageable);

    //search by token
    Optional<Snippet> findByShareToken(String token);

   Page<Snippet> findByUser(Pageable pageable, User user);

   Optional<Snippet> findByIdAndUser(Long id,User user);
}

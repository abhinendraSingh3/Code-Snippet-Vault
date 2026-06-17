package com.personalProject.codeVault.service;
import com.personalProject.codeVault.dto.SnippetRequestDTO;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.SnippetSummaryDTO;
import com.personalProject.codeVault.exception.ResourceNotFoundException;
import com.personalProject.codeVault.model.Snippet;
import com.personalProject.codeVault.repository.SnippetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;
    //    public SnippetServiceImpl(SnippetRepository snippetRepository) {
    //        this.snippetRepository = snippetRepository;
    //    } use manual constructor or use @RequiredArgsConstructor


    @Override
    public SnippetResponseDTO createSnippet(SnippetRequestDTO request) {

        Snippet snippet = new Snippet();

        snippet.setTitle(request.getTitle());
        snippet.setDescription(request.getDescription());
        snippet.setCode(request.getCode());
        snippet.setLanguage(request.getLanguage());
        snippet.setTags(request.getTags());

        Snippet savedSnippet = snippetRepository.save(snippet);

        SnippetResponseDTO response = new SnippetResponseDTO();

        response.setId(savedSnippet.getId());
        response.setTitle(savedSnippet.getTitle());
        response.setDescription(savedSnippet.getDescription());
        response.setCode(savedSnippet.getCode());
        response.setLanguage(savedSnippet.getLanguage());
        response.setTags(savedSnippet.getTags());
        response.setShareToken(savedSnippet.getShareToken());
        response.setCreatedAt(savedSnippet.getCreatedAt());
        response.setUpdatedAt(savedSnippet.getUpdatedAt());

        return response;
    }

    @Override
    public SnippetResponseDTO getSnippetById(Long id) {
        Snippet snippet =snippetRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Snippet not found by given id"));

        SnippetResponseDTO response=new SnippetResponseDTO();

        response.setId(snippet.getId());
        response.setTitle(snippet.getTitle());
        response.setDescription(snippet.getDescription());
        response.setCode(snippet.getCode());
        response.setLanguage(snippet.getLanguage());
        response.setTags(snippet.getTags());
        response.setShareToken(snippet.getShareToken());
        response.setCreatedAt(snippet.getCreatedAt());
        response.setUpdatedAt(snippet.getUpdatedAt());

        return response;
    }

    @Override
    public Page<SnippetSummaryDTO> getAllSnippets(int page) {
        Pageable pageable= PageRequest.of(page,5);

        Page <Snippet> snippets=snippetRepository.findAll(pageable);

        return snippets.map(item->{

            SnippetSummaryDTO dto=new SnippetSummaryDTO();

            dto.setId(item.getId());
            dto.setTitle(item.getTitle());
            dto.setLanguage(item.getLanguage());
            dto.setDescription(item.getDescription());
            dto.setTags(item.getTags());
            dto.setCreatedAt(item.getCreatedAt());
            dto.setUpdatedAt(item.getUpdatedAt());
            return dto;
                });
    }

    @Override
    public SnippetResponseDTO updateSnippet(Long id, SnippetRequestDTO request) {
        
        Snippet snippet=snippetRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Snippet not found by the given id"));
        
        if(request.getTitle()!=null){
            snippet.setTitle(request.getTitle());
        }
        if(request.getDescription() !=null){
            snippet.setDescription(request.getDescription());
        }
        if(request.getCode() !=null){
            snippet.setCode(request.getCode());
        }
        if(request.getLanguage()!=null){
            snippet.setLanguage(request.getLanguage());
        }
        if(request.getTags()!=null){
            snippet.setTags(request.getTags());
        }

        snippetRepository.save(snippet);

        SnippetResponseDTO response=new SnippetResponseDTO();

        response.setId(snippet.getId());
        response.setTitle(snippet.getTitle());
        response.setDescription(snippet.getDescription());
        response.setCode(snippet.getCode());
        response.setLanguage(snippet.getLanguage());
        response.setTags(snippet.getTags());
        response.setShareToken(snippet.getShareToken());
        response.setCreatedAt(snippet.getCreatedAt());
        response.setUpdatedAt(snippet.getUpdatedAt());

        return response;
    }

    @Override
    public void deleteSnippet(Long id) {
        Snippet snippet=snippetRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Snippet not found by the given id"));
        System.out.println("snippet found by given id"+snippet+"now proceeding to delete");
        snippetRepository.deleteById(id);
        System.out.println("deleted Successfully");
    }


    public List<SnippetSummaryDTO> getByLanguage(String language){
        List <Snippet> snippet = snippetRepository.findByLanguage(language);

    List<SnippetSummaryDTO> responses=new ArrayList<>();

        for(Snippet items:snippet){
            SnippetSummaryDTO response=new SnippetSummaryDTO();
            response.setId(items.getId());
            response.setTitle(items.getTitle());
            response.setLanguage(items.getLanguage());
            response.setDescription(items.getDescription());
            response.setTags(items.getTags());
            response.setCreatedAt(items.getCreatedAt());
            response.setUpdatedAt(items.getUpdatedAt());

            responses.add(response);
        }
        return responses;
//-------------------or--------------
//        return snippetRepository.findByLanguage(language)
//                .stream()
//                .map(item->{
//                    SnippetSummaryDTO dto=new SnippetSummaryDTO();
//                    dto.setId(item.getId());
//                    dto.setTitle(item.getTitle());
//                    dto.setLanguage(item.getLanguage());
//                    dto.setDescription(item.getDescription());
//                    dto.setTags(item.getTags());
//                    dto.setCreatedAt(item.getCreatedAt());
//                    dto.setUpdatedAt(item.getUpdatedAt());
//                    return dto;
//
//                }).toList();
    }

    //----GET BY TITLE------------------------
    public List<SnippetSummaryDTO> getByTitle(String title){

        return snippetRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(item->{
                    SnippetSummaryDTO dto=new SnippetSummaryDTO();
                    dto.setId(item.getId());
                    dto.setTitle(item.getTitle());
                    dto.setLanguage(item.getLanguage());
                    dto.setDescription(item.getDescription());
                    dto.setTags(item.getTags());
                    dto.setCreatedAt(item.getCreatedAt());
                    dto.setUpdatedAt(item.getUpdatedAt());
                    return dto;
                }).toList();
    }

    public List<SnippetSummaryDTO> getByTitleOrLanguage(String keyword){
        return snippetRepository
                .findByTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(keyword,keyword)
                .stream()
                .map(item->{
                    SnippetSummaryDTO dto=new SnippetSummaryDTO();
                    dto.setId(item.getId());
                    dto.setTitle(item.getTitle());
                    dto.setLanguage(item.getLanguage());
                    dto.setDescription(item.getDescription());
                    dto.setTags(item.getTags());
                    dto.setCreatedAt(item.getCreatedAt());
                    dto.setUpdatedAt(item.getUpdatedAt());
                    return dto;
                }).toList();
    }



}

package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.SnippetRequestDTO;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.SnippetSummaryDTO;
import com.personalProject.codeVault.model.Snippet;
import com.personalProject.codeVault.repository.SnippetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;
    //    public SnippetServiceImpl(SnippetRepository snippetRepository) {
    //        this.snippetRepository = snippetRepository;
    //    } use manual constructor or use @RequiredArgsConstructor


    @Override
    public SnippetResponseDTO createSnippet(SnippetRequestDTO request) {
        Snippet snippet=new Snippet();

        //saving the data from the request to the snippet
        snippet.setTitle(request.getTitle());
        snippet.setDescription(request.getDescription());
        snippet.setCode(request.getCode());
        snippet.setLanguage(request.getLanguage());
        snippet.setTags(request.getTags());


        //saving the data in the db with the help of repository
       Snippet snippetSavedResponse= snippetRepository.save(snippet);

       //convert the snippet data in to responseDTO
        SnippetResponseDTO response=new SnippetResponseDTO();
        response.setId(snippet.getId());
        response.setTitle(snippet.getTitle());
        response.setDescription((snippet.getDescription()));
        response.setLanguage(snippet.getLanguage());

        return response;
    }

    @Override
    public SnippetResponseDTO getSnippetById(Long id) {
        Snippet snippet =snippetRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Snippet not found by given id"));

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
    public List<SnippetSummaryDTO> getAllSnippets() {

        List <Snippet> snippets=snippetRepository.findAll();

        List<SnippetSummaryDTO> summaryList=new ArrayList<>();

        for(Snippet snippet:snippets){
            SnippetSummaryDTO summaryDTO=new SnippetSummaryDTO();

            summaryDTO.setId(snippet.getId());

            summaryDTO.setTitle(snippet.getTitle());

            summaryDTO.setLanguage(snippet.getLanguage());

            summaryDTO.setDescription(snippet.getDescription());

            summaryDTO.setTags(snippet.getTags());

            summaryDTO.setCreatedAt(snippet.getCreatedAt());

            summaryDTO.setUpdatedAt(snippet.getUpdatedAt());

            //adding these item in list of summary
            summaryList.add(summaryDTO);
        }

        return summaryList;
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
        return ;
    }

    @Override
    public void deleteSnippet(Long id) {

    }


}

package com.personalProject.codeVault.service;
import com.personalProject.codeVault.dto.ShareTokenResponseDTO;
import com.personalProject.codeVault.dto.SnippetRequestDTO;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.SnippetSummaryDTO;
import com.personalProject.codeVault.exception.ResourceNotFoundException;
import com.personalProject.codeVault.exception.TokenExpiredException;
import com.personalProject.codeVault.model.Snippet;
import com.personalProject.codeVault.repository.SnippetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;


@Transactional
@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;

    //    public SnippetServiceImpl(SnippetRepository snippetRepository) {
    //        this.snippetRepository = snippetRepository;
    //    } use above manual constructor or use @RequiredArgsConstructor

//--------------------------------------------------------------------------------------
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
    //--------------------------------------------------------------------------------------
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
    //--------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------

    @Override
    public void deleteSnippet(Long id) {
        Snippet snippet=snippetRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Snippet not found by the given id"));
        System.out.println("snippet found by given id"+snippet+"now proceeding to delete");
        snippetRepository.deleteById(id);
        System.out.println("deleted Successfully");
    }

    //--------------------------------------------------------------------------------------

    @Override
    public Page<SnippetSummaryDTO> getByLanguage(String language,int page){

        Pageable pageable= PageRequest.of(page,5);

        Page<Snippet> snippet = snippetRepository.findByLanguage(language,pageable);
        return snippet.map(item->{
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

    //----GET BY TITLE--------------------------------------------------------------------
    @Override
    public Page<SnippetSummaryDTO> getByTitle(String title,int page){

        Pageable pageable=PageRequest.of(page,5);

        return snippetRepository.findByTitleContainingIgnoreCase(title,pageable)
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
                });
    }

    //--------------------------------------------------------------------------------------
    @Override
    public Page<SnippetSummaryDTO> getByTitleOrLanguage(String keyword, int page){
        Pageable pageable=PageRequest.of(page,5);
        return snippetRepository
                .findByTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(keyword,keyword,pageable)
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
                });
    }
    //--------------------------------------------------------------------------------------

    @Override
   public ShareTokenResponseDTO generateShareToken(Long id){

        ShareTokenResponseDTO shareTokenResponseDTO=new ShareTokenResponseDTO();

        Snippet snippet=snippetRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find snippet with the given id"));
        //generate new token
        String token= UUID.randomUUID().toString();

        //store token
        snippet.setShareToken(token);

        //generate new expiry-valid for 10mins
        snippet.setExpiryTime(LocalDateTime.now().plusMinutes(1));

        //save snippet
        snippetRepository.save(snippet);

        //convert the snippet to ShareTokenResponseDTO
        shareTokenResponseDTO.setToken(snippet.getShareToken());
        shareTokenResponseDTO.setExpiresAt(snippet.getExpiryTime());
        shareTokenResponseDTO.setUrl("http://localhost:8080/api/snippets/search/"+snippet.getShareToken());
        return shareTokenResponseDTO;
    }

    @Override
    public SnippetResponseDTO getSharedSnippetByToken(String token) {
        Snippet snippet=snippetRepository.findByShareToken(token);

        SnippetResponseDTO response=new SnippetResponseDTO();

        LocalDateTime expiryTime=snippet.getExpiryTime();

        if(LocalDateTime.now().isAfter(expiryTime)){
            throw new TokenExpiredException("Token Has Expired");
        }
        else{
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
    }



}

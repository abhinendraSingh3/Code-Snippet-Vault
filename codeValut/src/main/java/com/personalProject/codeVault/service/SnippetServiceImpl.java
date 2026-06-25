package com.personalProject.codeVault.service;
import com.personalProject.codeVault.dto.*;
import com.personalProject.codeVault.exception.ResourceNotFoundException;
import com.personalProject.codeVault.exception.TokenExpiredException;
import com.personalProject.codeVault.exception.UnauthorizedException;
import com.personalProject.codeVault.model.Snippet;
import com.personalProject.codeVault.model.SnippetVersion;
import com.personalProject.codeVault.model.User;
import com.personalProject.codeVault.repository.SnippetRepository;
import com.personalProject.codeVault.repository.SnippetVersionRepository;
import com.personalProject.codeVault.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;
    private final SnippetVersionRepository snippetVersionRepository;
    private final UserRepository userRepository;
    SnippetResponseDTO responseDTO = new SnippetResponseDTO();

    public String getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //    public SnippetServiceImpl(SnippetRepository snippetRepository) {
    //        this.snippetRepository = snippetRepository;
    //    } use above manual constructor or use @RequiredArgsConstructor

//--------------------------------------------------------------------------------------
    @Override
    public SnippetResponseDTO createSnippet(SnippetRequestDTO request) {

        System.out.println("reached here");
        String username=getCurrentUser();
        System.out.println(username);

        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find the user by given username"));

        Snippet snippet = new Snippet();

        snippet.setTitle(request.getTitle());
        snippet.setDescription(request.getDescription());
        snippet.setCode(request.getCode());
        snippet.setLanguage(request.getLanguage());
        snippet.setTags(request.getTags());
        snippet.setUser(user);

        Snippet savedSnippet = snippetRepository.save(snippet);

        responseDTO.setId(savedSnippet.getId());
        responseDTO.setTitle(savedSnippet.getTitle());
        responseDTO.setDescription(savedSnippet.getDescription());
        responseDTO.setCode(savedSnippet.getCode());
        responseDTO.setLanguage(savedSnippet.getLanguage());
        responseDTO.setTags(savedSnippet.getTags());
        responseDTO.setShareToken(savedSnippet.getShareToken());
        responseDTO.setCreatedAt(savedSnippet.getCreatedAt());
        responseDTO.setUpdatedAt(savedSnippet.getUpdatedAt());

        return responseDTO;
    }

    //--------------------------------------------------------------------------------------
    @Cacheable(value = "Snippet", key="#id")
    @Override
    public SnippetResponseDTO getSnippetById(Long id) {

        String username=getCurrentUser();

        User user=userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found"));

        Snippet snippet =snippetRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Snippet not found by given id"));

        if(snippet.getUser().getId()!=user.getId()){
            throw new UnauthorizedException("You are not allowed to visit this snippet");
        }

        SnippetResponseDTO response=new SnippetResponseDTO();

        response.setId(snippet.getId());
        response.setTitle(snippet.getTitle());
        response.setDescription(snippet.getDescription());
        response.setCode(snippet.getCode());
        response.setLanguage(snippet.getLanguage());
        response.setTags(new ArrayList<>(snippet.getTags()));
        response.setShareToken(snippet.getShareToken());
        response.setCreatedAt(snippet.getCreatedAt());
        response.setUpdatedAt(snippet.getUpdatedAt());
        response.setVersions(snippet.getSnippetVersions().size());

        return response;
    }
    //--------------------------------------------------------------------------------------

    @Override
    public Page<SnippetSummaryDTO> getAllSnippets(int page) {
        Pageable pageable= PageRequest.of(page,5);

        String userName=getCurrentUser();//this we are getting from token;

        User user=userRepository.findByUsername(userName)
                .orElseThrow(()->new ResourceNotFoundException("Resource cannot be find by the userName"));



        Page <Snippet> snippets=snippetRepository.findByUser(pageable,user);

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
    @CacheEvict(value="snippet",key="#id")
    public SnippetResponseDTO updateSnippet(Long id, SnippetRequestDTO request) {

        String userName=getCurrentUser();
        User user= userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("Cant find the user"));

        // Find the existing snippet
        Snippet snippet = snippetRepository.findByIdAndUser(id,user).orElseThrow(()->new ResourceNotFoundException("Cant find snippet with the given id or user"));


        // Create a version from the current state before updating
        SnippetVersion snippetVersion = new SnippetVersion();

        snippetVersion.setTitle(snippet.getTitle());
        snippetVersion.setDescription(snippet.getDescription());
        snippetVersion.setCode(snippet.getCode());
        snippetVersion.setLanguage(snippet.getLanguage());
        snippetVersion.setTags(new ArrayList<>(snippet.getTags()));

        // Set version number
        snippetVersion.setVersionNumber(snippet.getSnippetVersions().size() + 1);

        // Maintain both sides of relationship
        snippetVersion.setSnippet(snippet);
        snippet.getSnippetVersions().add(snippetVersion);

        // Save version
        snippetVersionRepository.save(snippetVersion);

        // Update snippet with new values
        if (request.getTitle() != null) {
            snippet.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            snippet.setDescription(request.getDescription());
        }

        if (request.getCode() != null) {
            snippet.setCode(request.getCode());
        }

        if (request.getLanguage() != null) {
            snippet.setLanguage(request.getLanguage());
        }

        if (request.getTags() != null) {
            snippet.setTags(new ArrayList<>(request.getTags()));
        }

        // Save updated snippet
        snippetRepository.save(snippet);

        // Prepare response
        SnippetResponseDTO response = new SnippetResponseDTO();

        response.setId(snippet.getId());
        response.setTitle(snippet.getTitle());
        response.setDescription(snippet.getDescription());
        response.setCode(snippet.getCode());
        response.setLanguage(snippet.getLanguage());
        response.setTags(new ArrayList<>(snippet.getTags()));
        response.setShareToken(snippet.getShareToken());
        response.setCreatedAt(snippet.getCreatedAt());
        response.setUpdatedAt(snippet.getUpdatedAt());

        // Number of versions stored
        response.setVersions(snippet.getSnippetVersions().size());

        return response;
    }

    //--------------------------------------------------------------------------------------

    @Override
    public void deleteSnippet(Long id) {

        String userName=getCurrentUser();
        User user= userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("Cant find the user"));

        // Find the existing snippet
        Snippet snippet = snippetRepository.findByIdAndUser(id,user).orElseThrow(()->new ResourceNotFoundException("Cant find snippet with the given id or user"));

        System.out.println("snippet found by given id"+snippet+"now proceeding to delete");
        snippetRepository.deleteById(id);
        System.out.println("deleted Successfully");
    }

    //--------------------------------------------------------------------------------------

    @Override
    public Page<SnippetSummaryDTO> getByLanguage(String language,int page){

        Pageable pageable= PageRequest.of(page,5);

        String userName=getCurrentUser();

        User user= userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("Cant find the user"));

        Page<Snippet> snippet = snippetRepository.findByLanguageAndUser(language,user,pageable);
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


        String userName=getCurrentUser();

        User user= userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("Cant find the user"));

        Pageable pageable=PageRequest.of(page,5);

        return snippetRepository.findByUserAndTitleContainingIgnoreCase(user,title,pageable)
                .map(item->{
                    SnippetSummaryDTO dto=new SnippetSummaryDTO();
                    dto.setId(item.getId());
                    dto.setTitle(item.getTitle());
                    dto.setLanguage(item.getLanguage());
                    dto.setDescription(item.getDescription());
                    dto.setTags(new ArrayList<>(item.getTags()));
                    dto.setCreatedAt(item.getCreatedAt());
                    dto.setUpdatedAt(item.getUpdatedAt());
                    return dto;
                });
    }

    //--------------------------------------------------------------------------------------
    @Override
    public Page<SnippetSummaryDTO> getByTitleOrLanguage(String keyword, int page){

        Pageable pageable=PageRequest.of(page,5);

    String userName=getCurrentUser();

    User user=userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("User cant be found"));

        return snippetRepository
                .findByUserAndTitleContainingIgnoreCaseOrLanguageContainingIgnoreCase(user,keyword,keyword,pageable)
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

        String userName=getCurrentUser();

        User user=userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("User cant be found"));

        ShareTokenResponseDTO shareTokenResponseDTO=new ShareTokenResponseDTO();

        Snippet snippet=snippetRepository
                .findByIdAndUser(id,user)
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

    //----------------------------------------------------------------------------------------------------
    @Override
    public SnippetResponseDTO getSharedSnippetByToken(String token) {

        Snippet snippet = snippetRepository.findByShareToken(token)
                .orElseThrow(() -> new UnauthorizedException("Invalid Token"));

        SnippetResponseDTO response=new SnippetResponseDTO();

        LocalDateTime expiryTime=snippet.getExpiryTime();

        if(LocalDateTime.now().isAfter(expiryTime)){
            throw new TokenExpiredException("Token Has Expired");
        }

        else if (!token.equals(snippet.getShareToken())) {
            throw new UnauthorizedException("Token is invalid");

        } else{
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
//-------------------------------------------------------------------------------------
//    Get all versions of a snippet
    @Override
    public List <SnippetVersionSummaryDTO> getSnippetsVersions(Long id){

       return snippetVersionRepository
               .findBySnippetId(id)
               .stream()
               .map(items->{
           SnippetVersionSummaryDTO summaryDTO=new SnippetVersionSummaryDTO();
           summaryDTO.setTitle(items.getTitle());
           summaryDTO.setVersionNumber(items.getVersionNumber());
           summaryDTO.setCreatedAt(items.getCreatedAt());
           return summaryDTO;
       }) .toList();
}

//------------------------------------------------------------------------------------------------------
//Get a particular version
    @Override
   public SnippetVersionResponseDTO getSnippetByIdAndVersionNumber(Long id, int versionNumber){
       SnippetVersion version=snippetVersionRepository.findBySnippetIdAndVersionNumber(id,versionNumber);

        SnippetVersionResponseDTO versionResponseDTO=new SnippetVersionResponseDTO();

        versionResponseDTO.setId(version.getId());
        versionResponseDTO.setVersionNumber(version.getVersionNumber());
        versionResponseDTO.setTags(version.getTags());
        versionResponseDTO.setTitle(version.getTitle());
        versionResponseDTO.setDescription(version.getDescription());
        versionResponseDTO.setLanguage(version.getLanguage());
        versionResponseDTO.setCreatedAt(version.getCreatedAt());
//        versionResponseDTO.setSnippet(version.getSnippet());-> this causes lazy loading issue because
        //here the command is extracting whole snippet that's why it took time, but we only want id right so we will modify it.
        versionResponseDTO.setSnippetId(version.getSnippet().getId());

        return versionResponseDTO;
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public SnippetResponseDTO replaceCurrentVersion(Long id, int versionNumber) {

        String userName=getCurrentUser();

        User user=userRepository.findByUsername(userName).orElseThrow(()->new ResourceNotFoundException("User cant be found"));

        // Get current snippet
        Snippet snippet = snippetRepository.findByIdAndUser(id,user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Snippet not found"));

        // Get desired version
        SnippetVersion version = snippetVersionRepository
                .findBySnippetIdAndVersionNumber(id, versionNumber);

        // Save current state as a new version
        SnippetVersion currentVersion = new SnippetVersion();

        currentVersion.setVersionNumber(snippet.getSnippetVersions().size() + 1);
        currentVersion.setTitle(snippet.getTitle());
        currentVersion.setDescription(snippet.getDescription());
        currentVersion.setCode(snippet.getCode());
        currentVersion.setLanguage(snippet.getLanguage());
        currentVersion.setTags(new ArrayList<>(snippet.getTags()));

        // Link to current snippet
        currentVersion.setSnippet(snippet);

        snippetVersionRepository.save(currentVersion);

        // Restore the desired version into current snippet
        snippet.setTitle(version.getTitle());
        snippet.setDescription(version.getDescription());
        snippet.setCode(version.getCode());
        snippet.setLanguage(version.getLanguage());
        snippet.setTags(new ArrayList<>(snippet.getTags()));

        // Save updated snippet
        snippetRepository.save(snippet);

        responseDTO.setId(snippet.getId());
        responseDTO.setTitle(snippet.getTitle());
        responseDTO.setDescription(snippet.getDescription());
        responseDTO.setCode(snippet.getCode());
        responseDTO.setLanguage(snippet.getLanguage());
        responseDTO.setTags(new ArrayList<>(snippet.getTags()));
        responseDTO.setShareToken(snippet.getShareToken());
        responseDTO.setCreatedAt(snippet.getCreatedAt());
        responseDTO.setUpdatedAt(snippet.getUpdatedAt());
        responseDTO.setVersions(snippet.getSnippetVersions().size());

        return responseDTO;
    }

}

    //receives HTTP requests, calls service

    package com.personalProject.codeVault.controller;
    import com.personalProject.codeVault.dto.*;
    import com.personalProject.codeVault.service.SnippetService;

    import jakarta.validation.Valid;
    import org.springframework.data.domain.Page;
    import org.springframework.data.repository.query.Param;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/snippets")
    public class SnippetController {

        private final SnippetService snippetService;

        public SnippetController(SnippetService snippetService) {
            this.snippetService=snippetService;
        }

        //get/snippets-this gives summary of all snippets
        @GetMapping
            public Page<SnippetSummaryDTO> getAllSnippets(@RequestParam (defaultValue = "0") int page) {
            return snippetService.getAllSnippets(page);
        }
        //post/snippets-create new snippet
        @PostMapping
        public SnippetResponseDTO createSnippet(@Valid @RequestBody SnippetRequestDTO requestDTO){
            return snippetService.createSnippet(requestDTO);
        }

        //get/snippets/{id}-get snippet by id
        @GetMapping("/{id}")
        public SnippetResponseDTO getSnippetById(@PathVariable Long id){
            return snippetService.getSnippetById(id);
        }

        //put/snippets/{id}-update a particular snippet
        @PutMapping("/{id}")
        public SnippetResponseDTO updateSnippet(@PathVariable Long id, @RequestBody SnippetRequestDTO requestDTO){
            return snippetService.updateSnippet(id,requestDTO);
        }

        //delete/snippets/{id}-delete snippet by id
        @DeleteMapping("/{id}")
        public void deleteSnippet(@PathVariable Long id){
            snippetService.deleteSnippet(id);
        }

        //search by language
        @GetMapping("/search/language")
        public Page<SnippetSummaryDTO> getByLanguage(@RequestParam String language,@RequestParam(defaultValue="0") int page){
            return snippetService.getByLanguage(language,page);
        }

        @GetMapping("/search/title")
        public Page<SnippetSummaryDTO> getByTitle(@RequestParam String title,@RequestParam(defaultValue = "0") int page){
            return snippetService.getByTitle(title,page);
        }

        @GetMapping("/search")
        public Page<SnippetSummaryDTO> search(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page){
            return snippetService.getByTitleOrLanguage(keyword,page);
        }

        @GetMapping("/search/{token}")
        public SnippetResponseDTO getSnippetByToken(@PathVariable String token){
            return snippetService.getSharedSnippetByToken(token);
        }

        @GetMapping("/token/{id}")
        public ShareTokenResponseDTO generateShareToken(@PathVariable Long id){
            return snippetService.generateShareToken(id);
        }

        @GetMapping("/{id}/versions")
        public List<SnippetVersionSummaryDTO> getSnippetsVersions(@PathVariable Long id){
            return snippetService.getSnippetsVersions(id);
        }



    }

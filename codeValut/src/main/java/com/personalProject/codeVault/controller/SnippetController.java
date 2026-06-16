    //receives HTTP requests, calls service

    package com.personalProject.codeVault.controller;
    import com.personalProject.codeVault.dto.SnippetRequestDTO;
    import com.personalProject.codeVault.dto.SnippetResponseDTO;
    import com.personalProject.codeVault.dto.SnippetSummaryDTO;
    import com.personalProject.codeVault.service.SnippetService;
    import jakarta.transaction.Transactional;
    import jakarta.validation.Valid;
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
            public List<SnippetSummaryDTO> getAllSnippets() {
            return snippetService.getAllSnippets();

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
        public List<SnippetSummaryDTO> getByLanguage(@RequestParam String language){
            return snippetService.getByLanguage(language);
        }
        @GetMapping("/search/title")
        public List<SnippetSummaryDTO> getByTitle(@RequestParam String title){
            return snippetService.getByTitle(title);
        }

        @GetMapping("/search")
        public List<SnippetSummaryDTO> search(@RequestParam String keyword){
            return snippetService.getByTitleOrLanguage(keyword);
        }
    }

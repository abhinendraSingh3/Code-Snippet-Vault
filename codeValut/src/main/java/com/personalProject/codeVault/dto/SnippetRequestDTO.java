//what client sends IN (RequestDTO) and gets back OUT (ResponseDTO) — never expose raw model to outside
package com.personalProject.codeVault.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnippetRequestDTO {


    @NotBlank(message = "Title is required")
    @Size(max=100,message = "Please enter title less than 100 characters")
    private String title;

    //for the description
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Code is required")
    private String code;


    private String language;

    @NotEmpty(message = "At least one tag is required")
    private List<String> tags;




}

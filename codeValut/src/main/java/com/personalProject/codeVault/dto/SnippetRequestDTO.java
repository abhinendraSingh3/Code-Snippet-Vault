//what client sends IN (RequestDTO) and gets back OUT (ResponseDTO) — never expose raw model to outside
package com.personalProject.codeVault.dto;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnippetRequestDTO {


    private String title;

    private String description;

    private String code;

    private String language;

    private List<String> tags;




}

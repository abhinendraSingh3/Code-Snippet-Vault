package com.personalProject.codeVault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareTokenResponseDTO {

    private String token;

    private String url;

    private LocalDateTime expiresAt;

}

package com.personalProject.codeVault.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserResponseDTO {


    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String token;

}

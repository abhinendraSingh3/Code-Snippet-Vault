package com.personalProject.codeVault.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserSignupRequestDTO {


    @Column(unique = true)
    @NotBlank(message = "username should not be blank")
    private String username;

    @NotBlank(message = "first name should not be blank")
    private String firstName;

    @NotBlank(message = "last name should not be blank")
    private String lastName;

    @Email
    @NotBlank(message = "email should not be blank")
    private String email;

    @NotBlank(message = "password should not be blank")
    private String password;
}

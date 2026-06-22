package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.UserLoginRequestDTO;
import com.personalProject.codeVault.dto.UserSignupRequestDTO;
import com.personalProject.codeVault.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO userRegistration (UserSignupRequestDTO request);

    UserResponseDTO userLogin(UserLoginRequestDTO request);

}

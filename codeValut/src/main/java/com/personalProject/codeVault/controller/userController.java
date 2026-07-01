package com.personalProject.codeVault.controller;
import com.personalProject.codeVault.dto.SnippetResponseDTO;
import com.personalProject.codeVault.dto.UserLoginRequestDTO;
import com.personalProject.codeVault.dto.UserSignupRequestDTO;
import com.personalProject.codeVault.dto.UserResponseDTO;
import com.personalProject.codeVault.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;

    @PostMapping("/signup")
    UserResponseDTO newUserRegistration(@Valid @RequestBody UserSignupRequestDTO request){
        return userService.userRegistration(request);

    }

    @PostMapping("/login")
    UserResponseDTO loginUser(@Valid @RequestBody UserLoginRequestDTO request){
        return userService.userLogin(request);
    }
}

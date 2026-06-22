package com.personalProject.codeVault.service;

import com.personalProject.codeVault.dto.UserLoginRequestDTO;
import com.personalProject.codeVault.dto.UserSignupRequestDTO;
import com.personalProject.codeVault.dto.UserResponseDTO;
import com.personalProject.codeVault.exception.ResourceNotFoundException;
import com.personalProject.codeVault.exception.UnauthorizedException;
import com.personalProject.codeVault.model.User;
import com.personalProject.codeVault.repository.UserRepository;
import com.personalProject.codeVault.securityConfig.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

//    public UserServiceImp userServiceImp(UserRepository userRepository){
//        this.userRepository=userRepository;
//    } use above manual initialization or use @RequiredArgsConstructor

    @Override
    public UserResponseDTO userRegistration(UserSignupRequestDTO request) {
        User user=new User();


        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        UserResponseDTO responseDTO=new UserResponseDTO();

        responseDTO.setUsername(user.getUsername());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setEmail(user.getEmail());

        return responseDTO;
    }

    @Override
    public UserResponseDTO userLogin(UserLoginRequestDTO request){

        String username= request.getUsername();
        String inputPassword= request.getPassword();


       User user=userRepository.findByUsername(username)
               .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        //checking if the password is correct or not;
        boolean passwordCorrect=passwordEncoder.matches(
                inputPassword,
                user.getPassword()
        );

        if(!passwordCorrect){
            throw new UnauthorizedException("Password is not correct");
        }

        //generate token using jwtService
        String token=jwtService.generateJwt(user);

        UserResponseDTO response=new UserResponseDTO();
           response.setUsername(user.getUsername());
           response.setFirstName(user.getFirstName());
           response.setLastName(user.getLastName());
           response.setEmail(user.getEmail());
           response.setToken(token);

        return response;
    }


}

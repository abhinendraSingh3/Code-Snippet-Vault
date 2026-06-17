package com.personalProject.codeVault.exception;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException(String message){
        super(message);
    }

}

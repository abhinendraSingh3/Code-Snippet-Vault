package com.personalProject.codeVault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CodeVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeVaultApplication.class, args);
	}

}

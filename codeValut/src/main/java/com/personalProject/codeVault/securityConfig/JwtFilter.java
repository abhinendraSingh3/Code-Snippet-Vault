package com.personalProject.codeVault.securityConfig;

import com.personalProject.codeVault.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //get authentication header
        String authHeader=request.getHeader("Authorization");
//        System.out.println("gotten authHeader " +authHeader);

        // 2. If no token, just skip — let Spring handle it (will deny access)
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }

        // 3. Extract the token
        String token=authHeader.substring(7);
//        System.out.println("token "+token);

        // 4. Verify and put user on whiteboard
        if(jwtService.isTokenValid(token)){
            String userName= jwtService.extractUserName(token);
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userName,null, List.of());

            // Put user on the whiteboard so the rest of the app knows "who is logged in"
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            throw new UnauthorizedException("Token not valid or expired");
        }
        // 5. Move to next step (always call this at the end)
        chain.doFilter(request,response);



    }
}

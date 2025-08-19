package com.Authentication_User.config;


import com.Authentication_User.service.CustomUserDetailsService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void
    doFilterInternal(HttpServletRequest request,
                     HttpServletResponse response,
                     FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");       // Retrieves the Authorization header from the request.
        String token = null;
        String username = null;

        //validate the token

        if (authHeader != null && authHeader.startsWith("Bearer ")) {         //  Checks if the token is present and starts with "Bearer ".
            token = authHeader.substring(7);                   // Removes the "Bearer " prefix from the token.
            username = jwtUtil.extractUsername(token);   // Extracts the username from the token if it is valid.
        }

                if (username != null && SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token,userDetails)){

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}

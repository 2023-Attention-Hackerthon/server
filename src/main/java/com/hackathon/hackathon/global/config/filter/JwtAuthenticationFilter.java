package com.hackathon.hackathon.global.config.filter;

import com.hackathon.hackathon.global.exception.base.BaseException;
import com.hackathon.hackathon.global.jwt.JwtProvider;
import com.hackathon.hackathon.global.jwt.dto.DecodedJwtToken;
import com.hackathon.hackathon.global.utils.FilterExceptionProcessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hackathon.hackathon.global.constant.StaticValue.ACCESS_TOKEN;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final FilterExceptionProcessor filterExceptionProcessor;
    public JwtAuthenticationFilter(JwtProvider jwtProvider, FilterExceptionProcessor filterExceptionProcessor) {
        this.jwtProvider = jwtProvider;
        this.filterExceptionProcessor = filterExceptionProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String token = resolveToken(request);
            if (token != null) {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContextHolder에 담기
            }
            chain.doFilter(request, response);
        }catch (BaseException e){
            filterExceptionProcessor.excute(response, e);
        }
    }
    private Authentication getAuthentication(String token) {
        DecodedJwtToken decodedJwtToken = jwtProvider.decodeToken(token,ACCESS_TOKEN);

        Long userId = decodedJwtToken.getUserId();
        String role = decodedJwtToken.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
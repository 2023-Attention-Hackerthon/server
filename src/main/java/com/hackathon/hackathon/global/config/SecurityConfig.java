package com.hackathon.hackathon.global.config;


import com.hackathon.hackathon.global.config.filter.JwtAuthenticationFilter;
import com.hackathon.hackathon.global.jwt.JwtProvider;
import com.hackathon.hackathon.global.utils.FilterExceptionProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final JwtProvider jwtTokenProvider;
    private final FilterExceptionProcessor filterExceptionProcessor;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()

                .cors()
                .disable()

                .csrf()
                .disable()

                .httpBasic()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers("/v1/auth/**").permitAll()
                .requestMatchers("/v1/**").authenticated();


        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,filterExceptionProcessor), UsernamePasswordAuthenticationFilter.class);





        return http.build();
    }
}

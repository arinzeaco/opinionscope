package com.auth.opinionscope.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.auth.opinionscope.model.Role.ADMIN;
import static com.auth.opinionscope.model.Role.USERS;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProjectSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    @Autowired
//    private final LogoutHandler logoutHandler;


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("api/v1/auth/*", "api/v1/otp/*","api/v1/test/*"
                        ,"api/v1/password/*","api/v1/tags/*")
                .permitAll()
                .requestMatchers(POST, "api/v1/votes/*").authenticated()
                .requestMatchers(POST, "api/v1/votes/*").authenticated()
                .requestMatchers(HttpMethod.GET, "api/v1/questions/**").hasAnyAuthority(USERS.name(), ADMIN.name())
                .requestMatchers(HttpMethod.GET, "api/v1/profile/**").hasAnyAuthority(USERS.name(), ADMIN.name())
                .requestMatchers(HttpMethod.POST, "api/v1/questions/**").hasAuthority(ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "api/v1/questions/**").hasAuthority(ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "api/v1/questions/**").hasAuthority(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }


}

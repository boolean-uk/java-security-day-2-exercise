package com.booleanuk.library.security;

import com.booleanuk.library.security.jwt.AuthEntryPointJwt;
import com.booleanuk.library.security.jwt.AuthTokenFilter;
import com.booleanuk.library.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET,"/books", "/books/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/board_games", "/board_games/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/cds", "/cds/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/dvds", "/dvds/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/video_games", "/video_games/**").hasRole("USER")

                        .requestMatchers("/video_games", "/video_games/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/books", "/books/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/board_games", "/board_games/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/cds", "/cds/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/dvds", "/dvds/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/auth/**").permitAll()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

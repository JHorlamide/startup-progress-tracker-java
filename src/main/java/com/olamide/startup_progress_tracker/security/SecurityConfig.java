package com.olamide.startup_progress_tracker.security;

import com.olamide.startup_progress_tracker.security.filters.AuthenticationFilter;
import com.olamide.startup_progress_tracker.security.filters.ExceptionHandlerFilter;
import com.olamide.startup_progress_tracker.security.filters.JWTAuthorizationFilter;
import com.olamide.startup_progress_tracker.security.managers.CustomAuthenticationManager;
import com.olamide.startup_progress_tracker.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private CustomAuthenticationManager authenticationManager;
    private ResponseHandler responseHandler;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        var exceptionHandlerFilter = new ExceptionHandlerFilter(responseHandler);
        var authenticationFilter = new AuthenticationFilter(authenticationManager, responseHandler);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(authenticationFilter)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.AUTH_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .addFilterBefore(exceptionHandlerFilter, AuthenticationFilter.class)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        var exceptionHandlerFilter = new ExceptionHandlerFilter(responseHandler);
        var authenticationFilter = new AuthenticationFilter(authenticationManager, responseHandler);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(authenticationFilter)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/h2/**", SecurityConstants.AUTH_ENDPOINT, SecurityConstants.REGISTER_PATH)
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterBefore(exceptionHandlerFilter, AuthenticationFilter.class)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

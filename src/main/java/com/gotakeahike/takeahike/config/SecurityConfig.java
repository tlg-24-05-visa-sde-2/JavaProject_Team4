package com.gotakeahike.takeahike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for setting up web security.
 * <p>
 * This class configures various aspects of web security, including:
 * <ul>
 *     <li>Disabling CSRF protection.</li>
 *     <li>Enabling Cross-Origin Resource Sharing (CORS) with default settings.</li>
 *     <li>Permitting all HTTP requests without authentication.</li>
 *     <li>Configuring logout behavior, including the URL for logout, session invalidation, and cookie deletion.</li>
 * </ul>
 * <p>
 * This configuration class is annotated with:
 * <ul>
 *     <li>{@link Configuration} to indicate that it provides Spring configuration.</li>
 *     <li>{@link EnableWebSecurity} to enable Spring Security's web security support.</li>
 *     <li>{@link EnableMethodSecurity} to enable method-level security annotations.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the {@link SecurityFilterChain} for HTTP security.
     * <p>
     * This method sets up the security filter chain with the following configuration:
     * <ul>
     *     <li>Disables CSRF (Cross-Site Request Forgery) protection.</li>
     *     <li>Enables CORS (Cross-Origin Resource Sharing) with default settings.</li>
     *     <li>Permits all HTTP requests without authentication.</li>
     *     <li>Configures logout behavior to:
     *         <ul>
     *             <li>Set the logout URL to "/auth/logout".</li>
     *             <li>Invalidate the HTTP session upon logout.</li>
     *             <li>Delete the "HikeCookie" cookie on logout.</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} object used to configure web security
     * @return the configured {@link SecurityFilterChain} object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("HikeCookie"))
                .build();
    }
}
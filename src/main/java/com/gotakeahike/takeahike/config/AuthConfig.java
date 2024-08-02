package com.gotakeahike.takeahike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AuthConfig is a configuration class for setting up authentication-related beans.
 * <p>
 * This class defines beans for password encoding, authentication provider, and
 * authentication manager, integrating with Spring Security.
 */
@Configuration
public class AuthConfig {

    // Service for loading user-specific data
    private final UserDetailsService userDetailsService;

    /**
     * Constructor to initialize AuthConfig with the UserDetailsService.
     *
     * @param userDetailsService the service for loading user-specific data
     */
    public AuthConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Bean for password encoding.
     * <p>
     * This method returns a BCryptPasswordEncoder with strength 10, which is used for hashing passwords.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Bean for DAO authentication provider.
     * <p>
     * This method sets up a DaoAuthenticationProvider with the custom UserDetailsService
     * and the password encoder defined in {@link #passwordEncoder()}.
     *
     * @return a DaoAuthenticationProvider instance
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Bean for authentication manager.
     * <p>
     * This method retrieves the AuthenticationManager from the provided AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the configuration for authentication
     * @return an AuthenticationManager instance
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
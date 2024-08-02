package com.gotakeahike.takeahike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * Web configuration class that sets up various web-related settings.
 * <p>
 * This class configures CORS (Cross-Origin Resource Sharing) policies and provides a {@link RestTemplate} bean.
 * It implements {@link WebMvcConfigurer} to customize web-related configurations.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS settings for the application.
     * <p>
     * This method creates a {@link CorsConfiguration} object with the following settings:
     * <ul>
     *     <li>Allowed origins: <a href="http://localhost:8080">...</a>, http://localhost:3000, http://localhost:8081</li>
     *     <li>Allowed methods: GET, POST, PUT, DELETE, PATCH, OPTIONS</li>
     *     <li>Allowed headers: All headers (*)</li>
     *     <li>Allow credentials (cookies)</li>
     *     <li>Expose cookies in response</li>
     * </ul>
     * The configuration is then applied to all paths ("/**").
     *
     * @return a {@link CorsConfigurationSource} object with the configured CORS settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000", "http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies)
        configuration.setAllowCredentials(true);
        // Expose cookies in response
        configuration.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        System.out.println("**************** Cors Has Been Configured");
        return source;
    }

    /**
     * Configures CORS mappings for the application.
     * <p>
     * This method is used to specify CORS settings for various endpoints. It allows:
     * <ul>
     *     <li>Origins: <a href="http://localhost:8080">...</a>, http://localhost:3000, http://localhost:8081</li>
     *     <li>Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS</li>
     *     <li>Headers: All headers (*)</li>
     *     <li>Credentials (cookies)</li>
     *     <li>Exposure of cookies in response</li>
     * </ul>
     *
     * @param registry the {@link CorsRegistry} used to configure CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3000", "http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Set-Cookie");
        System.out.println("**************** CORS has been configured");
    }

    /**
     * Provides a {@link RestTemplate} bean for making HTTP requests.
     *
     * @return a {@link RestTemplate} instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
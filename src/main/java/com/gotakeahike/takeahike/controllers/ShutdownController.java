package com.gotakeahike.takeahike.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ShutdownController handles HTTP requests related to shutting down the application.
 * <p>
 * This controller provides an endpoint for shutting down the Spring application context,
 * effectively stopping the application.
 */
@RestController
@RequestMapping("/admin")
public class ShutdownController {

    // Application context that will be closed to shut down the application
    private final ConfigurableApplicationContext context;

    /**
     * Constructor to initialize ShutdownController with the application context.
     *
     * @param context the configurable application context
     */
    @Autowired
    public ShutdownController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * Endpoint to shut down the application.
     * <p>
     * This method handles GET requests to the "/admin/shutdown" URL, prints a shutdown message
     * to the console, and closes the application context to shut down the application.
     *
     * @return a message indicating the application is shutting down
     */
    @GetMapping("/shutdown")
    public String shutdown() {
        System.out.println("Application shutting down");
        // Close the application context to shut down the application
        context.close();
        return "Application is shutting down...";
    }
}
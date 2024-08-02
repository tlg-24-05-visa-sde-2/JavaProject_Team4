package com.gotakeahike.takeahike.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ShutdownController {

    private final ConfigurableApplicationContext context;

    @Autowired
    public ShutdownController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @GetMapping("/shutdown")
    public String shutdown() {
        System.out.println("Application shutting down");
        // Close the application context to shut down the application
        context.close();
        return "Application is shutting down...";
    }
}
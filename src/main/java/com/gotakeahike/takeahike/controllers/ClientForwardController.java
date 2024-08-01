package com.gotakeahike.takeahike.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
/*
 * This controllers sole purpose is to forward all traffic coming into the localhost:8080 port to our React application.
 *
 * The React app has its own routing handler and will show the proper pages within itself.
 *
 * The rest of the controllers are used to handle data coming in from the React GUI
 */
@Controller
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
public class ClientForwardController {
    @GetMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }
}
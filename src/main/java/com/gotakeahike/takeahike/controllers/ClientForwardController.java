/*
 * This controllers sole purpose is to forward all traffic coming into the localhost:8080 port to our React application.
 *
 * The React app has its own routing handler and will show the proper pages within itself.
 *
 * The rest of the controllers are used to handle data coming in from the React GUI
 */
package com.gotakeahike.takeahike.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * ClientForwardController handles forwarding all non-static resource requests to the React application.
 * <p>
 * This class forwards requests to the root URL ("/") so that the React application's routing can handle them.
 * This setup ensures that the React app manages the routing and displays the appropriate pages within itself.
 */
@Controller
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
public class ClientForwardController {

    /**
     * Forwards all non-static resource requests to the root URL.
     * <p>
     * This method matches any path that does not contain a period (".") and forwards it to the root URL ("/").
     * This allows the React application to handle the routing.
     *
     * @return a forward directive to the root URL
     */
    @GetMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }
}
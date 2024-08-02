package com.gotakeahike.takeahike.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * BrowserLauncher is a component that opens the default web browser to a specified URL
 * when the application starts.
 * <p>
 * This class implements the {@link CommandLineRunner} interface, which means its
 * {@link #run(String...)} method will be executed after the application context is loaded.
 */
@Component
public class BrowserLauncher implements CommandLineRunner {

    /**
     * Runs the browser launcher.
     * <p>
     * This method opens the default web browser to the specified URL depending on the
     * operating system. Supported operating systems are Windows, Mac, and Unix-based systems.
     *
     * @param args command-line arguments (not used)
     * @throws IOException        if an I/O error occurs
     * @throws URISyntaxException if the URL syntax is incorrect
     */
    @Override
    public void run(String... args) throws IOException, URISyntaxException {
        String url = "http://localhost:8080";

        // Check the operating system and open the default browser accordingly
        if (isWindows()) {
            Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
        } else if (isMac()) {
            Runtime.getRuntime().exec(new String[]{"open", url});
        } else if (isUnix()) {
            Runtime.getRuntime().exec(new String[]{"xdg-open", url});
        } else {
            System.out.println("Operating system not supported!");
        }
    }

    /**
     * Checks if the operating system is Windows.
     *
     * @return true if the operating system is Windows, false otherwise
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Checks if the operating system is Mac.
     *
     * @return true if the operating system is Mac, false otherwise
     */
    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    /**
     * Checks if the operating system is Unix-based (Linux, AIX).
     *
     * @return true if the operating system is Unix-based, false otherwise
     */
    private static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux") || os.contains("aix");
    }
}
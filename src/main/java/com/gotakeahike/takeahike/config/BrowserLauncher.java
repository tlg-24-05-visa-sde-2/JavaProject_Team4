package com.gotakeahike.takeahike.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class BrowserLauncher implements CommandLineRunner {

    @Override
    public void run(String... args) throws IOException, URISyntaxException {
        String url = "http://localhost:8080";
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

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    private static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux") || os.contains("aix");
    }
}
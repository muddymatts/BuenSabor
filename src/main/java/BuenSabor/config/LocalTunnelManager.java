package BuenSabor.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LocalTunnelManager {

    private static final Logger logger = Logger.getLogger(LocalTunnelManager.class.getName());

    @Getter
    private String localTunnelUrl;

    private Process localTunnelProcess;

    private static final int PORT = 8080;

    @PostConstruct
    public void init() throws IOException {
        boolean tunnelRunning = isLocalTunnelRunning();
        if (tunnelRunning) {
            System.out.println("LocalTunnel is already running on port " + PORT);
        } else {
            startLocalTunnel();
        }
    }

    private boolean isLocalTunnelRunning() {
        try {
            Process process = new ProcessBuilder("netstat", "-ano").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(":8080") && line.contains("LISTENING")) {
                    return true;
                }
            }
            process.waitFor();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al detectar localTunnel corriendo", e);
        }
        return false;
    }

    private void startLocalTunnel() throws IOException {
        System.out.println("Starting LocalTunnel on port " + PORT + "...");

        Dotenv dotenv = Dotenv.load();
        String ltExecutable = dotenv.get("LT_PATH"); // debe existir la variable LT_PATH en el archivo .env que apunte hacia "lt.cmd"
        String customSubdomain = dotenv.get("LT_SUBDOMAIN");

        if (ltExecutable == null || ltExecutable.isBlank()) {
            ltExecutable = "lt";
        }

        ProcessBuilder pb;

        if (customSubdomain != null && !customSubdomain.isBlank()) {
            pb = new ProcessBuilder(
                    ltExecutable,
                    "--port", String.valueOf(PORT),
                    "--subdomain", customSubdomain
            );
            System.out.println("Using custom subdomain: " + customSubdomain);
        } else {
            pb = new ProcessBuilder(
                    ltExecutable,
                    "--port", String.valueOf(PORT)
            );
            System.out.println("No subdomain specified. Using random generated one.");
        }

        pb.redirectErrorStream(true);
        Process localTunnelProcess = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(localTunnelProcess.getInputStream()));
        Pattern urlPattern = Pattern.compile("https://[a-z0-9.-]+\\.loca\\.lt");

        new Thread(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = urlPattern.matcher(line);
                    if (matcher.find()) {
                        localTunnelUrl = matcher.group(0);
                        System.out.println("LocalTunnel URL is: " + localTunnelUrl);
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error al iniciar servicio localTunnel", e);
            }
        }).start();
    }

    @PreDestroy
    public void stopLocalTunnel() {
        if (localTunnelProcess != null && localTunnelProcess.isAlive()) {
            localTunnelProcess.destroy();
            System.out.println("LocalTunnel stopped.");
        }
    }
}


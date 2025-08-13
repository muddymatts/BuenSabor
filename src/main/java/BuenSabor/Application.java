package BuenSabor;

import com.mercadopago.MercadoPagoConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        createDatabaseIfNotExists();
        setupKeyMp();
        SpringApplication.run(Application.class, args);
    }

    private static void createDatabaseIfNotExists() {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        String dbName = "el_buen_sabor";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("BASE DE DATOS CREADA: " + dbName);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR AL CREAR BASE DE DATOS: " + dbName, e);
        }
    }

    private static void setupKeyMp() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String mpKey = dotenv.get("PROD_ACCESS_TOKEN");

        if (mpKey == null || mpKey.isEmpty()) {
            throw new IllegalStateException("Falta la variable de entorno VITE_MERCADOPAGO_KEY.");
        }

        MercadoPagoConfig.setAccessToken(mpKey);
    }
}

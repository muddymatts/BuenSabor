package BuenSabor.config;

public class SecurityConstants {
    public static final String[] PUBLIC_PATHS = {
            "/api/auth/login",
            "/api/registro-usuario",
            "/uploads/images/**",
            "/api/paises/**",
            "/api/provincias/**",
            "/api/localidades/**",
            "/api/mercadopago/webhook"
    };
}
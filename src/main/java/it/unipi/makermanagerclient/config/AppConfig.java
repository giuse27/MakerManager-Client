package it.unipi.makermanagerclient.config;

/**
 * Classe di configurazione statica per il Client.
 */
public final class AppConfig {

    private AppConfig() {
        throw new UnsupportedOperationException(
            "AppConfig non può essere istanziata."
        );
    }

    // Indirizzo base del server Spring Boot
    public static final String BASE_URL = "http://localhost:8080";

    // Inizializzazione
    public static final String ENDPOINT_INIZIALIZZA = BASE_URL + "/inizializza";

    // Autenticazione
    public static final String ENDPOINT_AUTH_LOGIN = BASE_URL + "/auth/login";
    public static final String ENDPOINT_AUTH_REGISTRAZIONE = BASE_URL + "/auth/registrazione";

    // Utenti
    public static final String ENDPOINT_UTENTI = BASE_URL + "/api/utenti";
    public static final String ENDPOINT_UTENTI_ME = ENDPOINT_UTENTI + "/me";

    // Catalogo
    public static final String ENDPOINT_CATALOGO = BASE_URL + "/api/catalogo";

    // Inventario
    public static final String ENDPOINT_INVENTARIO = BASE_URL + "/api/inventario";
    public static final String ENDPOINT_INVENTARIO_UTENTE = ENDPOINT_INVENTARIO + "/utente";
    public static final String ENDPOINT_INVENTARIO_ARTICOLI = ENDPOINT_INVENTARIO + "/articoli";

    // Progetti
    public static final String ENDPOINT_PROGETTI = BASE_URL + "/api/progetti";
    public static final String ENDPOINT_PROGETTI_TIPOLOGIA = ENDPOINT_PROGETTI + "/tipologia";
    public static final String ENDPOINT_PROGETTI_CONSIGLIATI = ENDPOINT_PROGETTI + "/consigliati";

}
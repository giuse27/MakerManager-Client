package it.unipi.makermanagerclient.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Classe responsabile esclusivamente della comunicazione HTTP con il Server. 
 * Il Client dialoga con il Server via richieste HTTP con payload JSON.
 *
 * Viene usata una singola istanza condivisa di HttpClient; crearne una
 * nuova per ogni richiesta sarebbe uno spreco di risorse.
 */
public final class ApiClient {

    // configurazione della connessione
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private ApiClient() {
        throw new UnsupportedOperationException(
            "ApiClient è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Esegue una get non autenticata
     *
     * @param url url della chiamata
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse get(String url) 
            throws IOException, InterruptedException
    {
        return get(url, null);
    }

    /**
     * Esegue una get, aggiungendo l'header "Authorization: Bearer" se
     * viene passato un token (null per gli endpoint pubblici).
     *
     * @param url url della chiamata
     * @param token token JWT da usare per l'header Authorization, o null
     *              se la chiamata non richiede autenticazione
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse get(String url, String token)
            throws IOException, InterruptedException
    {

        HttpRequest.Builder richiesta = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .GET();

        aggiungiAutorizzazione(richiesta, token);

        return invia(richiesta.build());

    }

    /**
     * Esegue una post non autenticata
     *
     * @param url url della chiamata
     * @param corpoJson json serializzato come stringa
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse post(String url, String corpoJson)
            throws IOException, InterruptedException
    {
        return post(url, corpoJson, null);
    }

    /**
     * Esegue una post, aggiungendo l'header "Authorization: Bearer" se
     * viene passato un token (null per gli endpoint pubblici).
     *
     * @param url url della chiamata
     * @param corpoJson json serializzato come stringa
     * @param token token JWT da usare per l'header Authorization, o null
     *              se la chiamata non richiede autenticazione
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse post(String url, String corpoJson, String token)
            throws IOException, InterruptedException
    {

        // creo il body vero e proprio
        HttpRequest.BodyPublisher body;

        // lo costruisco e se il json è vuoto (es per /inizializza) lo creo vuoto
        if (corpoJson == null || corpoJson.isBlank()) {
            body = HttpRequest.BodyPublishers.noBody();
        } else {
            body = HttpRequest.BodyPublishers.ofString(corpoJson);
        }

        HttpRequest.Builder richiesta = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .POST(body);

        aggiungiAutorizzazione(richiesta, token);

        return invia(richiesta.build());

    }

    /**
     * Esegue una patch non autenticata
     *
     * @param url url della chiamata
     * @param corpoJson json serializzato come stringa
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse patch(String url, String corpoJson)
            throws IOException, InterruptedException
    {
        return patch(url, corpoJson, null);
    }

    /**
     * Esegue una patch, aggiungendo l'header "Authorization: Bearer" se
     * viene passato un token.
     *
     * @param url url della chiamata
     * @param corpoJson json serializzato come stringa
     * @param token token JWT da usare per l'header Authorization, o null
     *              se la chiamata non richiede autenticazione
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse patch(String url, String corpoJson, String token)
            throws IOException, InterruptedException
    {

        HttpRequest.BodyPublisher body = (corpoJson == null || corpoJson.isBlank())
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(corpoJson);

        HttpRequest.Builder richiesta = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .method("PATCH", body);

        aggiungiAutorizzazione(richiesta, token);

        return invia(richiesta.build());

    }

    /**
     * esegue una delete non autenticata
     *
     * @param url url della chiamata
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse delete(String url)
            throws IOException, InterruptedException
    {
        return delete(url, null);
    }

    /**
     * Esegue una delete, aggiungendo l'header "Authorization: Bearer" se
     * viene passato un token (null per gli endpoint pubblici).
     *
     * @param url url della chiamata
     * @param token token JWT da usare per l'header Authorization, o null
     *              se la chiamata non richiede autenticazione
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse delete(String url, String token)
            throws IOException, InterruptedException
    {

        HttpRequest.Builder richiesta = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .DELETE();

        aggiungiAutorizzazione(richiesta, token);

        return invia(richiesta.build());

    }

    /**
     * Aggiunge l'header "Authorization: Bearer {token}" alla richiesta in
     * costruzione, solo se e' stato fornito un token. Centralizzato qui
     * per non ripetere lo stesso if in get/post/delete.
     *
     * @param richiesta il builder della richiesta HTTP in costruzione
     * @param token token JWT, o null/vuoto se la chiamata e' pubblica
     */
    private static void aggiungiAutorizzazione(HttpRequest.Builder richiesta, String token) {
        if (token != null && !token.isBlank()) {
            richiesta.header("Authorization", "Bearer " + token);
        }
    }

    /**
     * Metodo di utilità privata per ridurre la ridonanza
     *
     * @param richiesta
     * @return Resituisce un record con lo status code e il body di risposta
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    private static ApiResponse invia(HttpRequest richiesta)
            throws IOException, InterruptedException
    {

        HttpResponse<String> response = HTTP_CLIENT.send(
            richiesta,
            HttpResponse.BodyHandlers.ofString()
        );

        return new ApiResponse(response.statusCode(), response.body());

    }

}
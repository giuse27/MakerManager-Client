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
     * Esegue una get
     * 
     * @param url url della chiamata
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse get(String url) 
            throws IOException, InterruptedException 
    {

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .GET()
                                .build();

        return invia(request);

    }

    /**
     * Esegue una post
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
    
        // creo il body vero e proprio
        HttpRequest.BodyPublisher body;

        // lo costruisco e se il json è vuoto (es per /inizializza) lo creo vuoto
        if (corpoJson == null || corpoJson.isBlank()) {
            body = HttpRequest.BodyPublishers.noBody();
        } else {
            body = HttpRequest.BodyPublishers.ofString(corpoJson);
        }

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .POST(body)
                                .build();

        return invia(request);

    }

    /**
     * esegue una delete
     * 
     * @param url url della chiamata
     * @return restituisce il body della risposta come stringa
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static ApiResponse delete(String url) 
            throws IOException, InterruptedException 
    {

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .DELETE()
                                .build();

        return invia(request);

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
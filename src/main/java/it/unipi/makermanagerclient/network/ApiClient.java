package it.unipi.makermanagerclient.network;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

/**
 * Classe responsabile esclusivamente della comunicazione HTTP con il Server. 
 * Il Client dialoga con il Server via richieste HTTP con payload JSON.
 *
 * Viene usata una singola istanza condivisa di HttpClient; crearne una
 * nuova per ogni richiesta sarebbe uno spreco di risorse.
 */
public final class ApiClient {

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
     * @param url
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static ApiResponse get(String url) 
            throws IOException, InterruptedException 
    {

        return null;

    }

    /**
     * Esegue una post
     * 
     * @param url
     * @param corpoJson
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static ApiResponse post(String url, String corpoJson) 
            throws IOException, InterruptedException 
    {

        return null;

    }

    public static ApiResponse delete(String url) 
            throws IOException, InterruptedException 
    {

        return null;

    }

    /**
     * Metodo di utilità privata per ridurre la ridonanza
     * 
     * @param richiesta
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private static ApiResponse invia(HttpRequest richiesta) 
            throws IOException, InterruptedException 
    {

        return null;

    }

}
package it.unipi.makermanagerclient.network;

import java.net.http.HttpClient;
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

}
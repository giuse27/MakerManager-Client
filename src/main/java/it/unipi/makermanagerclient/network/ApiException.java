package it.unipi.makermanagerclient.network;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.unipi.makermanagerclient.util.GsonProvider;

/**
 * Eccezione non controllata sollevata dal livello Service quando il
 * Server risponde con uno status di errore (4xx/5xx). Il messaggio viene
 * estratto dal corpo JSON della risposta, che il GlobalExceptionHandler
 * del Server restituisce sempre come mappa di stringhe: o con una
 * singola chiave "errore", o con una chiave per ogni campo non valido
 * (errori di validazione su @NotBlank, @Email, ecc.).
 */
public class ApiException extends RuntimeException {

    // metodo standard per creare una mappa a partire da tipi generici (utile
    // soprattutto quando ci sono più errori in una risposta del server)
    private static final Type MAPPA_ERRORI = new TypeToken<Map<String, String>>() {}.getType();

    private final int statusCode;

    public ApiException(int statusCode, String messaggio) {
        super(messaggio);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Costruisce un'ApiException a partire da una risposta HTTP di
     * errore, provando a estrarne un messaggio leggibile dal corpo JSON.
     *
     * @param risposta la risposta del Server, con isSuccesso() falso
     * @return l'eccezione pronta da lanciare
     */
    public static ApiException da(ApiResponse risposta) {
        return new ApiException(
            risposta.statusCode(), 
            estraiMessaggio(risposta.body()
        ));
    }

    private static String estraiMessaggio(String corpo) {

        if (corpo == null || corpo.isBlank()) {
            return "Il server ha risposto con un errore senza dettagli.";
        }

        try {

            // provo a deserializzare la risposta del server
            Map<String, String> campiErrore = GsonProvider
                                                .get()
                                                .fromJson(
                                                    corpo, 
                                                    MAPPA_ERRORI
                                                );
            
            // caso 1 non ci sono errori
            if (campiErrore == null || campiErrore.isEmpty()) {
                return corpo;
            }

            // caso 2 c'è un errore
            if (campiErrore.containsKey("errore")) {
                return campiErrore.get("errore");
            }

            // caso 3 ci sono più errori
            // Errori di validazione: una chiave per ogni campo non valido
            return String.join("; ", campiErrore.values());

        } catch (JsonSyntaxException e) {
            return corpo;
        }

    }

}

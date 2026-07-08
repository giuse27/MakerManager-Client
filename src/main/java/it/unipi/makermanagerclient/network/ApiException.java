package it.unipi.makermanagerclient.network;

/**
 * Eccezione non controllata sollevata dal livello Service quando il
 * Server risponde con uno status di errore (4xx/5xx). Il messaggio viene
 * estratto dal corpo JSON della risposta, che il GlobalExceptionHandler
 * del Server restituisce sempre come mappa di stringhe: o con una
 * singola chiave "errore", o con una chiave per ogni campo non valido
 * (errori di validazione su @NotBlank, @Email, ecc.).
 */
public class ApiException extends RuntimeException {

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

        // TODO
        return "";

    }

}

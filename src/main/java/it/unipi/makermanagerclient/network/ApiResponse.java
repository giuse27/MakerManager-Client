package it.unipi.makermanagerclient.network;

/**
 * Rappresenta il risultato grezzo di una chiamata HTTP: codice di stato
 * e corpo della risposta come testo.
 *
 * E' un semplice record (nessuna logica), usato da ApiClient per non far
 * perdere al chiamante (il controller) l'informazione sullo status code
 */
public record ApiResponse(int statusCode, String body) {

    public boolean isSuccesso() {
        return statusCode >= 200 && statusCode < 300;
    }

}
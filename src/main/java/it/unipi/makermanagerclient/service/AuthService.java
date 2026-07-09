package it.unipi.makermanagerclient.service;

import java.io.IOException;
import java.util.Map;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.model.RispostaAutenticazioneDTO;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.network.ApiResponse;
import it.unipi.makermanagerclient.util.GsonProvider;

/**
 * Classe di service responsabile dell'autenticazione
 * comunica con /auth/* lanciando ApiException se il Server risponde con un 
 * errore (credenziali errate, email/nickname già in uso, dati non validi ecc)
 */
public final class AuthService {

    private AuthService() {
        throw new UnsupportedOperationException(
            "AuthService è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Autentica un utente esistente.
     *
     * @throws ApiException se le credenziali non sono corrette (401) o i dati non sono validi (400)
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static RispostaAutenticazioneDTO login(
        String email, 
        String password
    )
            throws IOException, InterruptedException
    {

        String corpo = GsonProvider.get().toJson(Map.of(
                "email", email,
                "password", password
        ));

        return leggiEsito(ApiClient.post(AppConfig.ENDPOINT_AUTH_LOGIN, corpo));

    }

    /**
     * Registra un nuovo utente (ruolo UTENTE) e lo autentica subito.
     *
     * @throws ApiException se email/nickname sono già in uso (409) o i dati non sono validi (400)
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static RispostaAutenticazioneDTO registrati(
        String nickname, 
        String email, 
        String password
    )
            throws IOException, InterruptedException
    {

        String corpo = GsonProvider.get().toJson(Map.of(
                "nickname", nickname,
                "email", email,
                "password", password
        ));

        return leggiEsito(ApiClient.post(AppConfig.ENDPOINT_AUTH_REGISTRAZIONE, corpo));

    }

    // deserializza il json di risposta
    private static RispostaAutenticazioneDTO leggiEsito(ApiResponse risposta) {

        // se l'esito è negativo trovo un'ApiException che contiene status code
        // e messaggio estratto
        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        return GsonProvider.get().fromJson(risposta.body(), RispostaAutenticazioneDTO.class);

    }

}

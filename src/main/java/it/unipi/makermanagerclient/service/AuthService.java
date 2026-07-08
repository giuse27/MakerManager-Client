package it.unipi.makermanagerclient.service;

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

}

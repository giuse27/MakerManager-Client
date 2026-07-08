package it.unipi.makermanagerclient.util;

import com.google.gson.Gson;

/**
 * Punto di accesso unico all'istanza condivisa di Gson usata da tutto il
 * Client per (de)serializzare i corpi JSON scambiati con il Server. Una
 * singola istanza va condivisa perche' la creazione di un Gson non e'
 * gratuita, e l'istanza e' comunque immutabile e thread-safe.
 */
public final class GsonProvider {

    private static final Gson ISTANZA = new Gson();

    private GsonProvider() {
        throw new UnsupportedOperationException(
            "GsonProvider è una classe di utilità statica e non può essere istanziata."
        );
    }

    public static Gson get() {
        return ISTANZA;
    }

}

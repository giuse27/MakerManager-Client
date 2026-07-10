package it.unipi.makermanagerclient.service;

import java.io.IOException;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.network.ApiResponse;
import it.unipi.makermanagerclient.sessione.Sessione;

/**
 * operazioni riservate all'admin come /inizializza
 */
public final class AmministrazioneService {

    private AmministrazioneService() {
        throw new UnsupportedOperationException(
            "AmministrazioneService è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Reinizializza il database del Server con i dati di
     * src/main/resources/data/inizializzazione.json (distruttivo:
     * cancella tutto tranne l'ADMIN di default).
     *
     * @throws ApiException se chi chiama non e' ADMIN (403)
     * @throws IOException se la richiesta fallisce (es. server non raggiungibile)
     * @throws InterruptedException se viene interrotto durante l'attesa
     */
    public static void inizializzaDatabase()
            throws IOException, InterruptedException
    {

        ApiResponse risposta = ApiClient.post(
                AppConfig.ENDPOINT_INIZIALIZZA, null, Sessione.getIstanza().getToken()
        );

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

    }

}

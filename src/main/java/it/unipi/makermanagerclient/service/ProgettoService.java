package it.unipi.makermanagerclient.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.model.ProgettoConBomDTO;
import it.unipi.makermanagerclient.model.ProgettoConsigliatoDTO;
import it.unipi.makermanagerclient.model.ProgettoDTO;
import it.unipi.makermanagerclient.model.RigaBOMDTO;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.network.ApiResponse;
import it.unipi.makermanagerclient.sessione.Sessione;
import it.unipi.makermanagerclient.util.GsonProvider;

/**
 * Classe di servizio per il dominio Progetti: comunica con gli endpoint
 * /api/progetti (elenco, dettaglio+BOM, creazione/eliminazione,
 * gestione righe BOM) e /api/progetti/consigliati, e traduce le
 * risposte JSON in DTO tipizzati.
 */
public final class ProgettoService {

    private static final Type LISTA_PROGETTI = new TypeToken<List<ProgettoDTO>>() {}.getType();
    private static final Type LISTA_CONSIGLIATI = new TypeToken<List<ProgettoConsigliatoDTO>>() {}.getType();

    private ProgettoService() {
        throw new UnsupportedOperationException(
            "ProgettoService è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Elenco di tutti i progetti
     */
    public static List<ProgettoDTO> elencoTutti()
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_PROGETTI;
        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), LISTA_PROGETTI);

    }

    /**
     * Elenco dei progetti creati da un utente (vista sintetica, senza BOM).
     */
    public static List<ProgettoDTO> elencoDiUtente(long idUtente)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_PROGETTI + "/utente/" + idUtente;
        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), LISTA_PROGETTI);

    }

    /**
     * Dettaglio completo di un progetto, inclusa la sua B.O.M.
     */
    public static ProgettoConBomDTO dettaglio(long idProgetto)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto;
        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), ProgettoConBomDTO.class);

    }

    /**
     * Crea un nuovo progetto (con B.O.M. vuota) per l'utente autenticato,
     * sempre autore del progetto.
     */
    public static ProgettoDTO crea(String tipo, String nome, String descrizione)
            throws IOException, InterruptedException
    {

        // serializzo il json
        String corpo = GsonProvider.get().toJson(Map.of(
                "tipo", tipo,
                "nome", nome,
                "descrizione", descrizione == null ? "" : descrizione
        ));

        ApiResponse risposta = ApiClient.post(
                AppConfig.ENDPOINT_PROGETTI, corpo, Sessione.getIstanza().getToken()
        );

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), ProgettoDTO.class);

    }

    /**
     * Elimina un progetto (e, in cascata lato Server, tutte le righe della sua B.O.M.).
     */
    public static void elimina(long idProgetto)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto;
        ApiResponse risposta = ApiClient.delete(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

    }

    /**
     * Aggiunge una riga alla B.O.M. di un progetto, a partire da un
     * elemento di catalogo gia' esistente.
     */
    public static RigaBOMDTO aggiungiRigaBom(long idProgetto, long idElementoCatalogo, int quantita)
            throws IOException, InterruptedException
    {

        // serializzo il json
        String corpo = GsonProvider.get().toJson(Map.of(
                "idElementoCatalogo", idElementoCatalogo,
                "quantita", quantita
        ));

        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto + "/bom";
        ApiResponse risposta = ApiClient.post(url, corpo, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), RigaBOMDTO.class);

    }

    /**
     * Aggiorna la quantita' richiesta di una riga della B.O.M. GIA'
     * ESISTENTE, sostituendo il valore corrente con nuovaQuantita' (non
     * e' un incremento): chi chiama deve gia' aver calcolato il nuovo
     * valore assoluto.
     */
    public static RigaBOMDTO aggiornaQuantitaRigaBom(long idProgetto, long idRiga, int nuovaQuantita)
            throws IOException, InterruptedException
    {

        // serializzo
        String corpo = GsonProvider.get().toJson(Map.of("quantita", nuovaQuantita));

        // url
        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto + "/bom/" + idRiga;

        ApiResponse risposta = ApiClient.patch(url, corpo, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), RigaBOMDTO.class);

    }

    /**
     * Elimina una riga dalla B.O.M. di un progetto.
     */
    public static void eliminaRigaBom(long idProgetto, long idRiga)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto + "/bom/" + idRiga;
        ApiResponse risposta = ApiClient.delete(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

    }

    /**
     * Progetti suggeriti all'utente autenticato in base alle
     * disponibilità di tutti i suoi inventari.
     *
     * @param sogliaMancanti soglia opzionale (vedi Impostazioni): se
     *                       null il parametro viene omesso e il Server
     *                       applica il proprio valore di default
     */
    public static List<ProgettoConsigliatoDTO> consigliati(Integer sogliaMancanti)
            throws IOException, InterruptedException
    {

        // scelgo quale endpoint usare sulla base della soglia
        String url = sogliaMancanti == null
                ? AppConfig.ENDPOINT_PROGETTI_CONSIGLIATI
                : AppConfig.ENDPOINT_PROGETTI_CONSIGLIATI + "?sogliaMancanti=" + sogliaMancanti;

        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo
        return GsonProvider.get().fromJson(risposta.body(), LISTA_CONSIGLIATI);

    }

}

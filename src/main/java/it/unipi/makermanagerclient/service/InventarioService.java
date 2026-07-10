package it.unipi.makermanagerclient.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.model.ArticoloInventarioDTO;
import it.unipi.makermanagerclient.model.InventarioDTO;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.network.ApiResponse;
import it.unipi.makermanagerclient.sessione.Sessione;
import it.unipi.makermanagerclient.util.GsonProvider;

/**
 * Classe di servizio per il dominio Inventario: comunica con gli
 * endpoint /api/inventario e /api/inventario/articoli (autenticati,
 * token letto dalla Sessione) e traduce le risposte JSON in DTO
 * tipizzati.
 *
 * Ancora da implementare.
 */
public final class InventarioService {

    private static final Type LISTA_INVENTARI = new TypeToken<List<InventarioDTO>>() {}.getType();
    private static final Type LISTA_ARTICOLI = new TypeToken<List<ArticoloInventarioDTO>>() {}.getType();

    private InventarioService() {
        throw new UnsupportedOperationException(
            "InventarioService è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Restituisce gli inventari dell'utente autenticato, con il campo
     * client-only numeroArticoli gia' popolato: per ciascun inventario
     * serve una chiamata aggiuntiva (GET /api/inventario/{id}), perche'
     * il Server non espone un conteggio diretto.
     */
    public static List<InventarioDTO> elencoInventariConConteggio()
            throws IOException, InterruptedException
    {

        // trovo tutti gli inventari di un utente
        List<InventarioDTO> inventari = elencoInventariUtente();

        // per ogni inventario calcolo il numero di articoli
        for (InventarioDTO inventario : inventari) {
            List<ArticoloInventarioDTO> contenuto = contenutoInventario(inventario.getId());
            inventario.setNumeroArticoli(contenuto.size());
        }

        return inventari;

    }

    /**
     * Elenco "grezzo" degli inventari dell'utente autenticato
     * GET /api/inventario/utente/{idUtente}.
     */
    public static List<InventarioDTO> elencoInventariUtente()
            throws IOException, InterruptedException
    {

        Long idUtente = Sessione.getIstanza().getIdUtente();
        String url = AppConfig.ENDPOINT_INVENTARIO_UTENTE + "/" + idUtente;

        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        // se ci sono stati errori trovo un'ApiException
        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo la risposta del server in LISTA_INVENTARI
        return GsonProvider.get().fromJson(risposta.body(), LISTA_INVENTARI);

    }

    /**
     * Contenuto (articoli) di un singolo inventario,
     * GET /api/inventario/{idInventario}.
     */
    public static List<ArticoloInventarioDTO> contenutoInventario(long idInventario)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_INVENTARIO + "/" + idInventario;
        ApiResponse risposta = ApiClient.get(url, Sessione.getIstanza().getToken());

        // se ci sono stati errori trovo un'ApiException
        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo la risposta del server in LISTA_ARTICOLI
        return GsonProvider.get().fromJson(risposta.body(), LISTA_ARTICOLI);

    }

}

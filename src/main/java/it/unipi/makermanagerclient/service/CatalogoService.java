package it.unipi.makermanagerclient.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.network.ApiResponse;
import it.unipi.makermanagerclient.sessione.Sessione;
import it.unipi.makermanagerclient.util.GsonProvider;

/**
 * Classe di servizio per il dominio Catalogo: comunica con
 * GET/POST/DELETE /api/catalogo e traduce le risposte JSON in DTO
 * tipizzati.
 */
public final class CatalogoService {

    // lista di elementi catalogo per Gson
    private static final Type LISTA_ELEMENTI = new TypeToken<List<ElementoCatalogoDTO>>() {}.getType();

    private CatalogoService() {
        throw new UnsupportedOperationException(
            "CatalogoService è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Elenco completo del catalogo condiviso (PUBBLICO lato Server, ma
     * qui passiamo comunque il token se presente).
     */
    public static List<ElementoCatalogoDTO> elenco()
            throws IOException, InterruptedException
    {

        // chiamata al server
        ApiResponse risposta = ApiClient.get(AppConfig.ENDPOINT_CATALOGO, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo la risposta
        return GsonProvider.get().fromJson(risposta.body(), LISTA_ELEMENTI);

    }

    /**
     * Crea un nuovo elemento nel catalogo condiviso.
     */
    public static ElementoCatalogoDTO crea(String nome, String descrizione, String tipologia)
            throws IOException, InterruptedException
    {

        // serializzo il dto
        String corpo = GsonProvider.get().toJson(Map.of(
                "nome", nome,
                "descrizione", descrizione,
                "tipologia", tipologia
        ));

        ApiResponse risposta = ApiClient.post(
                AppConfig.ENDPOINT_CATALOGO, corpo, Sessione.getIstanza().getToken()
        );

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

        // deserializzo la risposta
        return GsonProvider.get().fromJson(risposta.body(), ElementoCatalogoDTO.class);

    }

    /**
     * Elimina un elemento dal catalogo condiviso (ADMIN). Risponde con
     * ApiException a statusCode 500 se viola le FK del DB
     */
    public static void elimina(long idElemento)
            throws IOException, InterruptedException
    {

        String url = AppConfig.ENDPOINT_CATALOGO + "/" + idElemento;
        ApiResponse risposta = ApiClient.delete(url, Sessione.getIstanza().getToken());

        if (!risposta.isSuccesso()) {
            throw ApiException.da(risposta);
        }

    }


}
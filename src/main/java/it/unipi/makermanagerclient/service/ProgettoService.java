package it.unipi.makermanagerclient.service;

/**
 * Classe di servizio per il dominio Progetti: comunica con gli endpoint
 * /api/progetti (elenco, dettaglio+BOM, creazione/eliminazione,
 * gestione righe BOM) e /api/progetti/consigliati, e traduce le
 * risposte JSON in DTO tipizzati.
 *
 * Ancora da implementare.
 */
public final class ProgettoService {

    private ProgettoService() {
        throw new UnsupportedOperationException(
            "ProgettoService è una classe di utilità statica e non può essere istanziata."
        );
    }

}

package it.unipi.makermanagerclient.controller;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.util.OutputFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller del pannello Inizializzazione.
 *
 * Gestisce l'unico endpoint del dominio: POST /inizializza (nessun
 * parametro in input). Segue lo stesso principio "un controller per
 * dominio di endpoint" usato per gli altri pannelli.
 */
public class InizializzazioneController {

    @FXML
    private TextArea areaOutput;

    @FXML
    private void onInizializza() {
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /inizializza",
                () -> ApiClient.post(AppConfig.ENDPOINT_INIZIALIZZA, null)
        );
    }

}
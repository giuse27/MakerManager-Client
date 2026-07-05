package it.unipi.makermanagerclient.controller;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.util.OutputFormatter;
import it.unipi.makermanagerclient.util.SpinnerFactories;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller del pannello Progetti.
 *
 * Gestisce i 7 endpoint del dominio. Il parametro idProgetto NON e'
 * definito in questo pannello ma nella sidebar (shell.fxml), perche'
 * condiviso tra piu' blocchi
 */
public class ProgettoController implements Initializable {

    // Gemelli dell'enum TipologiaProgetto
    private static final List<String> TIPOLOGIE_PROGETTO = List.of(
            "STAMPA_3D", "ELETTRONICA", "ROBOTICA", "SOFTWARE"
    );

    // Riferimento allo Spinner definito nella sidebar (shell.fxml),
    // iniettato da ShellController.
    private Spinner<Integer> spinnerIdProgettoCondiviso;

    @FXML
    private ComboBox<String> comboTipologiaFiltro;

    @FXML
    private ComboBox<String> comboTipologiaCrea;

    @FXML
    private TextField campoNomeProgetto;

    @FXML
    private TextField campoDescrizioneProgetto;

    @FXML
    private Spinner<Integer> spinnerIdElementoCatalogoBom;

    @FXML
    private Spinner<Integer> spinnerQuantitaBom;

    @FXML
    private Spinner<Integer> spinnerIdRigaBom;

    @FXML
    private TextArea areaOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboTipologiaFiltro.getItems().addAll(TIPOLOGIE_PROGETTO);
        comboTipologiaCrea.getItems().addAll(TIPOLOGIE_PROGETTO);

        spinnerIdElementoCatalogoBom.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );
        spinnerQuantitaBom.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );
        spinnerIdRigaBom.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );

    }

    /**
     * Chiamato da ShellController subito dopo il caricamento del pannello,
     * per collegare questo controller allo Spinner condiviso idProgetto
     */
    public void impostaSpinnerCondiviso(Spinner<Integer> spinnerIdProgetto) {
        this.spinnerIdProgettoCondiviso = spinnerIdProgetto;
    }

    @FXML
    private void onTuttiIProgetti() {
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/progetti",
                () -> ApiClient.get(AppConfig.ENDPOINT_PROGETTI)
        );
    }

    @FXML
    private void onDettaglioProgetto() {
        Integer idProgetto = spinnerIdProgettoCondiviso.getValue();
        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/progetti/" + idProgetto,
                () -> ApiClient.get(url)
        );
    }

    @FXML
    private void onProgettiPerTipologia() {
        String tipologia = comboTipologiaFiltro.getValue() != null ? comboTipologiaFiltro.getValue() : "";
        String url = AppConfig.ENDPOINT_PROGETTI_TIPOLOGIA + "/" + tipologia;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/progetti/tipologia/" + tipologia,
                () -> ApiClient.get(url)
        );
    }

    @FXML
    private void onCreaProgetto() {

        String tipo = comboTipologiaCrea.getValue() != null ? comboTipologiaCrea.getValue() : "";
        String corpo = """
                {
                  "tipo": "%s",
                  "nome": "%s",
                  "descrizione": "%s"
                }
                """.formatted(tipo, escapaJson(campoNomeProgetto.getText()), escapaJson(campoDescrizioneProgetto.getText()));

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /api/progetti",
                () -> ApiClient.post(AppConfig.ENDPOINT_PROGETTI, corpo)
        );

    }

    @FXML
    private void onEliminaProgetto() {
        Integer idProgetto = spinnerIdProgettoCondiviso.getValue();
        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "DELETE /api/progetti/" + idProgetto,
                () -> ApiClient.delete(url)
        );
    }

    @FXML
    private void onAggiungiRigaBom() {

        Integer idProgetto = spinnerIdProgettoCondiviso.getValue();
        Integer idElementoCatalogo = spinnerIdElementoCatalogoBom.getValue();
        Integer quantita = spinnerQuantitaBom.getValue();

        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto + "/bom";
        String corpo = """
                {
                  "idElementoCatalogo": %d,
                  "quantita": %d
                }
                """.formatted(idElementoCatalogo, quantita);

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /api/progetti/" + idProgetto + "/bom",
                () -> ApiClient.post(url, corpo)
        );

    }

    @FXML
    private void onEliminaRigaBom() {

        Integer idProgetto = spinnerIdProgettoCondiviso.getValue();
        Integer idRiga = spinnerIdRigaBom.getValue();
        String url = AppConfig.ENDPOINT_PROGETTI + "/" + idProgetto + "/bom/" + idRiga;

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "DELETE /api/progetti/" + idProgetto + "/bom/" + idRiga,
                () -> ApiClient.delete(url)
        );

    }

    // controlli semplici per la rottura del json
    private String escapaJson(String testo) {
        return testo == null ? "" : testo.replace("\"", "\\\"");
    }

}
package it.unipi.makermanagerclient.controller;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.util.OutputFormatter;
import it.unipi.makermanagerclient.util.SpinnerFactories;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller del pannello Catalogo.
 *
 * Gestisce i 3 endpoint del dominio: GET (lista), POST (crea), DELETE
 * (elimina per id). Nessun parametro e' condiviso con altri domini,
 * quindi tutti i campi restano locali a questo pannello (a differenza
 * di Inventario/Progetti, che leggono alcuni id dalla sidebar).
 */
public class CatalogoController implements Initializable {

    // Valori validi per il campo "tipologia", gemelli poveri (solo
    // stringhe) dell'enum TipologiaElemento definito lato Server: il
    // Client non ha e non deve avere alcun legame di compilazione con
    // quell'enum, quindi qui si tratta semplicemente di testo.
    private static final List<String> TIPOLOGIE_ELEMENTO = List.of(
            "COMPONENTE_ELETTRONICO", "MATERIALE_CONSUMABILE", "ATTREZZO_DA_LAVORO", "SOFTWARE"
    );

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoDescrizione;

    @FXML
    private ComboBox<String> comboTipologia;

    @FXML
    private Spinner<Integer> spinnerIdElimina;

    @FXML
    private TextArea areaOutput;

    @FXML
    private Button bottoneLista;

    @FXML
    private Button bottoneCrea;

    @FXML
    private Button bottoneElimina;

    /**
     * Chiamato automaticamente da FXMLLoader subito dopo l'iniezione dei
     * campi @FXML: qui popoliamo la ComboBox e configuriamo lo Spinner,
     * operazioni che vanno fatte una sola volta all'avvio del pannello.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboTipologia.getItems().addAll(TIPOLOGIE_ELEMENTO);

        // SpinnerValueFactory: definisce il range di valori accettati
        // dallo Spinner (qui: interi da 1 in su, valore iniziale 1).
        spinnerIdElimina.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );

    }

    @FXML
    private void onLista() {
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/catalogo",
                () -> ApiClient.get(AppConfig.ENDPOINT_CATALOGO)
        );
    }

    @FXML
    private void onCrea() {

        String corpo = """
                {
                  "nome": "%s",
                  "descrizione": "%s",
                  "tipologia": "%s"
                }
                """.formatted(
                escapaJson(campoNome.getText()),
                escapaJson(campoDescrizione.getText()),
                comboTipologia.getValue() != null ? comboTipologia.getValue() : ""
        );

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /api/catalogo",
                () -> ApiClient.post(AppConfig.ENDPOINT_CATALOGO, corpo)
        );

    }

    @FXML
    private void onElimina() {

        String url = AppConfig.ENDPOINT_CATALOGO + "/" + spinnerIdElimina.getValue();

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "DELETE /api/catalogo/" + spinnerIdElimina.getValue(),
                () -> ApiClient.delete(url)
        );

    }

    /**
     * Escape minimo per i caratteri che romperebbero la sintassi JSON se
     * l'utente li digitasse nei campi di testo (es. virgolette). Non e'
     * un parser JSON completo: per una GUI di puro test e' sufficiente
     * gestire il caso piu' comune.
     */
    private String escapaJson(String testo) {
        return testo == null ? "" : testo.replace("\"", "\\\"");
    }

}
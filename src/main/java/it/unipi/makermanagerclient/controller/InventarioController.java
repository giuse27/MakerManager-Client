package it.unipi.makermanagerclient.controller;

import it.unipi.makermanagerclient.config.AppConfig;
import it.unipi.makermanagerclient.network.ApiClient;
import it.unipi.makermanagerclient.util.OutputFormatter;
import it.unipi.makermanagerclient.util.SpinnerFactories;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller del pannello Inventario.
 *
 * Gestisce i 7 endpoint del dominio. A differenza di CatalogoController,
 * due parametri (idInventario, idUtente) NON sono definiti in questo
 * pannello ma nella sidebar (shell.fxml), perche' condivisi tra piu'
 * blocchi: ShellController li inietta qui tramite
 * impostaSpinnerCondivisi(), chiamato subito dopo il caricamento FXML.
 */
public class InventarioController implements Initializable {

    // Riferimenti agli Spinner definiti nella sidebar (shell.fxml),
    // iniettati da ShellController: non sono @FXML perche' non
    // appartengono al file FXML di QUESTO pannello.
    private Spinner<Integer> spinnerIdInventarioCondiviso;
    private Spinner<Integer> spinnerIdUtenteCondiviso;

    @FXML
    private TextField campoNomeInventario;

    @FXML
    private Spinner<Integer> spinnerIdElementoCatalogo;

    @FXML
    private Spinner<Integer> spinnerQuantitaArticolo;

    @FXML
    private Spinner<Integer> spinnerIdArticolo;

    @FXML
    private TextArea areaOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spinnerIdElementoCatalogo.setValueFactory(
                // funzione di utilità per evitare eccezioni in console e rendere
                // il valore dell'intero sicuro
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );
        spinnerQuantitaArticolo.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );
        spinnerIdArticolo.setValueFactory(
                SpinnerFactories.interoSicuro(1, Integer.MAX_VALUE, 1)
        );

    }

    /**
     * Chiamato da ShellController subito dopo il caricamento del pannello,
     * per collegare questo controller agli Spinner condivisi definiti
     * nella sidebar. Necessario perche' FXMLLoader inietta automaticamente
     * solo i campi @FXML dichiarati nel file FXML caricato
     */
    public void impostaSpinnerCondivisi(
        Spinner<Integer> spinnerIdInventario, 
        Spinner<Integer> spinnerIdUtente
    ) {
        this.spinnerIdInventarioCondiviso = spinnerIdInventario;
        this.spinnerIdUtenteCondiviso = spinnerIdUtente;
    }

    @FXML
    private void onTuttiGliInventari() {
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/inventario",
                () -> ApiClient.get(AppConfig.ENDPOINT_INVENTARIO)
        );
    }

    @FXML
    private void onContenutoInventario() {
        Integer idInventario = spinnerIdInventarioCondiviso.getValue();
        String url = AppConfig.ENDPOINT_INVENTARIO + "/" + idInventario;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/inventario/" + idInventario,
                () -> ApiClient.get(url)
        );
    }

    @FXML
    private void onInventariUtente() {
        Integer idUtente = spinnerIdUtenteCondiviso.getValue();
        String url = AppConfig.ENDPOINT_INVENTARIO_UTENTE + "/" + idUtente;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "GET /api/inventario/utente/" + idUtente,
                () -> ApiClient.get(url)
        );
    }

    @FXML
    private void onCreaInventario() {

        Integer idUtente = spinnerIdUtenteCondiviso.getValue();
        String corpo = """
                {
                  "nome": "%s",
                  "idUtente": %d
                }
                """.formatted(escapaJson(campoNomeInventario.getText()), idUtente);

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /api/inventario",
                () -> ApiClient.post(AppConfig.ENDPOINT_INVENTARIO, corpo)
        );

    }

    @FXML
    private void onEliminaInventario() {
        Integer idInventario = spinnerIdInventarioCondiviso.getValue();
        String url = AppConfig.ENDPOINT_INVENTARIO + "/" + idInventario;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "DELETE /api/inventario/" + idInventario,
                () -> ApiClient.delete(url)
        );
    }

    @FXML
    private void onAggiungiArticolo() {

        Integer idInventario = spinnerIdInventarioCondiviso.getValue();
        Integer idElementoCatalogo = spinnerIdElementoCatalogo.getValue();
        Integer quantita = spinnerQuantitaArticolo.getValue();

        String corpo = """
                {
                  "idElementoCatalogo": %d,
                  "idInventario": %d,
                  "quantita": %d
                }
                """.formatted(idElementoCatalogo, idInventario, quantita);

        OutputFormatter.eseguiEMostra(
                areaOutput,
                "POST /api/inventario/articoli",
                () -> ApiClient.post(AppConfig.ENDPOINT_INVENTARIO_ARTICOLI, corpo)
        );

    }

    @FXML
    private void onEliminaArticolo() {
        Integer idArticolo = spinnerIdArticolo.getValue();
        String url = AppConfig.ENDPOINT_INVENTARIO_ARTICOLI + "/" + idArticolo;
        OutputFormatter.eseguiEMostra(
                areaOutput,
                "DELETE /api/inventario/articoli/" + idArticolo,
                () -> ApiClient.delete(url)
        );
    }

    // controllo semplice per i test che evita la rottuta del json
    private String escapaJson(String testo) {
        return testo == null ? "" : testo.replace("\"", "\\\"");
    }

}
package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.sessione.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller della Shell principale: sidebar di navigazione fissa con area
 * contenuti. Precarica una sola volta i 6 pannelli (Dashboard, Tutti i 
 * progetti, Inventario, I miei progetti, Database, Impostazioni) dentro
 * areaContenuti (uno StackPane).
 */
public class ShellController implements Initializable {

    @FXML
    private StackPane areaContenuti;

    @FXML
    private Label etichettaPannelloCorrente;

    @FXML
    private Label etichettaNickname;

    // Pannelli precaricati, indicizzati per nome visualizzato (usato
    // anche come titolo nella barra superiore).
    private final Map<String, Parent> pannelli = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        etichettaNickname.setText(Sessione.getIstanza().getNickname());

        caricaPannello("Dashboard", "pannello-dashboard.fxml");
        caricaPannello("Tutti i progetti", "pannello-tutti-progetti.fxml");
        caricaPannello("Inventario", "pannello-inventario.fxml");
        caricaPannello("I miei progetti", "pannello-miei-progetti.fxml");
        caricaPannello("Database", "pannello-database.fxml");
        caricaPannello("Impostazioni", "pannello-impostazioni.fxml");

        // All'avvio il sistema si trova sulla Dashboard principale
        mostra("Dashboard");

    }

    private void caricaPannello(String nomeVisualizzato, String nomeFile) {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/it/unipi/makermanagerclient/" + nomeFile)
        );

        try {
            Parent radice = loader.load();
            pannelli.put(nomeVisualizzato, radice);
            areaContenuti.getChildren().add(radice);
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile caricare il pannello: " + nomeFile, e);
        }

    }

    /**
     * Mostra esclusivamente il pannello indicato (nascondendo gli
     * altri) e aggiorna la barra superiore con il suo nome. Usiamo sia
     * setVisible che setManaged: setVisible nasconde graficamente il
     * nodo, setManaged evita che continui comunque a occupare spazio
     * nel layout.
     */
    private void mostra(String nomeVisualizzato) {

        Parent daMostrare = pannelli.get(nomeVisualizzato);

        for (Parent pannello : pannelli.values()) {
            boolean pannelloGiusto = (pannello == daMostrare);
            pannello.setVisible(pannelloGiusto);
            pannello.setManaged(pannelloGiusto);
        }

        etichettaPannelloCorrente.setText(nomeVisualizzato);

    }

    @FXML
    private void mostraDashboard() {
        mostra("Dashboard");
    }

    @FXML
    private void mostraTuttiProgetti() {
        mostra("Tutti i progetti");
    }

    @FXML
    private void mostraInventario() {
        mostra("Inventario");
    }

    @FXML
    private void mostraMieiProgetti() {
        mostra("I miei progetti");
    }

    @FXML
    private void mostraDatabase() {
        mostra("Database");
    }

    @FXML
    private void mostraImpostazioni() {
        mostra("Impostazioni");
    }

}

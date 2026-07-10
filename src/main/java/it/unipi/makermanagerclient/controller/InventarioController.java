package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.InventarioDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.InventarioService;
import it.unipi.makermanagerclient.util.EsecutoreAsincrono;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller del pannello Inventario
 */
public class InventarioController implements Initializable, PannelloRicaricabile {

    @FXML
    private Label etichettaNumeroInventari;

    @FXML
    private TableView<InventarioDTO> tabellaInventari;

    @FXML
    private TableColumn<InventarioDTO, String> colonnaNomeInventario;

    @FXML
    private TableColumn<InventarioDTO, Number> colonnaNumeroArticoli;

    private final ObservableList<InventarioDTO> inventari = FXCollections.observableArrayList();

    // attiva o disattiva l'eliminazione di un'inventario
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNomeInventario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaNumeroArticoli.setCellValueFactory(new PropertyValueFactory<>("numeroArticoli"));

        tabellaInventari.setItems(inventari);
        tabellaInventari.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * Ricarica il pannello
     */
    @Override
    public void ricarica() {

        EsecutoreAsincrono.esegui(
            InventarioService::elencoInventariConConteggio,

            lista -> {
                inventari.setAll(lista);
                etichettaNumeroInventari.setText("Numero di inventari: " + lista.size());
            },

            errore -> mostraErrore("Impossibile caricare gli inventari", errore)
        );

    }

    private TableRow<InventarioDTO> caricaRiga() {

        TableRow<InventarioDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty()) {
                return;
            }

            if (modalitaEliminazione) {
                chiediConfermaEdElimina(riga.getItem());
            } else {
                apriContenutoInventario(riga.getItem());
            }

        });

        return riga;

    }

    @FXML
    private void onCreaInventario() {

    }

    @FXML
    private void onEliminaInventario() {

    }

    /**
     * visualizza il contenuto di un inventario in una nuova finestra
     * @param item inventario selezionato
     */
    private void apriContenutoInventario(InventarioDTO item) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apriContenutoInventario'");
    
    }

    /**
     * chiedi conferma con un pop up ed elimina un inventario
     * @param item inventario selezionato
     */
    private void chiediConfermaEdElimina(InventarioDTO item) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chiediConfermaEdElimina'");
    
    }

    private void mostraErrore(String titolo, Throwable errore) {

        String messaggio = (errore instanceof ApiException apiException)
                ? apiException.getMessage()
                : "Impossibile contattare il server: " + errore.getMessage();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();

    }

}

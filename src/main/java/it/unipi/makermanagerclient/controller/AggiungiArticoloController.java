package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller del popup "Aggiungi articolo"
 */
public class AggiungiArticoloController implements Initializable {

    // Valori validi per "tipologia"
    private static final List<String> TIPOLOGIE_ELEMENTO = List.of(
            "COMPONENTE_ELETTRONICO", "MATERIALE_CONSUMABILE", "ATTREZZO_DA_LAVORO", "SOFTWARE"
    );

    @FXML
    private ComboBox<ElementoCatalogoDTO> comboElementoEsistente;

    @FXML
    private TextField campoQuantitaImporta;

    @FXML
    private Label etichettaErroreImporta;

    @FXML
    private Button bottoneImporta;

    @FXML
    private TextField campoNomeNuovoElemento;

    @FXML
    private TextField campoDescrizioneNuovoElemento;

    @FXML
    private ComboBox<String> comboTipologiaNuovoElemento;

    @FXML
    private TextField campoQuantitaCrea;

    @FXML
    private Label etichettaErroreCrea;

    @FXML
    private Button bottoneCrea;

    private long idInventarioDestinazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboTipologiaNuovoElemento.setItems(FXCollections.observableArrayList(TIPOLOGIE_ELEMENTO));

        // TODO

    }

    /**
     * Chiamato da ContenutoInventarioController per sapere a quale inventario 
     * andrà aggiunto l'articolo
     */
    public void impostaInventarioDestinazione(long idInventario) {
        this.idInventarioDestinazione = idInventario;
    }

    @FXML
    private void onImportaEAggiungi() {

        // TODO

    }

    @FXML
    private void onCreaEAggiungi() {

        // TODO

    }

    private void mostraErrore(Label etichettaErrore, Throwable errore) {

        if (errore instanceof ApiException apiException) {
            etichettaErrore.setText(apiException.getMessage());
            return;
        }

        etichettaErrore.setText("Impossibile contattare il server: " + errore.getMessage());

    }

}

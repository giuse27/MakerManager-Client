package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboTipologiaNuovoElemento.setItems(FXCollections.observableArrayList(TIPOLOGIE_ELEMENTO));

    }

    @FXML
    private void onImportaEAggiungi() {

    }

    @FXML
    private void onCreaEAggiungi() {

    }

}

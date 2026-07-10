package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.CatalogoService;
import it.unipi.makermanagerclient.util.EsecutoreAsincrono;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller del popup "Aggiungi elemento al catalogo"
 */
public class AggiungiElementoCatalogoController implements Initializable {

    // Valori validi per "tipologia": gemelli poveri (solo stringhe)
    // dell'enum TipologiaElemento del Server.
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
    private Label etichettaErrore;

    @FXML
    private Button bottoneCrea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTipologia.setItems(FXCollections.observableArrayList(TIPOLOGIE_ELEMENTO));
    }

    @FXML
    private void onCrea() {

        String nome = campoNome.getText();
        String descrizione = campoDescrizione.getText();
        String tipologia = comboTipologia.getValue();

        if (nome == null || nome.isBlank() || tipologia == null) {
            etichettaErrore.setText("Nome e tipologia sono obbligatori.");
            return;
        }

        etichettaErrore.setText("");
        bottoneCrea.setDisable(true);

        EsecutoreAsincrono.esegui(

                () -> CatalogoService.crea(nome, descrizione, tipologia),
                creato -> ((Stage) bottoneCrea.getScene().getWindow()).close(),
                errore -> {
                    bottoneCrea.setDisable(false);
                    if (errore instanceof ApiException apiException) {
                        etichettaErrore.setText(apiException.getMessage());
                    } else {
                        etichettaErrore.setText("Impossibile contattare il server: " + errore.getMessage());
                    }
                }
        
            );

    }

}

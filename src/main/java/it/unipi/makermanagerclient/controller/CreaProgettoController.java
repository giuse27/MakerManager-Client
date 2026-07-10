package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.ProgettoService;
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
 * Controller del popup "Crea un nuovo progetto"
 */
public class CreaProgettoController implements Initializable {

    private static final List<String> TIPOLOGIE_PROGETTO = List.of(
            "STAMPA_3D", "ELETTRONICA", "ROBOTICA", "SOFTWARE"
    );

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoDescrizione;

    @FXML
    private Label etichettaErrore;

    @FXML
    private Button bottoneCrea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTipo.setItems(FXCollections.observableArrayList(TIPOLOGIE_PROGETTO));
    }

    @FXML
    private void onCrea() {

        // recupero i valori dai campi
        String tipo = comboTipo.getValue();
        String nome = campoNome.getText();
        String descrizione = campoDescrizione.getText();

        if (tipo == null || nome == null || nome.isBlank()) {
            etichettaErrore.setText("Tipo e nome sono obbligatori.");
            return;
        }

        // disattivo temporaneamente
        etichettaErrore.setText("");
        bottoneCrea.setDisable(true);

        EsecutoreAsincrono.esegui(

                // creo il progetto
                () -> ProgettoService.crea(tipo, nome, descrizione),
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

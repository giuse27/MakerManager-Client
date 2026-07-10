package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    }

}

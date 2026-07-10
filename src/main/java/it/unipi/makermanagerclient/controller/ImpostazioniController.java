package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller del pannello Impostazioni
 */
public class ImpostazioniController implements Initializable {

    private static final int SOGLIA_PREDEFINITA_SERVER = 3;

    @FXML
    private TextField campoSogliaMancanti;

    @FXML
    private Label etichettaEsito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onSalva() {

    }

    @FXML
    private void onRipristinaPredefinito() {

    }

}

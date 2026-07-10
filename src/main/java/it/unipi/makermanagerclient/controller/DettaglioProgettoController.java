package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.RigaBOMDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller della finestra di dettaglio di un progetto
 */
public class DettaglioProgettoController implements Initializable {

    @FXML
    private Label etichettaNome;

    @FXML
    private Label etichettaDescrizione;

    @FXML
    private Label etichettaAutore;

    @FXML
    private Button bottoneModificaBom;

    @FXML
    private Button bottoneAggiungiRiga;

    @FXML
    private Button bottoneEliminaRiga;

    @FXML
    private TableView<RigaBOMDTO> tabellaBom;

    @FXML
    private TableColumn<RigaBOMDTO, String> colonnaNomeElemento;

    @FXML
    private TableColumn<RigaBOMDTO, Integer> colonnaQuantita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onModificaBom() {

    }

    @FXML
    private void onAggiungiRiga() {

    }

    @FXML
    private void onEliminaRiga() {

    }

}

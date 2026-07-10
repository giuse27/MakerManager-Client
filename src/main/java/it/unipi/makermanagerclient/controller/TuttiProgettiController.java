package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ProgettoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller del pannello Tutti i progetti 
 */
public class TuttiProgettiController implements Initializable, PannelloRicaricabile {

    @FXML
    private Label etichettaTitolo;

    @FXML
    private TableView<ProgettoDTO> tabellaProgetti;

    @FXML
    private TableColumn<ProgettoDTO, String> colonnaNome;

    @FXML
    private TableColumn<ProgettoDTO, String> colonnaAutore;

    @FXML
    private TableColumn<ProgettoDTO, String> colonnaDescrizione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * ricarica il pannello
     */
    @Override
    public void ricarica() {

    }

}

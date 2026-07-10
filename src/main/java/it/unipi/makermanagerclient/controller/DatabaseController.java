package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller del pannello Database
 */
public class DatabaseController implements Initializable, PannelloRicaricabile {

    @FXML
    private Button bottoneInizializza;

    @FXML
    private Label etichettaTitoloTabella;

    @FXML
    private TableView<ElementoCatalogoDTO> tabellaElementi;

    @FXML
    private TableColumn<ElementoCatalogoDTO, String> colonnaNome;

    @FXML
    private TableColumn<ElementoCatalogoDTO, String> colonnaDescrizione;

    @FXML
    private TableColumn<ElementoCatalogoDTO, String> colonnaTipologia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * ricarica il pannello
     */
    @Override
    public void ricarica() {

    }

    @FXML
    private void onInizializza() {

    }

}

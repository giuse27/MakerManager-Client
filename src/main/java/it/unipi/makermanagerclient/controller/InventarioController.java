package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.InventarioDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Ricarica il pannello
     */
    @Override
    public void ricarica() {

    }

    @FXML
    private void onCreaInventario() {

    }

    @FXML
    private void onEliminaInventario() {

    }

}

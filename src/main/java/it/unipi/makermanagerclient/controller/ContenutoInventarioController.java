package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ArticoloInventarioDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller della finestra di dettaglio di un inventario
 */
public class ContenutoInventarioController implements Initializable {

    @FXML
    private Label etichettaTitoloInventario;

    @FXML
    private Label etichettaNumeroArticoli;

    @FXML
    private TableView<ArticoloInventarioDTO> tabellaArticoli;

    @FXML
    private TableColumn<ArticoloInventarioDTO, String> colonnaNomeArticolo;

    @FXML
    private TableColumn<ArticoloInventarioDTO, Integer> colonnaQuantita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onAggiungiArticolo() {

    }

    @FXML
    private void onEliminaArticolo() {

    }

}
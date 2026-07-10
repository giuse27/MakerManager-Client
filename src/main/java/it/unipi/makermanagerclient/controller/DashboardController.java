package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ProgettoConsigliatoDTO;
import it.unipi.makermanagerclient.sessione.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller del pannello Dashboard
 */
public class DashboardController implements Initializable, PannelloRicaricabile {

    @FXML
    private Label etichettaBenvenuto;

    @FXML
    private Label etichettaStatoServer;

    @FXML
    private Label etichettaTotaleProgetti;

    @FXML
    private Label etichettaMieiProgetti;

    @FXML
    private Label etichettaNumeroConsigliati;

    @FXML
    private TableView<ProgettoConsigliatoDTO> tabellaConsigliati;

    @FXML
    private TableColumn<ProgettoConsigliatoDTO, String> colonnaNome;

    @FXML
    private TableColumn<ProgettoConsigliatoDTO, String> colonnaAutore;

    @FXML
    private TableColumn<ProgettoConsigliatoDTO, String> colonnaFattibilita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        etichettaBenvenuto.setText("Benvenuto " + Sessione.getIstanza().getNickname() + " in MakerManager");

    }

    /**
     * Ricarica stato del pannello
     */
    @Override
    public void ricarica() {

    }

}

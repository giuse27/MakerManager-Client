package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.App;
import it.unipi.makermanagerclient.model.ProgettoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.ProgettoService;
import it.unipi.makermanagerclient.util.EsecutoreAsincrono;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    private final ObservableList<ProgettoDTO> progetti = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaAutore.setCellValueFactory(new PropertyValueFactory<>("nicknameAutore"));
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        tabellaProgetti.setItems(progetti);
        tabellaProgetti.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * ricarica il pannello
     */
    @Override
    public void ricarica() {

        EsecutoreAsincrono.esegui(

                ProgettoService::elencoTutti,
                lista -> {
                    progetti.setAll(lista);
                    etichettaTitolo.setText(
                            "Tutti i progetti disponibili in catalogo (" + lista.size() + ")"
                    );
                },
                errore -> mostraErrore("Impossibile caricare i progetti", errore)
        
            );

    }

    private TableRow<ProgettoDTO> caricaRiga() {

        TableRow<ProgettoDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {
            if (!riga.isEmpty()) {
                apriDettaglio(riga.getItem());
            }
        });

        return riga;

    }

    private void apriDettaglio(ProgettoDTO progetto) {

        try {

            // carico l'fxml per la visualizzazione in dettaglio
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/finestra-dettaglio-progetto.fxml")
            );
            Parent radice = loader.load();

            // da questo pannello la visualizzazione non permette la modifica
            // basta riaprire il proprio progetto da "i miei progetti"
            DettaglioProgettoController controller = loader.getController();
            controller.impostaProgetto(progetto.getId(), false);

            // visualizzo in una nuova finestra
            Stage finestra = new Stage();
            finestra.setTitle(progetto.getNome());
            Scene scena = new Scene(radice, 900, 600);
            scena.getStylesheets().add(App.getStylesheet());
            finestra.setScene(scena);
            finestra.show();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il dettaglio del progetto", e);
        }

    }

    private void mostraErrore(String titolo, Throwable errore) {

        String messaggio = (errore instanceof ApiException apiException)
                ? apiException.getMessage()
                : "Impossibile contattare il server: " + errore.getMessage();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();

    }

}

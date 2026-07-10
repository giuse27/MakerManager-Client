package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.App;
import it.unipi.makermanagerclient.model.ProgettoConsigliatoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.ProgettoService;
import it.unipi.makermanagerclient.sessione.Sessione;
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

    private final ObservableList<ProgettoConsigliatoDTO> consigliati = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        etichettaBenvenuto.setText("Benvenuto " + Sessione.getIstanza().getNickname() + " in MakerManager");

        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaAutore.setCellValueFactory(new PropertyValueFactory<>("nicknameAutore"));
        colonnaFattibilita.setCellValueFactory(new PropertyValueFactory<>("fattibilita"));

        tabellaConsigliati.setItems(consigliati);
        tabellaConsigliati.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * Ricarica stato del pannello
     */
    @Override
    public void ricarica() {

        // logica di refresh in tre fasi

        // prima chiamata per ottenere il conteggio dei progetti totali
        EsecutoreAsincrono.esegui(

                ProgettoService::elencoTutti,
                lista -> {
                    etichettaStatoServer.setText("Stato server: ONLINE");
                    etichettaTotaleProgetti.setText("Totale progetti: " + lista.size());
                },
                errore -> etichettaStatoServer.setText("Stato server: OFFLINE")
        
            );

        // seconda chiamata per ottenere il conteggio dei progetti personali
        long idUtente = Sessione.getIstanza().getIdUtente();

        EsecutoreAsincrono.esegui(

                () -> ProgettoService.elencoDiUtente(idUtente),
                lista -> etichettaMieiProgetti.setText("I miei progetti: " + lista.size()),
                errore -> mostraErrore("Impossibile caricare i tuoi progetti", errore)
        
            );

        // terza chiamata per mostrare i progetti consigliati nella dashboard
        EsecutoreAsincrono.esegui(

                () -> ProgettoService.consigliati(Sessione.getIstanza().getSogliaMancanti()),
                lista -> {
                    consigliati.setAll(lista);
                    etichettaNumeroConsigliati.setText("Progetti consigliati: " + lista.size());
                },
                errore -> mostraErrore("Impossibile caricare i progetti consigliati", errore)
        
            );

    }

    /** 
     * @return restituisce la riga caricata della tabella
     */
    private TableRow<ProgettoConsigliatoDTO> caricaRiga() {

        TableRow<ProgettoConsigliatoDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {
            if (!riga.isEmpty()) {
                apriDettaglio(riga.getItem());
            }
        });

        return riga;

    }

    /**
     * il client mostra un progetto a seguito del click
     */
    private void apriDettaglio(ProgettoConsigliatoDTO progetto) {

        try {

            // apro la finestra con i dettagli del progetto
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/finestra-dettaglio-progetto.fxml")
            );
            Parent radice = loader.load();

            // passo al controller di quella finestra l'id del progetto
            DettaglioProgettoController controller = loader.getController();
            // I progetti consigliati non sono mai dell'utente autenticato
            // (il Server li esclude gia'), quindi mai modificabili da qui.
            controller.impostaProgetto(progetto.getId(), false);

            // visualizzo il progetto in una nuova finestra
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

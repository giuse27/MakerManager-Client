package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ProgettoDTO;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller del pannello I miei progetti
 */
public class MieiProgettiController implements Initializable, PannelloRicaricabile {

    @FXML
    private Label etichettaTitolo;

    @FXML
    private TableView<ProgettoDTO> tabellaProgetti;

    @FXML
    private TableColumn<ProgettoDTO, String> colonnaNome;

    @FXML
    private TableColumn<ProgettoDTO, String> colonnaDescrizione;

    private final ObservableList<ProgettoDTO> progetti = FXCollections.observableArrayList();

    // true mentre e' attiva la modalita' "elimina"
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        tabellaProgetti.setItems(progetti);
        tabellaProgetti.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * ricarica il pannello
     */
    @Override
    public void ricarica() {

        long idUtente = Sessione.getIstanza().getIdUtente();

        EsecutoreAsincrono.esegui(

                () -> ProgettoService.elencoDiUtente(idUtente),
                lista -> {
                    progetti.setAll(lista);
                    etichettaTitolo.setText(
                            "Ciao " + Sessione.getIstanza().getNickname()
                            + " qui ci sono i progetti che hai creato (" + lista.size() + ")"
                    );
                },
                errore -> mostraErrore("Impossibile caricare i tuoi progetti", errore)
        
            );

    }

    private TableRow<ProgettoDTO> caricaRiga() {

        TableRow<ProgettoDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty()) {
                return;
            }

            if (modalitaEliminazione) {
                chiediConfermaEdElimina(riga.getItem());
            } else {
                apriDettaglio(riga.getItem());
            }

        });

        return riga;

    }

    @FXML
    private void onCreaProgetto() {

        try {

            // carico l'interfaccia di creazione del nuovo progetto
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/popup-crea-progetto.fxml")
            );
            Parent radice = loader.load();

            // la apro in una nuova finestra
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Nuovo progetto");
            popup.setScene(new Scene(radice, 420, 320));
            popup.setOnHidden(evento -> ricarica());
            popup.showAndWait();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il popup di creazione progetto", e);
        }

    }

    @FXML
    private void onEliminaProgetto() {

        modalitaEliminazione = true;

    }

    private void chiediConfermaEdElimina(ProgettoDTO progetto) {

        modalitaEliminazione = false;

        // chiedi conferma con un alert
        Alert conferma = new Alert(AlertType.CONFIRMATION);
        conferma.setTitle("Elimina progetto");
        conferma.setHeaderText(null);
        conferma.setContentText(
                "Sei sicuro di voler eliminare il progetto \"" + progetto.getNome() + "\" e tutta la sua B.O.M.?"
        );

        // se l'utente conferma procedo con l'eliminazione
        conferma.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(bottone -> EsecutoreAsincrono.<Void>esegui(

                        () -> {
                            ProgettoService.elimina(progetto.getId());
                            return null;
                        },
                        esito -> ricarica(),
                        errore -> mostraErrore("Impossibile eliminare il progetto", errore)
                
                    )
                );

    }

    private void apriDettaglio(ProgettoDTO progetto) {

        try {

            // carico l'fxml per la visualizzazione in dettaglio
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/finestra-dettaglio-progetto.fxml")
            );
            Parent radice = loader.load();

            // passo al controller del dettaglio l'id e dico che è modificabile
            // in quanto il progetto è mio
            DettaglioProgettoController controller = loader.getController();
            controller.impostaProgetto(progetto.getId(), true);

            // visualizzo il dettaglio in una nuova finestra
            Stage finestra = new Stage();
            finestra.setTitle(progetto.getNome());
            finestra.setScene(new Scene(radice, 900, 600));
            finestra.setOnHidden(evento -> ricarica());
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

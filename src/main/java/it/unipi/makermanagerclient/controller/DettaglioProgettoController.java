package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.RigaBOMDTO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

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

    private final ObservableList<RigaBOMDTO> righeBom = FXCollections.observableArrayList();

    private long idProgetto;

    // true mentre la B.O.M. e' in modalita' modifica (dopo la pressione
    // di "Modifica la BOM"): rivela Aggiungi/Elimina riga e rende la
    // quantita' editabile inline
    private boolean modalitaModifica = false;

    // true mentre e' attiva la modalita' "elimina riga"
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNomeElemento.setCellValueFactory(new PropertyValueFactory<>("elementoCatalogo"));

        colonnaQuantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        // Il cellFactory va impostato una volta sola per bloccare l'inserimento
        // non devo utilizzare setCellFactory(null) ma setEditable(false)
        colonnaQuantita.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colonnaQuantita.setOnEditCommit(this::onQuantitaModificata);

        tabellaBom.setItems(righeBom);
        tabellaBom.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * Chiamato dal chiamante subito dopo il caricamento del FXML: quale
     * progetto mostrare e se offrire la modifica della B.O.M. non sono
     * parametri del file FXML.
     */
    public void impostaProgetto(long idProgetto, boolean modificabile) {

        this.idProgetto = idProgetto;

        bottoneModificaBom.setVisible(modificabile);
        bottoneModificaBom.setManaged(modificabile);

        ricarica();

    }

    private void ricarica() {

        EsecutoreAsincrono.esegui(

                () -> ProgettoService.dettaglio(idProgetto),
                progetto -> {
                    etichettaNome.setText(progetto.getNome());
                    etichettaDescrizione.setText(progetto.getDescrizione());
                    etichettaAutore.setText("Autore: " + progetto.getNicknameAutore());
                    righeBom.setAll(progetto.getBom());
                },
                errore -> mostraErrore("Impossibile caricare il progetto", errore)
        
            );

    }

    @FXML
    private void onModificaBom() {

        // inverti la modalità di modifica
        modalitaModifica = !modalitaModifica;

        // inverti di conseguenza il tasto
        bottoneModificaBom.setText(modalitaModifica ? "Fine modifica" : "Modifica la BOM");

        bottoneAggiungiRiga.setVisible(modalitaModifica);
        bottoneAggiungiRiga.setManaged(modalitaModifica);
        bottoneEliminaRiga.setVisible(modalitaModifica);
        bottoneEliminaRiga.setManaged(modalitaModifica);

        // qui la nota iniziale
        tabellaBom.setEditable(modalitaModifica);

        if (!modalitaModifica) {
            modalitaEliminazione = false;
        }

    }

    /**
     * quando l'utente aggiorna la quantità durante la modifica
     */
    private void onQuantitaModificata(TableColumn.CellEditEvent<RigaBOMDTO, Integer> evento) {

        // recupero i valori nuovi
        RigaBOMDTO riga = evento.getRowValue();
        Integer nuovoValore = evento.getNewValue();

        if (nuovoValore == null || nuovoValore < 1) {
            tabellaBom.refresh();
            return;
        }

        // eseguo la patch
        EsecutoreAsincrono.esegui(

                () -> ProgettoService.aggiornaQuantitaRigaBom(idProgetto, riga.getId(), nuovoValore),
                aggiornata -> ricarica(),
                errore -> {
                    mostraErrore("Impossibile aggiornare la quantità", errore);
                    tabellaBom.refresh();
                }
        
            );

    }

    private TableRow<RigaBOMDTO> caricaRiga() {

        TableRow<RigaBOMDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty() || !modalitaEliminazione) {
                return;
            }

            // eliminazione della riga bom
            chiediConfermaEdElimina(riga.getItem());

        });

        return riga;

    }

    @FXML
    private void onAggiungiRiga() {

        try {

            // carica l'fxml per aggiungere una riga bom
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/popup-aggiungi-riga-bom.fxml")
            );
            Parent radice = loader.load();

            // passa al controller l'id del progetto di riferimento
            AggiungiRigaBomController controller = loader.getController();
            controller.impostaProgettoDestinazione(idProgetto);

            // mostra tutto in una finestra popup
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Aggiungi riga alla B.O.M.");
            popup.setScene(new Scene(radice, 480, 420));
            popup.setOnHidden(evento -> ricarica());
            popup.showAndWait();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il popup di aggiunta riga BOM", e);
        }

    }

    @FXML
    private void onEliminaRiga() {
        modalitaEliminazione = true;
    }

    private void chiediConfermaEdElimina(RigaBOMDTO riga) {

        modalitaEliminazione = false;

        // chiede conferma all'utente con un alert
        Alert conferma = new Alert(AlertType.CONFIRMATION);
        conferma.setTitle("Elimina riga");
        conferma.setHeaderText(null);
        conferma.setContentText("Eliminare \"" + riga.getElementoCatalogo() + "\" dalla B.O.M.?");

        // se l'utente ha dato conferma procedo con l'eliminazione
        conferma.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(bottone -> EsecutoreAsincrono.<Void>esegui(

                        () -> {
                            ProgettoService.eliminaRigaBom(idProgetto, riga.getId());
                            return null;
                        },
                        esito -> ricarica(),
                        errore -> mostraErrore("Impossibile eliminare la riga", errore)
                
                    )
                );

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

package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.App;
import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.AmministrazioneService;
import it.unipi.makermanagerclient.service.CatalogoService;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller del pannello Database
 */
public class DatabaseController implements Initializable, PannelloRicaricabile {

    @FXML
    private Button bottoneInizializza;

    @FXML
    private Button bottoneAggiungiElemento;

    @FXML
    private Button bottoneEliminaElemento;

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

    private final ObservableList<ElementoCatalogoDTO> elementi = FXCollections.observableArrayList();

    // true mentre e' attiva la modalità elimina
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // dico alle colonne come si chiamano i campi del dto da cui estrarre
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colonnaTipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

        tabellaElementi.setItems(elementi);
        tabellaElementi.setRowFactory(tabella -> caricaRiga());

        // "Inizializza" e elimina e' un'operazione distruttiva riservata ad ADMIN
        // (vedi endpoint.md): nascosta del tutto per gli altri utenti,
        // non solo disabilitata.
        boolean admin = Sessione.getIstanza().isAdmin();
        bottoneInizializza.setVisible(admin);
        bottoneInizializza.setManaged(admin);
        bottoneEliminaElemento.setVisible(admin);
        bottoneEliminaElemento.setManaged(admin);

    }

    /**
     * ricarica il pannello
     */
    @Override
    public void ricarica() {

        EsecutoreAsincrono.esegui(

                // richiedo l'elenco di elementi
                CatalogoService::elenco,
                lista -> {
                    elementi.setAll(lista);
                    etichettaTitoloTabella.setText(
                            "Elenco completo degli elementi presenti in catalogo (" + lista.size() + ")"
                    );
                },
                errore -> mostraErrore("Impossibile caricare il catalogo", errore)

        );

    }

    /**
     * comportamento alla pressione dell'admin di inizializza
     */
    @FXML
    private void onInizializza() {

        // avviso l'admin prima di cancellare tutto
        Alert avviso = new Alert(AlertType.WARNING);
        avviso.setTitle("Inizializza database");
        avviso.setHeaderText("Operazione distruttiva");
        avviso.setContentText(
                "Questa operazione cancella tutto il contenuto del database (tranne l'ADMIN di default) "
                + "e lo ripopola dal file di inizializzazione. Continuare?"
        );
        avviso.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.OK);

        avviso.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(bottone -> {

                    bottoneInizializza.setDisable(true);

                    EsecutoreAsincrono.<Void>esegui(

                            () -> {
                                AmministrazioneService.inizializzaDatabase();
                                return null;
                            },
                            esito -> {
                                bottoneInizializza.setDisable(false);
                                ricarica();
                            },
                            errore -> {
                                bottoneInizializza.setDisable(false);
                                mostraErrore("Impossibile inizializzare il database", errore);
                            }

                    );

                });

    }

    private TableRow<ElementoCatalogoDTO> caricaRiga() {

        TableRow<ElementoCatalogoDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty() || !modalitaEliminazione) {
                return;
            }

            chiediConfermaEdElimina(riga.getItem());

        });

        return riga;

    }

    @FXML
    private void onAggiungiElemento() {

        try {

            // carico l'interfaccia per aggiungere un nuovo elemento
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/popup-aggiungi-elemento-catalogo.fxml")
            );
            Parent radice = loader.load();

            // visualizzo in una nuova finestra
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Nuovo elemento catalogo");
            Scene scena = new Scene(radice, 420, 340);
            scena.getStylesheets().add(App.getStylesheet());
            popup.setScene(scena);
            popup.setOnHidden(evento -> ricarica());
            popup.showAndWait();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il popup di aggiunta elemento", e);
        }

    }

    @FXML
    private void onEliminaElemento() {
        modalitaEliminazione = true;
    }

    private void chiediConfermaEdElimina(ElementoCatalogoDTO elemento) {

        modalitaEliminazione = false;

        // chiedo conferma all'admin prima di eliminare
        Alert conferma = new Alert(AlertType.CONFIRMATION);
        conferma.setTitle("Elimina elemento");
        conferma.setHeaderText(null);
        conferma.setContentText("Eliminare \"" + elemento.getNome() + "\" dal catalogo?");

        // se l'admin conferma procedo
        conferma.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(bottone -> EsecutoreAsincrono.<Void>esegui(

                        () -> {
                            CatalogoService.elimina(elemento.getId());
                            return null;
                        },
                        esito -> ricarica(),
                        errore -> mostraErroreEliminazione(errore)
                
                    )
                );

    }

    /**
     * Come mostraErrore(), ma gestisce il caso speciale di foreign key vincolate
     * a seguito dell'eliminazione. per queste il server non prevede ancora una
     * gestione corretta e lancia un 500 generico
     */
    private void mostraErroreEliminazione(Throwable errore) {

        String messaggio;

        if (errore instanceof ApiException apiException && apiException.getStatusCode() == 500) {
            messaggio = "Elemento vincolato da foreign key sul database: è ancora posseduto in un "
                    + "inventario o richiesto nella B.O.M. di un progetto, quindi non può essere eliminato.";
        } else if (errore instanceof ApiException apiException) {
            messaggio = apiException.getMessage();
        } else {
            messaggio = "Impossibile contattare il server: " + errore.getMessage();
        }

        // mando l'alert
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Impossibile eliminare l'elemento");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();

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

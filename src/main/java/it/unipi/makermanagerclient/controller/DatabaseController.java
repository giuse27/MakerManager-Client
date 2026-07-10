package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.AmministrazioneService;
import it.unipi.makermanagerclient.service.CatalogoService;
import it.unipi.makermanagerclient.sessione.Sessione;
import it.unipi.makermanagerclient.util.EsecutoreAsincrono;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private final ObservableList<ElementoCatalogoDTO> elementi = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // dico alle colonne come si chiamano i campi del dto da cui estrarre
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colonnaTipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

        tabellaElementi.setItems(elementi);

        // "Inizializza" e' un'operazione distruttiva riservata ad ADMIN
        // (vedi endpoint.md): nascosta del tutto per gli altri utenti,
        // non solo disabilitata.
        boolean admin = Sessione.getIstanza().isAdmin();
        bottoneInizializza.setVisible(admin);
        bottoneInizializza.setManaged(admin);

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

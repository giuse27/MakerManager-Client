package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.InventarioDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.InventarioService;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    private final ObservableList<InventarioDTO> inventari = FXCollections.observableArrayList();

    // attiva o disattiva l'eliminazione di un'inventario
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNomeInventario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaNumeroArticoli.setCellValueFactory(new PropertyValueFactory<>("numeroArticoli"));

        tabellaInventari.setItems(inventari);
        tabellaInventari.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * Ricarica il pannello
     */
    @Override
    public void ricarica() {

        EsecutoreAsincrono.esegui(
            InventarioService::elencoInventariConConteggio,

            lista -> {
                inventari.setAll(lista);
                etichettaNumeroInventari.setText("Numero di inventari: " + lista.size());
            },

            errore -> mostraErrore("Impossibile caricare gli inventari", errore)
        );

    }

    /**
     * modo in cui il client renderizza (carica) il contenuto di una riga
     * e specifica il comportamento della riga quando qualcuno vi fa click sopra
     * 
     * @return restituisce la riga della tabella
     */
    private TableRow<InventarioDTO> caricaRiga() {

        TableRow<InventarioDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty()) {
                return;
            }

            if (modalitaEliminazione) {
                chiediConfermaEdElimina(riga.getItem());
            } else {
                apriContenutoInventario(riga.getItem());
            }

        });

        return riga;

    }

    /**
     * comportamento del client alla pressione del tasto crea inventario
     * 
     * - mostra un popup per inserire il nome
     * - se l'inserimento ha senso procede con la creazione
     */
    @FXML
    private void onCreaInventario() {

        // popup per chiedere il nome dell'inventario da creare
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuovo inventario");
        dialog.setHeaderText(null);
        dialog.setContentText("Nome del nuovo inventario:");

        // aspetto che l'utente inserisca il nome
        Optional<String> nome = dialog.showAndWait();

        // verifico che il nome non sia vuoto o di soli spazi
        nome.filter(testo -> !testo.isBlank())
                // se ha senso eseguo la chiamata
                .ifPresent(testo ->
                    EsecutoreAsincrono.esegui(

                        () -> InventarioService.creaInventario(testo),
                        creato -> ricarica(),
                        errore -> mostraErrore("Impossibile creare l'inventario", errore)

                    )
                );

    }

    /**
     * comportamento alla pressione di elimina
     * 
     * - attiva la modalità di eliminazione
     */
    @FXML
    private void onEliminaInventario() {
        
        modalitaEliminazione = true;

    }

    /**
     * visualizza il contenuto di un inventario in una nuova finestra
     * 
     * @param item inventario selezionato
     */
    private void apriContenutoInventario(InventarioDTO inventario) {

        try {

            // apro la finestra per visualizzare il contenuto dell'inventario
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/finestra-contenuto-inventario.fxml")
            );
            Parent radice = loader.load();

            // recupero il controller del contenuto inventario
            ContenutoInventarioController controller = loader.getController();

            // passo le informazioni su quale inventario al controller
            controller.impostaInventario(inventario.getId(), inventario.getNome());

            // visualizzo il contenuto in una nuova finestra
            Stage finestra = new Stage();
            finestra.setTitle(inventario.getNome());
            finestra.setScene(new Scene(radice, 900, 600));
            finestra.setOnHidden(evento -> ricarica());
            finestra.show();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il contenuto dell'inventario", e);
        }

    }

    /**
     * chiedi conferma con un pop up ed elimina un inventario
     * 
     * @param item inventario selezionato
     */
    private void chiediConfermaEdElimina(InventarioDTO inventario) {

        // la mod eliminazione vale solo per una riga
        modalitaEliminazione = false;

        // chiedo conferma all'utente
        Alert conferma = new Alert(AlertType.CONFIRMATION);
        conferma.setTitle("Elimina inventario");
        conferma.setHeaderText(null);
        conferma.setContentText(
                "Eliminare l'inventario \"" + inventario.getNome() + "\" e tutti i suoi articoli?"
        );

        // se l'utente ha premuto ok (filter + ifPresent) elimino l'inventario
        conferma.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(
                    bottone -> EsecutoreAsincrono.<Void>esegui(

                        () -> {
                            InventarioService.eliminaInventario(inventario.getId());
                            return null;
                        },
                        esito -> ricarica(),
                        errore -> mostraErrore("Impossibile eliminare l'inventario", errore)
                    
                    )
                );

    }

    /**
     * utility per mostrare errori in un alert 
     * 
     * @param titolo titolo del popup
     * @param errore errore da mostrare
     */
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

package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ArticoloInventarioDTO;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

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

    private final ObservableList<ArticoloInventarioDTO> articoli = FXCollections.observableArrayList();

    private long idInventario;
    private String nomeInventario;

    // true mentre e' attiva la modalita' "elimina": il prossimo click su
    // una riga chiede conferma invece di essere ignorato
    private boolean modalitaEliminazione = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colonnaNomeArticolo.setCellValueFactory(new PropertyValueFactory<>("elementoCatalogo"));

        colonnaQuantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colonnaQuantita.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colonnaQuantita.setOnEditCommit(this::onQuantitaModificata);

        tabellaArticoli.setItems(articoli);
        tabellaArticoli.setRowFactory(tabella -> caricaRiga());

    }

    /**
     * Chiamato da InventarioController subito dopo il caricamento del
     * FXML, per sapere quale inventario mostrare
     */
    public void impostaInventario(long idInventario, String nomeInventario) {
        this.idInventario = idInventario;
        this.nomeInventario = nomeInventario;
        etichettaTitoloInventario.setText(nomeInventario);
        ricarica();
    }

    /**
     * carica una riga nella tabella e gestisce il click su di essa
     * 
     * @return riga
     */
    private TableRow<ArticoloInventarioDTO> caricaRiga() {

        TableRow<ArticoloInventarioDTO> riga = new TableRow<>();

        riga.setOnMouseClicked(evento -> {

            if (riga.isEmpty() || !modalitaEliminazione) {
                return;
            }

            chiediConfermaEdElimina(riga.getItem());

        });

        return riga;

    }

    /**
     * ricarica la tabella aggiornando il contenuto dell'inventario
     */
    private void ricarica() {

        EsecutoreAsincrono.esegui(
                () -> InventarioService.contenutoInventario(idInventario),
                lista -> {
                    articoli.setAll(lista);
                    etichettaNumeroArticoli.setText("Numero articoli: " + lista.size());
                },
                errore -> mostraErrore("Impossibile caricare il contenuto dell'inventario", errore)
        );

    }

    /**
     * comportamento client quando l'utente vuole aggiungere un articolo
     */
    @FXML
    private void onAggiungiArticolo() {

        try {

            // carico "popup-aggiungi-articolo.fxml"
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unipi/makermanagerclient/popup-aggiungi-articolo.fxml")
            );
            Parent radice = loader.load();

            // passo l'inventario di destinazione al controller
            AggiungiArticoloController controller = loader.getController();
            controller.impostaInventarioDestinazione(idInventario);

            // apro una nuova finestra per aggiungere un articolo
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Aggiungi articolo a \"" + nomeInventario + "\"");
            popup.setScene(new Scene(radice, 480, 420));
            popup.setOnHidden(evento -> ricarica());
            popup.showAndWait();

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile aprire il popup di aggiunta articolo", e);
        }

    }

    /**
     * comportamento client quando l'utente vuole eliminare un articolo
     */
    @FXML
    private void onEliminaArticolo() {

        modalitaEliminazione = true;

    }

    /**
     * Invocato da JavaFX quando l'utente conferma la modifica della quantità
     */
    private void onQuantitaModificata(TableColumn.CellEditEvent<ArticoloInventarioDTO, Integer> evento) {

        ArticoloInventarioDTO articolo = evento.getRowValue();
        Integer nuovoValore = evento.getNewValue();

        if (nuovoValore == null || nuovoValore < 0) {
            tabellaArticoli.refresh();
            return;
        }

        EsecutoreAsincrono.esegui(

                () -> InventarioService.aggiornaQuantita(articolo.getId(), nuovoValore),
                aggiornato -> ricarica(),
                errore -> {
                    mostraErrore("Impossibile aggiornare la quantità", errore);
                    tabellaArticoli.refresh();
                }

        );

    }

    /**
     * chiedi conferma prima di eliminare un articolo
     * 
     * @param articolo articolo selezionato
     */
    private void chiediConfermaEdElimina(ArticoloInventarioDTO articolo) {

        // vale solo per un articolo quindi la disattivo
        modalitaEliminazione = false;

        // mando l'alert di conferma
        Alert conferma = new Alert(AlertType.CONFIRMATION);
        conferma.setTitle("Elimina articolo");
        conferma.setHeaderText(null);
        conferma.setContentText("Eliminare \"" + articolo.getElementoCatalogo() + "\" dall'inventario?");

        // stessa logica di InventarioController
        conferma.showAndWait()
                .filter(bottone -> bottone == ButtonType.OK)
                .ifPresent(
                    bottone -> EsecutoreAsincrono.<Void>esegui(

                        () -> {
                            InventarioService.eliminaArticolo(articolo.getId());
                            return null;
                        },
                        esito -> ricarica(),
                        errore -> mostraErrore("Impossibile eliminare l'articolo", errore)
                    
                    )
                );

    }

    /**
     * utility per errori tramite alert come in InventarioController
     * 
     * @param titolo titolo popup
     * @param errore errore
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
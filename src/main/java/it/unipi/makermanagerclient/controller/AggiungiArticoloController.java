package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.CatalogoService;
import it.unipi.makermanagerclient.service.InventarioService;
import it.unipi.makermanagerclient.util.EsecutoreAsincrono;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller del popup "Aggiungi articolo"
 */
public class AggiungiArticoloController implements Initializable {

    // Valori validi per "tipologia"
    private static final List<String> TIPOLOGIE_ELEMENTO = List.of(
            "COMPONENTE_ELETTRONICO", "MATERIALE_CONSUMABILE", "ATTREZZO_DA_LAVORO", "SOFTWARE"
    );

    @FXML
    private ComboBox<ElementoCatalogoDTO> comboElementoEsistente;

    @FXML
    private TextField campoQuantitaImporta;

    @FXML
    private Label etichettaErroreImporta;

    @FXML
    private Button bottoneImporta;

    @FXML
    private TextField campoNomeNuovoElemento;

    @FXML
    private TextField campoDescrizioneNuovoElemento;

    @FXML
    private ComboBox<String> comboTipologiaNuovoElemento;

    @FXML
    private TextField campoQuantitaCrea;

    @FXML
    private Label etichettaErroreCrea;

    @FXML
    private Button bottoneCrea;

    private long idInventarioDestinazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboTipologiaNuovoElemento.setItems(FXCollections.observableArrayList(TIPOLOGIE_ELEMENTO));

        EsecutoreAsincrono.esegui(

                CatalogoService::elenco,
                lista -> comboElementoEsistente.setItems(FXCollections.observableArrayList(lista)),
                errore -> mostraErrore(etichettaErroreImporta, errore)

        );

    }

    /**
     * Chiamato da ContenutoInventarioController per sapere a quale inventario 
     * andrà aggiunto l'articolo
     */
    public void impostaInventarioDestinazione(long idInventario) {
        this.idInventarioDestinazione = idInventario;
    }

    /**
     * comportamento del client quando si aggiunge un elemento esistente
     */
    @FXML
    private void onImportaEAggiungi() {

        // recupero l'elemento catalogo selezionato
        ElementoCatalogoDTO elemento = comboElementoEsistente.getValue();

        // lettura sicura della quantità
        Integer quantita = leggiQuantita(campoQuantitaImporta, etichettaErroreImporta);

        if (elemento == null) {
            etichettaErroreImporta.setText("Seleziona un elemento dal catalogo.");
            return;
        }
        if (quantita == null) {
            return;
        }

        // disattivo temporaneamente il tasto, per evitare click multipli
        etichettaErroreImporta.setText("");
        bottoneImporta.setDisable(true);

        // provo a inserire l'articolo
        EsecutoreAsincrono.esegui(

            () -> InventarioService.aggiungiArticolo(
                                        elemento.getId(), 
                                        idInventarioDestinazione, 
                                        quantita
                                    ),
            creato -> chiudiFinestra(),
            errore -> {
                bottoneImporta.setDisable(false);
                mostraErrore(etichettaErroreImporta, errore);
            }

        );

    }

    /**
     * comportamento client quando si crea un nuovo elemento e si aggiunge
     */
    @FXML
    private void onCreaEAggiungi() {

        // recupero le informazioni sull'elemento da creare
        String nome = campoNomeNuovoElemento.getText();
        String descrizione = campoDescrizioneNuovoElemento.getText();
        String tipologia = comboTipologiaNuovoElemento.getValue();
        Integer quantita = leggiQuantita(campoQuantitaCrea, etichettaErroreCrea);

        if (nome == null || nome.isBlank() || tipologia == null) {
            etichettaErroreCrea.setText("Nome e tipologia sono obbligatori.");
            return;
        }
        if (quantita == null) {
            return;
        }

        // disattivo temporaneamente il tasto, per evitare click multipli
        etichettaErroreCrea.setText("");
        bottoneCrea.setDisable(true);


        EsecutoreAsincrono.esegui(

            () -> {
                // creo il nuovo elemento in catalogo
                ElementoCatalogoDTO nuovoElemento = CatalogoService.crea(nome, descrizione, tipologia);
                
                // lo aggiungo all'inventario
                return InventarioService.aggiungiArticolo(
                        nuovoElemento.getId(), idInventarioDestinazione, quantita
                );
            },
            creato -> chiudiFinestra(),
            errore -> {
                bottoneCrea.setDisable(false);
                mostraErrore(etichettaErroreCrea, errore);
            }

        );

    }

    /**
     * lettura sicura di un intero
     * 
     * @param campo da dove leggere
     * @param etichettaErrore label errore per eventuali problemi
     * @return intero sicuro
     */
    private Integer leggiQuantita(TextField campo, Label etichettaErrore) {

        try {
            
            // recupero il valore intero dal campo di testo
            int valore = Integer.parseInt(campo.getText().trim());

            if (valore <= 0) {
                etichettaErrore.setText("La quantità deve essere maggiore di zero.");
                return null;
            }

            return valore;

        } catch (NumberFormatException e) {
            etichettaErrore.setText("Inserisci una quantità valida.");
            return null;
        }

    }

    /**
     * chiude la finestra
     */
    private void chiudiFinestra() {
        ((Stage) comboElementoEsistente.getScene().getWindow()).close();
    }

    private void mostraErrore(Label etichettaErrore, Throwable errore) {

        if (errore instanceof ApiException apiException) {
            etichettaErrore.setText(apiException.getMessage());
            return;
        }

        etichettaErrore.setText("Impossibile contattare il server: " + errore.getMessage());

    }

}

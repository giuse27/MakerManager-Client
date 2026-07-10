package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.model.ElementoCatalogoDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.service.CatalogoService;
import it.unipi.makermanagerclient.service.ProgettoService;
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
 * Controller del popup "Aggiungi riga alla B.O.M."
 */
public class AggiungiRigaBomController implements Initializable {

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

    private long idProgettoDestinazione;

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
     * Chiamato da DettaglioProgettoController subito dopo
     * il caricamento del FXML, per sapere a quale progetto andra'
     * aggiunta la riga
     */
    public void impostaProgettoDestinazione(long idProgetto) {
        this.idProgettoDestinazione = idProgetto;
    }

    /**
     * aggiunta di una riga bom da elemento esistente
     */
    @FXML
    private void onImportaEAggiungi() {

        // seleziono elemento e inserisco la quantità
        ElementoCatalogoDTO elemento = comboElementoEsistente.getValue();
        Integer quantita = leggiQuantita(campoQuantitaImporta, etichettaErroreImporta);

        if (elemento == null) {
            etichettaErroreImporta.setText("Seleziona un elemento dal catalogo.");
            return;
        }
        if (quantita == null) {
            return;
        }

        etichettaErroreImporta.setText("");
        bottoneImporta.setDisable(true);

        // aggiungo la nuova riga
        EsecutoreAsincrono.esegui(

                () -> ProgettoService.aggiungiRigaBom(idProgettoDestinazione, elemento.getId(), quantita),
                creata -> chiudiFinestra(),
                errore -> {
                    bottoneImporta.setDisable(false);
                    mostraErrore(etichettaErroreImporta, errore);
                }
        
            );

    }

    /**
     * aggiunta di riga bom da elemento da creare
     */
    @FXML
    private void onCreaEAggiungi() {

        // recupero i valori inseriti
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

        etichettaErroreCrea.setText("");
        bottoneCrea.setDisable(true);

        // creo prima l'elemento e poi aggiungo la riga
        EsecutoreAsincrono.esegui(

                () -> {
                    ElementoCatalogoDTO nuovoElemento = CatalogoService.crea(nome, descrizione, tipologia);
                    return ProgettoService.aggiungiRigaBom(
                            idProgettoDestinazione, nuovoElemento.getId(), quantita
                    );
                },
                creata -> chiudiFinestra(),
                errore -> {
                    bottoneCrea.setDisable(false);
                    mostraErrore(etichettaErroreCrea, errore);
                }
        
            );

    }

    /**
     * utility per la conversione sicura da testo a intero
     */
    private Integer leggiQuantita(TextField campo, Label etichettaErrore) {

        try {

            // tento la conversione
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

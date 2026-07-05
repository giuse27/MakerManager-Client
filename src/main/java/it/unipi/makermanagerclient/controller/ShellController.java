package it.unipi.makermanagerclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.StackPane;

/**
 * Controller della Shell principale.
 *
 * Carica una sola volta i 4 pannelli (Inizializzazione, Catalogo, Inventario, 
 * Progetti), e li collega ai campi definiti nella sidebar
 * Questo preserva lo stato di ciascun pannello (in particolare l'ultimo output 
 * mostrato) quando si cambia sezione.
 *
 * Per questo i 4 pannelli vengono caricati una volta sola in initialize() 
 * e restano sempre vivi in memoria, cambiare sezione significa solo alternare 
 * quale sia visibile.
 */
public class ShellController implements Initializable {

    @FXML
    private StackPane areaContenuti;

    // spinner per la selezione di idInventario
    @FXML
    private Spinner<Integer> spinnerIdInventario;

    // spinner per la selezione di idUtente
    @FXML
    private Spinner<Integer> spinnerIdUtente;

    // spinner per la selezione di idProgetto
    @FXML
    private Spinner<Integer> spinnerIdProgetto;

    // Riferimenti ai 4 pannelli precaricati (per poterle mostrare/nascondere).
    private Parent pannelloInizializzazione;
    private Parent pannelloCatalogo;
    private Parent pannelloInventario;
    private Parent pannelloProgetti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configuraSpinnerCondivisi();
        caricaTuttiIPannelli();

        // All'avvio mostriamo il pannello Inizializzazione
        mostraSoltanto(pannelloInizializzazione);

    }

    private void configuraSpinnerCondivisi() {

        spinnerIdInventario.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        spinnerIdUtente.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        spinnerIdProgetto.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );

    }

    /**
     * Carica i 4 file FXML dei pannelli una sola volta, li aggiunge tutti
     * a areaContenuti (sovrapposti, dato che e' uno StackPane), e collega
     * gli Spinner condivisi ai controller che ne hanno bisogno.
     */
    private void caricaTuttiIPannelli() {

        pannelloInizializzazione = carica("pannello-inizializzazione.fxml");
        pannelloCatalogo = carica("pannello-catalogo.fxml");

        FXMLLoader loaderInventario = new FXMLLoader(
                getClass().getResource("/it/unipi/makermanagerclient/pannello-inventario.fxml")
        );
        pannelloInventario = carica(loaderInventario);
        InventarioController controllerInventario = loaderInventario.getController();
        controllerInventario.impostaSpinnerCondivisi(spinnerIdInventario, spinnerIdUtente);

        FXMLLoader loaderProgetti = new FXMLLoader(
                getClass().getResource("/it/unipi/makermanagerclient/pannello-progetti.fxml")
        );
        pannelloProgetti = carica(loaderProgetti);
        ProgettoController controllerProgetti = loaderProgetti.getController();
        controllerProgetti.impostaSpinnerCondiviso(spinnerIdProgetto);

        areaContenuti.getChildren().addAll(
                pannelloInizializzazione, pannelloCatalogo
        );

    }

    // Metodo di comodo per i pannelli che non necessitano di accesso al
    // loro controller dopo il caricamento (Inizializzazione, Catalogo).
    private Parent carica(String nomeFile) {
        return carica(new FXMLLoader(getClass().getResource("/it/unipi/makermanagerclient/" + nomeFile)));
    }

    // Variante che accetta un FXMLLoader gia' costruito dal chiamante,
    // usata quando serve poi recuperare anche il controller associato
    // (Inventario, Progetti) tramite loader.getController().
    private Parent carica(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile caricare il pannello: " + loader.getLocation(), e);
        }
    }

    /**
     * Mostra esclusivamente il pannello indicato, nascondendo gli altri
     * tre. Usiamo sia setVisible che setManaged: setVisible nasconde
     * graficamente il nodo, setManaged evita che continui comunque a
     * occupare spazio nel layout (altrimenti i pannelli nascosti
     * lascerebbero "buchi" invisibili nello StackPane).
     */
    private void mostraSoltanto(Parent pannelloDaMostrare) {

        for (var pannello : areaContenuti.getChildren()) {
            boolean pannelloGiusto = (pannello == pannelloDaMostrare);
            pannello.setVisible(pannelloGiusto);
            pannello.setManaged(pannelloGiusto);
        }

    }

    @FXML
    private void mostraInizializzazione() {
        mostraSoltanto(pannelloInizializzazione);
    }

    @FXML
    private void mostraCatalogo() {
        mostraSoltanto(pannelloCatalogo);
    }

    @FXML
    private void mostraInventario() {
        mostraSoltanto(pannelloInventario);
    }

    @FXML
    private void mostraProgetti() {
        mostraSoltanto(pannelloProgetti);
    }

}
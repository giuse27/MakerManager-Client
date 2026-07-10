package it.unipi.makermanagerclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.unipi.makermanagerclient.sessione.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller del pannello Impostazioni
 */
public class ImpostazioniController implements Initializable {

    // Deve rispecchiare soglia.progetti.consigliati in
    // application.properties lato Server
    private static final int SOGLIA_PREDEFINITA_SERVER = 3;

    @FXML
    private TextField campoSogliaMancanti;

    @FXML
    private Label etichettaEsito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // vedo se è stata già salvata una preferenza per la soglia durante la 
        // sessione corrente
        Integer sogliaAttuale = Sessione.getIstanza().getSogliaMancanti();

        campoSogliaMancanti.setText(sogliaAttuale != null ? String.valueOf(sogliaAttuale) : "");
        campoSogliaMancanti.setPromptText("predefinito Server: " + SOGLIA_PREDEFINITA_SERVER);

    }

    /**
     * quando viene premuto salva
     */
    @FXML
    private void onSalva() {

        String testo = campoSogliaMancanti.getText();

        // se l'utente ha premuto salva senza inserire un testo
        if (testo == null || testo.isBlank()) {
            Sessione.getIstanza().setSogliaMancanti(null);
            etichettaEsito.setText("Verrà usato il valore predefinito del Server.");
            return;
        }

        // altrimenti vedo se l'utente ha inserito un numero valido
        try {

            int valore = Integer.parseInt(testo.trim());

            if (valore < 0) {
                etichettaEsito.setText("La soglia non può essere negativa.");
                return;
            }

            Sessione.getIstanza().setSogliaMancanti(valore);
            etichettaEsito.setText("Impostazione salvata.");

        } catch (NumberFormatException e) {
            etichettaEsito.setText("Inserisci un numero intero valido.");
        }

    }

    /**
     * quando viene premuto ripristina
     */
    @FXML
    private void onRipristinaPredefinito() {

        campoSogliaMancanti.clear();
        Sessione.getIstanza().setSogliaMancanti(null);
        etichettaEsito.setText("Verrà usato il valore predefinito del Server.");

    }

}

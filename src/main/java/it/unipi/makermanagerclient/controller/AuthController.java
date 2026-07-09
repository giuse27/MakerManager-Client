package it.unipi.makermanagerclient.controller;

import java.io.IOException;

import it.unipi.makermanagerclient.App;
import it.unipi.makermanagerclient.model.RispostaAutenticazioneDTO;
import it.unipi.makermanagerclient.network.ApiException;
import it.unipi.makermanagerclient.sessione.Sessione;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthController {

    @FXML
    private TextField inserisciEmailAccedi;
    
    @FXML
    private TextField inserisciPasswordAccedi;

    @FXML
    private Label etichettaErroreAccedi;

    @FXML
    private Button pulsanteAccedi;

    @FXML
    private TextField inserisciNicknameRegistrati;

    @FXML
    private TextField inserisciEmailRegistrati;
    
    @FXML
    private TextField inserisciPasswordRegistrati;

    @FXML
    private Label etichettaErroreRegistrati;

    @FXML
    private Button pulsanteRegistrati;

    /**
     * pressione del tasto Accedi
     */
    @FXML
    private void onAccedi() {

        String email = inserisciEmailAccedi.getText();
        String password = inserisciPasswordAccedi.getText();

        etichettaErroreAccedi.setText("");
        pulsanteAccedi.setDisable(true);

        // TODO

    }

    /**
     * Pressione del tasto registrati
     */
    @FXML
    private void onRegistrati() {

        String nickname = inserisciNicknameRegistrati.getText();
        String email = inserisciEmailRegistrati.getText();
        String password = inserisciPasswordRegistrati.getText();

        etichettaErroreRegistrati.setText("");
        pulsanteRegistrati.setDisable(true);

        // TODO

    }

    /**
     * Avvia la sessione e passa alla shell principale
     * 
     * @param esito risposta (positiva) del server alla richiesta di autenticazione
     */
    private void completaAutenticazione(RispostaAutenticazioneDTO esito) {

        Sessione.getIstanza().avvia(
                esito.getToken(), 
                esito.getId(), 
                esito.getNickname(),
                esito.getEmail(), 
                esito.getRuolo()
        );

        try {

            App.setRoot("shell");

        } catch (IOException e) {
            throw new IllegalStateException("Impossibile caricare la Shell principale", e);
        }

    }

    /**
     * Traduce l'eccezione sollevata durante la chiamata http in un
     * messaggio comprensibile per l'utente: se è un errore applicativo
     * del Server (ApiException) ne mostra il messaggio gia' tradotto,
     * altrimenti si tratta di un problema di connessione.
     */
    private String messaggioErrore(Throwable errore) {

        if (errore instanceof ApiException apiException) {
            return apiException.getMessage();
        }

        return "Impossibile contattare il server: " + errore.getMessage();

    }

}

package it.unipi.makermanagerclient.util;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import it.unipi.makermanagerclient.network.ApiResponse;
import javafx.scene.control.TextArea;

/**
 * Utility condivisa da tutti i controller di pannello per eseguire una 
 * chiamata ApiClient e mostrarne l'esito in una TextArea, con formattazione 
 * uniforme (orario, stato, codice, body).
 */
public final class OutputFormatter {

    private static final DateTimeFormatter ORARIO = DateTimeFormatter.ofPattern("HH:mm:ss");

    private OutputFormatter() {
        throw new UnsupportedOperationException(
            "OutputFormatter non può essere istanziata."
        );
    }

    /**
     * Interfaccia funzionale minima per rappresentare una chiamata
     * ApiClient che puo' lanciare le eccezioni di rete.
     */
    @FunctionalInterface
    public interface ChiamataApi {
        ApiResponse esegui() throws IOException, InterruptedException;
    }

    /**
     * Esegue la chiamata, formatta il risultato (o l'errore) e lo scrive
     * in cima alla TextArea indicata, preservando la cronologia precedente.
     *
     * @param areaOutput la TextArea del pannello su cui scrivere
     * @param descrizioneOperazione es. "GET /api/catalogo", mostrata nel log
     * @param chiamata la chiamata ApiClient da eseguire
     */
    public static void eseguiEMostra(
        TextArea areaOutput, 
        String descrizioneOperazione, 
        ChiamataApi chiamata
    ) {

        try {

            ApiResponse risposta = chiamata.esegui();
            String esito = risposta.isSuccesso() ? "OK" : "ERRORE";

            scrivi(areaOutput, String.format(
                    "[%s] %s -> %s (status %d)%n%s",
                    orarioAttuale(), descrizioneOperazione, esito, 
                    risposta.statusCode(), risposta.body()
            ));

        } catch (IOException | InterruptedException e) {

            scrivi(areaOutput, String.format(
                    "[%s] %s -> ERRORE DI CONNESSIONE%n%s",
                    orarioAttuale(), descrizioneOperazione, e.getMessage()
            ));

        }

    }

    private static void scrivi(TextArea areaOutput, String testo) {
        String separatore = "----------------------------------------" + System.lineSeparator();
        areaOutput.setText(testo + System.lineSeparator() + separatore + areaOutput.getText());
    }

    private static String orarioAttuale() {
        return LocalTime.now().format(ORARIO);
    }

}
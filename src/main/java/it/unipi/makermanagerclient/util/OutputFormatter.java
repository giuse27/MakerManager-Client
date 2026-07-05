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
                    "[%s] %s%n%s  ·  status %d%n%n%s",
                    orarioAttuale(), descrizioneOperazione, esito,
                    risposta.statusCode(), formattaBody(risposta.body())
            ));

        } catch (IOException | InterruptedException e) {

            scrivi(areaOutput, String.format(
                    "[%s] %s%nERRORE DI CONNESSIONE%n%n%s",
                    orarioAttuale(), descrizioneOperazione, e.getMessage()
            ));

        }

    }

    private static void scrivi(TextArea areaOutput, String testo) {
        String separatore = System.lineSeparator()
                + "=".repeat(70) + System.lineSeparator()
                + System.lineSeparator();
        areaOutput.setText(testo + separatore + areaOutput.getText());
    }

    private static String orarioAttuale() {
        return LocalTime.now().format(ORARIO);
    }

    /**
     * Se il body sembra JSON (inizia con '{' o '['), lo reindenta per
     * leggibilita'; altrimenti lo restituisce cosi' com'e'. Non e' un
     * parser JSON completo: e' un semplice reindentatore basato sulle
     * parentesi, sufficiente perche' il body arriva gia' sintatticamente
     * valido dal Server.
     */
    private static String formattaBody(String body) {

        if (body == null || body.isBlank()) {
            return "(corpo vuoto)";
        }

        String testo = body.trim();
        if (testo.charAt(0) != '{' && testo.charAt(0) != '[') {
            return body;
        }

        StringBuilder risultato = new StringBuilder();
        int livello = 0;
        boolean dentroStringa = false;

        for (int i = 0; i < testo.length(); i++) {

            char c = testo.charAt(i);

            if (dentroStringa) {
                risultato.append(c);
                if (c == '\\' && i + 1 < testo.length()) {
                    risultato.append(testo.charAt(++i));
                } else if (c == '"') {
                    dentroStringa = false;
                }
                continue;
            }

            switch (c) {
                case '"' -> {
                    dentroStringa = true;
                    risultato.append(c);
                }
                case '{', '[' -> {
                    boolean vuoto = i + 1 < testo.length()
                            && (testo.charAt(i + 1) == '}' || testo.charAt(i + 1) == ']');
                    risultato.append(c);
                    if (!vuoto) {
                        livello++;
                        risultato.append(System.lineSeparator()).append("  ".repeat(livello));
                    }
                }
                case '}', ']' -> {
                    char precedente = risultato.charAt(risultato.length() - 1);
                    if (precedente != '{' && precedente != '[') {
                        livello--;
                        risultato.append(System.lineSeparator()).append("  ".repeat(Math.max(livello, 0)));
                    }
                    risultato.append(c);
                }
                case ',' -> risultato.append(c).append(System.lineSeparator()).append("  ".repeat(livello));
                case ':' -> risultato.append(c).append(' ');
                default -> {
                    if (!Character.isWhitespace(c)) {
                        risultato.append(c);
                    }
                }
            }

        }

        return risultato.toString();
    }

}
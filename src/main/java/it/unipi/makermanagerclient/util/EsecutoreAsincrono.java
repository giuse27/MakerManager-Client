package it.unipi.makermanagerclient.util;

import java.util.function.Consumer;

import javafx.concurrent.Task;

/**
 * Utility condivisa per eseguire un'operazione bloccante (tipicamente una
 * chiamata di rete tramite ApiClient/Service) su un thread separato dal
 * thread grafico di JavaFX, senza scrivere ogni volta un Task per esteso
 * in ciascun controller.
 *
 * Il Task viene eseguito su un thread daemon dedicato; i callback
 * ifSuccesso/ifFallimento vengono invocati automaticamente sul thread
 * dell'applicazione JavaFX (garanzia di Task.setOnSucceeded/setOnFailed),
 * quindi possono toccare direttamente i nodi grafici senza bisogno di un
 * ulteriore Platform.runLater.
 */
public final class EsecutoreAsincrono {

    private EsecutoreAsincrono() {
        throw new UnsupportedOperationException(
            "EsecutoreAsincrono è una classe di utilità statica e non può essere istanziata."
        );
    }

    /**
     * Interfaccia funzionale minima per rappresentare un'operazione
     * bloccante che produce un risultato di tipo T e puo' fallire con
     * qualunque eccezione. @FunctionalInterface mi permette di passare i dati
     * tramite espressioni lambda come () -> {}
     */
    @FunctionalInterface
    public interface Operazione<T> {
        T esegui() throws Exception;
    }

    /**
     * Esegue l'operazione su un thread separato e notifica l'esito sul
     * thread JavaFX.
     *
     * @param operazione il lavoro bloccante da eseguire in background
     * @param ifSuccesso invocato sul thread JavaFX con il risultato, se 
     *                   l'operazione ha successo. Consumer<T> è un'interfaccia
     *                   funzionale che consuma il dato senza restituire nulla
     * @param ifFallimento invocato sul thread JavaFX con l'eccezione, se l'operazione fallisce
     */
    public static <T> void esegui(
        Operazione<T> operazione,
        Consumer<T> ifSuccesso,
        Consumer<Throwable> ifFallimento
    ) {

        // creo il task per la gestione in background
        Task<T> task = new Task<>() {
            // call mi permette di eseguire l'operazione fuori dal thread della gui
            @Override
            protected T call() throws Exception {
                return operazione.esegui();
            }
        };

        // i blocchi di codice che imposto qui dentro vengono eseguiti direttamente
        // sul thread della gui java fx senza dover inserire Platform.runLater()
        task.setOnSucceeded(evento -> ifSuccesso.accept(task.getValue()));
        task.setOnFailed(evento -> ifFallimento.accept(task.getException()));

        // affido quindi il task a un nuovo thread e lo avvio
        Thread thread = new Thread(task, "chiamata-api");
        thread.setDaemon(true);
        thread.start();

    }

}

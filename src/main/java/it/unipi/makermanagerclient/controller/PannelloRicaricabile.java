package it.unipi.makermanagerclient.controller;

/**
 * I pannelli hanno bisogno di essere ricaricati ogni volta che qualcuno ci
 * clicca sopra (per aggiornare le get). chi implementa questa interfaccia
 * viene intercettato da ShellController
 */
public interface PannelloRicaricabile {

    /**
     * Ricarica i dati del pannello dal Server. Chiamato da
     * ShellController subito dopo aver reso visibile il pannello.
     */
    void ricarica();

}
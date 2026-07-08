module it.unipi.makermanagerclient {

    requires javafx.controls;
    requires javafx.fxml;

    // Modulo di sistema del JDK che contiene HttpClient/HttpRequest/
    // HttpResponse, usati da ApiClient per dialogare con il Server.
    // Con JPMS attivo va dichiarato esplicitamente come ogni altro
    // modulo, anche se fa parte del JDK stesso e non e' una libreria
    // esterna scaricata da Maven.
    requires java.net.http;

    // Libreria usata da GsonProvider per (de)serializzare i corpi JSON
    // scambiati con il Server
    requires com.google.gson;

    // "opens ... to javafx.fxml" concede a FXMLLoader accesso via
    // reflection alle classi di questo package: e' necessario per ogni
    // package che contiene classi Controller (con campi/metodi @FXML),
    // altrimenti FXMLLoader.load() fallisce a runtime con un errore di
    // accesso, anche se il codice compila correttamente.
    opens it.unipi.makermanagerclient to javafx.fxml;
    opens it.unipi.makermanagerclient.controller to javafx.fxml;

    // "opens ... to com.google.gson" concede a Gson accesso via
    // reflection ai campi PRIVATI dei DTO in questo package. Gson
    // per default legge/scrive direttamente i campi delle classi che
    // (de)serializza, e questo richiede "opens" anche se i campi hanno
    // gia' getter/setter pubblici.
    opens it.unipi.makermanagerclient.model to com.google.gson;

    // "exports" rende il package visibile dall'esterno del modulo. Va
    // dichiarato per ogni package che contiene classi referenziate da
    // altri moduli o usate pubblicamente (qui: il punto di ingresso
    // dell'app e i DTO, che sono classi pubbliche del dominio).
    exports it.unipi.makermanagerclient;
    exports it.unipi.makermanagerclient.controller;
    exports it.unipi.makermanagerclient.model;
    exports it.unipi.makermanagerclient.config;
    exports it.unipi.makermanagerclient.network;
    exports it.unipi.makermanagerclient.util;
    exports it.unipi.makermanagerclient.sessione;
    exports it.unipi.makermanagerclient.service;

}
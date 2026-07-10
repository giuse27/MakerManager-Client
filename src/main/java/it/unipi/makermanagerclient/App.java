package it.unipi.makermanagerclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App - Entry point del Client.
 *
 * Carica per prima la schermata di Login/Registrazione: solo dopo
 * un'autenticazione riuscita (che popola la Sessione) LoginController
 * sostituisce la root della Scene con la Shell principale (sidebar di
 * navigazione + area contenuti)
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("auth"), 1360, 900);
        scene.getStylesheets().add(getStylesheet());
        stage.setTitle("MakerManager");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * URL dell'unico foglio di stile del Client, da aggiungere a OGNI
     * Scene creata dall'applicazione (non solo quella principale):
     * ciascuna finestra secondaria (dettaglio inventario/progetto,
     * popup) crea una propria Scene, che per default non erediterebbe
     * lo stile della Scene principale.
     */
    public static String getStylesheet() {
        return App.class.getResource("styles.css").toExternalForm();
    }

    public static void main(String[] args) {
        launch();
    }

}
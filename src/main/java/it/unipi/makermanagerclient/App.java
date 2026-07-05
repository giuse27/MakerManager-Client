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
 * Carica la Shell principale (sidebar di navigazione + area contenuti),
 * che a sua volta precarica i 4 pannelli di dominio (Inizializzazione,
 * Catalogo, Inventario, Progetti).
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("shell"), 1360, 900);
        scene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        stage.setTitle("MakerManager");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
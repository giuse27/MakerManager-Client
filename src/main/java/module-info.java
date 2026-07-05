module it.unipi.makermanagerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens it.unipi.makermanagerclient to javafx.fxml;
    exports it.unipi.makermanagerclient;
}

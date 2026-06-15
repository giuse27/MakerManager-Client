module it.unipi.makermanagerclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens it.unipi.makermanagerclient to javafx.fxml;
    exports it.unipi.makermanagerclient;
}

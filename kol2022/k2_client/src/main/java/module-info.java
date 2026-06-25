module org.example.k2_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.k2_client to javafx.fxml;
    exports org.example.k2_client;
}
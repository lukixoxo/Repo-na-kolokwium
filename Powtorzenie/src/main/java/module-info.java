module org.example.powtorzenie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.powtorzenie to javafx.fxml;
    exports org.example.powtorzenie;
}
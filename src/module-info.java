module com.example.mapsdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires jxmapviewer2;
    requires java.sql;
    requires lombok;


    opens mapsdemo to javafx.fxml;
    exports mapsdemo;
}
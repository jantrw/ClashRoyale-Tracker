module me.jan_dev.clashroyaletracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires static lombok;


    opens me.jan_dev.clashroyaletracker to javafx.fxml;
    exports me.jan_dev.clashroyaletracker;
    exports me.jan_dev.clashroyaletracker.controller.component;
    opens me.jan_dev.clashroyaletracker.controller.component to javafx.fxml;
}
package me.jan_dev.clashroyaletracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jan_dev.clashroyaletracker.factory.ControllerFactory;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.io.IOException;

public class App extends Application {

    private final PlayerViewModel playerViewModel = new PlayerViewModel();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/MainView.fxml"));

        loader.setControllerFactory(new ControllerFactory(playerViewModel));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Clash Royale Tracker");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


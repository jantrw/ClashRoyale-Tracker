package me.jan_dev.clashroyaletracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jan_dev.clashroyaletracker.controller.MainController;
import me.jan_dev.clashroyaletracker.factory.ControllerFactory;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    private final PlayerViewModel playerViewModel = new PlayerViewModel();
    private MainController mainController;

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

    @Override
    // Application.stop() wird von JavaFX beim Beenden der Anwendung aufgerufen
    // hier ist die zentrale Methode um Controller/Services zu schließen
    public void stop() {
        // Wenn der Controller geladen wurde, sorgt ein Aufruf von close()
        // dafür, dass alle zugehörigen Ressourcen (z. B. Executor usw) freigegeben werden
        if (mainController != null) {
            try {
                mainController.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


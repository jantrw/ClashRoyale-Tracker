package me.jan_dev.clashroyaletracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.jan_dev.clashroyaletracker.factory.ControllerFactory;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.io.IOException;
import java.net.URL;
/**
  Factory zum Laden von FXML Views und zugehörigen Controllern
  Hauptsächlich vom MainController genutzt um dynamische Komponenten einzuhängen
  FXML-Dateien müssen unter /me/jan_dev/clashroyaletracker/fxml/ liegen
  Dieser Pfad ist hart verdrahtet könnte in Zukunft flexibilisiert werden
 */

public class ViewFactory {
    private static final PlayerViewModel SHARED_VM = new PlayerViewModel();
    private static final ControllerFactory FACTORY = new ControllerFactory(SHARED_VM);
    /**
      Lädt ein FXML-File und seinen Controller
      Gibt ein ViewTuple zurück das UI und Controller enthält
      @param fxmlPath Relativer Pfad zur FXML-Datei
      @param controllerClass Erwartete Controllerklasse
      @param <T> Typ des Controllers
      @return ViewTuple mit UI-Element und Controller
     */

    public static <T> ViewTuple<T> load(String fxmlPath, Class<T> controllerClass) {
        try {
            URL url = ViewFactory.class
                    .getResource("/me/jan_dev/clashroyaletracker/fxml/" + fxmlPath);
            FXMLLoader loader = new FXMLLoader(url);
            //loader.setLocation(fxmlPath); // Nicht benötigt da URL direkt gesetzt wird
            loader.setControllerFactory(FACTORY); // Die geladene UI-Komponente
            Parent view = loader.load(); // Der zugehörige Controller

            T controller = loader.getController();
            return new ViewTuple<>(view, controller);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }
    /**
    Gibt die gemeinsame PlayerViewModel-Instanz zurück
    Wird von allen Controllern geteilt
    @return Die gemeinsame PlayerViewModel-Instanz
    */

    public static PlayerViewModel getPlayerViewModel() {
        return SHARED_VM;
    }

    /**
     Container für View und Controller
     Wird beim Laden eines FXMLs erzeugt
     */

    public static class ViewTuple<T> {
        private final Parent view;
        private final T controller;
        public ViewTuple(Parent v, T c) { view = v; controller = c; }
        public Parent getView() { return view; }
        public T getController() { return controller; }
    }
}

package me.jan_dev.clashroyaletracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.jan_dev.clashroyaletracker.factory.ControllerFactory;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.io.IOException;
import java.net.URL;
/**
 * Factory zum Laden von FXML Views + zugehörigem Controller
 * Wird hauptsächlich vom MainController genutzt, um dynamisch Komponenten einzuhängen
 *
 * Wichtig: FXML-Dateien müssen unter dem festen Pfad "/me/jan_dev/clashroyaletracker/fxml/" liegen.
 * Das ist aktuell hart verdrahtet. Könnte man irgendwann mal flexibilisieren
 */

public class ViewFactory {
    private static final PlayerViewModel SHARED_VM = new PlayerViewModel();
    private static final ControllerFactory FACTORY = new ControllerFactory(SHARED_VM);
    /**
     * Lädt ein FXML-File inkl. Controller
     * Gibt ein ViewTuple zurück, das beides enthält (UI + Controller)
     *
     * @param fxmlPath Pfad zur FXML-Datei (relativ zum oben genannten Basisordner)
     * @param controllerClass Erwartete Controllerklasse
     * @param <T> Typ des Controllers
     * @return ViewTuple mit UI-Element (Parent) + Controller
     */

    public static <T> ViewTuple<T> load(String fxmlPath, Class<T> controllerClass) {
        try {
            URL url = ViewFactory.class
                    .getResource("/me/jan_dev/clashroyaletracker/fxml/" + fxmlPath);
            FXMLLoader loader = new FXMLLoader(url);
            //loader.setLocation(fxmlPath);
            loader.setControllerFactory(FACTORY);

            Parent view = loader.load();
            T controller = loader.getController();
            return new ViewTuple<>(view, controller);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }

    public static PlayerViewModel getPlayerViewModel() {
        return SHARED_VM;
    }

    /**
     * Kleines Containerobjekt, um View + Controller gemeinsam zurückzugeben
     * Wird immer beim Laden eines FXMLs erzeugt
     */

    public static class ViewTuple<T> {
        private final Parent view;
        private final T controller;
        public ViewTuple(Parent v, T c) { view = v; controller = c; }
        public Parent getView() { return view; }
        public T getController() { return controller; }
    }
}

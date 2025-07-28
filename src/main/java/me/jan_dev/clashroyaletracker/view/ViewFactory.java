package me.jan_dev.clashroyaletracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Factory zum Laden von FXML Views + zugehörigem Controller.
 * Wird hauptsächlich vom MainController genutzt, um dynamisch Komponenten einzuhängen.
 *
 * Wichtig: FXML-Dateien müssen unter dem festen Pfad "/me/jan_dev/clashroyaletracker/fxml/" liegen.
 * Das ist aktuell hart verdrahtet. Könnte man irgendwann mal flexibilisieren.
 */
public class ViewFactory {

    /**
     * Lädt ein FXML-File inkl. Controller.
     * Gibt ein ViewTuple zurück, das beides enthält (UI + Controller).
     *
     * @param fxmlPath Pfad zur FXML-Datei (relativ zum oben genannten Basisordner)
     * @param controllerClass Erwartete Controllerklasse
     * @param <T> Typ des Controllers
     * @return ViewTuple mit UI-Element (Parent) + Controller
     */
    public static <T> ViewTuple<T> load(String fxmlPath, Class<T> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader();
            // FXML-Datei suchen, relativ zum Klassenpfad
            URL fxmlUrl = ViewFactory.class.getResource("/me/jan_dev/clashroyaletracker/fxml/" + fxmlPath);
            loader.setLocation(fxmlUrl);

            // lädt das UI (Parent ist Basisklasse für alle Layout-Knoten)
            Parent view = loader.load();

            // Controller kommt aus der FXML, muss nicht extra instanziert werden
            T controller = loader.getController();

            return new ViewTuple<>(view, controller);
        } catch (IOException e) {
            // Richtig hart: RuntimeException--> Sollte in Prod-Anwendung besser behandelt werden.
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }

    /**
     * Kleines Containerobjekt, um View + Controller gemeinsam zurückzugeben.
     * Wird immer beim Laden eines FXMLs erzeugt.
     */
    public static class ViewTuple<T> {
        private final Parent view;
        private final T controller;

        public ViewTuple(Parent view, T controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public T getController() {
            return controller;
        }
    }
}

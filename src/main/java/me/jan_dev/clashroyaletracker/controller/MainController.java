package me.jan_dev.clashroyaletracker.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import me.jan_dev.clashroyaletracker.controller.component.HeaderController;
import me.jan_dev.clashroyaletracker.controller.component.SearchBarController;
import me.jan_dev.clashroyaletracker.service.ClashRoyaleService;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.view.ViewFactory;

/**
 * Einstiegspunkt für die GUI. Hängt dynamisch die Sub-Komponenten an (Header, SearchBar....)
 * Hier läuft alles zusammen
 */
public class MainController {

    @FXML private VBox mainVBox; // Container für alle dynamischen Komponenten

    private final ClashRoyaleService clashRoyaleService = new ClashRoyaleService();


    /**
     * Wird automatisch von JavaFX nach dem Laden des FXMLs aufgerufen
     * Initialisiert die View-Komponenten und registriert das Such-Callback
     */
    public void initialize() {
        // Header-Komponente laden und anhängen
        var headerTuple = ViewFactory.load("component/Header.fxml", HeaderController.class);
        mainVBox.getChildren().add(headerTuple.getView());

        // Suchleiste laden und anhängen
        var searchBarTuple = ViewFactory.load("component/SearchBar.fxml", SearchBarController.class);
        mainVBox.getChildren().add(searchBarTuple.getView());

        // Such-Callback setzen → Verknüpfung zwischen View und Logik
        searchBarTuple.getController().setOnSearch(this::handleSearch);

        System.out.println("[[DEBUG]]\tMainController initialized");
    }

    /**
     * Callback, das von der Suchleiste aufgerufen wird
     * Holt die Player-Daten vom Backend und triggert dann das UI-Update
     *
     * @param playerName Gesuchter Spielername
     */
    private void handleSearch(String playerName) {
        // Async-Aufruf des Services der später zurückkommt (CompletableFuture)
        System.out.println("[[DEBUG]]\t" + playerName);
        clashRoyaleService.fetchPlayerData(playerName)
                .thenAccept(player -> {
                    // JavaFX-UI darf nur im FX-Thread aktualisiert werden
                    Platform.runLater(() -> updateUI(player));
                    System.out.println("[[DEBUG]]\tPlayername: " + player.getName() + "\nPTrophaeen: " + player.getTrophies());

                })
                .exceptionally(ex -> {
                    // Fehler behandeln, ebenfalls im FX-Thread
                    Platform.runLater(() -> showError(ex));
                    ex.printStackTrace(); // sollte irgendwann mal ins Logging
                    return null;
                });
    }
    /**
     * Aktualisiert das UI mit den geladenen Daten
     * TODO: noch leer, hier später weitere Controller (z.B. WinLoss etc.) einhängen
     */
    private void updateUI(Player player) {
        // Daten an verschiedene Sub-Komponenten weiterleiten
        // aktuell noch leer evtl. MVP oder UI-Template vorbereiten
    }
    /**
     * Zeigt dem User eine Fehlermeldung an.
     * TODO: aktuell noch leer – Dialog o.ä. wäre hier sinnvoll
     */
    private void showError(Throwable ex) {
        // GUI-Fehleranzeige (Dialog, Snackbar, etc.) kommt noch
    }


}
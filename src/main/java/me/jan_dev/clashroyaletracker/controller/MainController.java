package me.jan_dev.clashroyaletracker.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.jan_dev.clashroyaletracker.controller.component.HeaderController;
import me.jan_dev.clashroyaletracker.controller.component.ProfileOverviewController;
import me.jan_dev.clashroyaletracker.controller.component.SearchBarController;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;
import me.jan_dev.clashroyaletracker.service.ClashRoyaleService;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.view.ViewFactory;

import java.util.concurrent.CompletableFuture;

/**
 * Einstiegspunkt für die GUI. Hängt dynamisch die Sub-Komponenten an (Header, SearchBar....)
 * Hier läuft alles zusammen
 */
public class MainController {

    @FXML
    private VBox mainVBox; // Container für alle dynamischen Komponenten
    @FXML
    private StackPane loadingOverlay;
    @FXML
    private ProgressIndicator progressIndicator;

    private final ClashRoyaleService clashRoyaleService = new ClashRoyaleService();

    private final PlayerViewModel playerViewModel = new PlayerViewModel();

    // referenz zum aktuellen Request
    private volatile CompletableFuture<?> currentRequest;


    /**
     * Wird automatisch von JavaFX nach dem Laden des FXMLs aufgerufen
     * Initialisiert die View-Komponenten und registriert das Such-Callback
     */
    public void initialize() {
        // Bind: Overlay sichtbar & managed an loadingProperty
        loadingOverlay.visibleProperty().bind(playerViewModel.loadingProperty());
        loadingOverlay.managedProperty().bind(playerViewModel.loadingProperty());

        // disabled main content während loading
        mainVBox.disableProperty().bind(playerViewModel.loadingProperty());

        // Header-Komponente laden und anhängen
        var header = ViewFactory.load("component/Header.fxml", HeaderController.class);
        mainVBox.getChildren().add(header.getView());

        // Suchleiste laden und anhängen
        var searchBar = ViewFactory.load("component/SearchBar.fxml", SearchBarController.class);
        mainVBox.getChildren().add(searchBar.getView());

        // Such-Callback setzen → Verknüpfung zwischen View und Logik
        searchBar.getController().setOnSearch(this::handleSearch);

        var profileOverviewTuple = ViewFactory.load("component/ProfileOverview.fxml", ProfileOverviewController.class);
        mainVBox.getChildren().add(profileOverviewTuple.getView());

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

        // vorherigen Request abbrechen
        if (currentRequest != null && !currentRequest.isDone()) {
            currentRequest.cancel(true);
        }

        // Loading auf true setzen
        playerViewModel.setLoadingOnFx(true);

        currentRequest = clashRoyaleService.fetchPlayerData(playerName)
                .whenComplete((player, throwable) -> {
                    // UI-Updates immer im FX-Thread
                    Platform.runLater(() -> {
                        // loading immer deaktivieren
                        playerViewModel.setLoading(false);

                        if (throwable != null) {
                            // Fehler anzeigen
                            showError(throwable);
                            throwable.printStackTrace();
                        } else {
                            // Player in ViewModel setzen -> alle Controller reagieren
                            ViewFactory.getPlayerViewModel().setPlayer(player);
                        }
                    });
                });
    }

    /**
     * Aktualisiert das UI mit den geladenen Daten
     */
    private void updateUI(Player player) {
        ViewFactory.getPlayerViewModel().setPlayer(player);
    }

    /**
     * Zeigt dem User eine Fehlermeldung an.
     * TODO: aktuell noch leer – Dialog o.ä. wäre hier sinnvoll
     */
    private void showError(Throwable ex) {
        // GUI-Fehleranzeige (Dialog, Snackbar, etc.) kommt noch
    }

    /**
     * WIrd von der App.java beim Beenden aufgerufen, um verwendete Ressourcen (wie Executor im ClashRoyaleService) freizugeben
     */
    public void close() {
        // mögliche Implementation von Listener oder so
        try {
            clashRoyaleService.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
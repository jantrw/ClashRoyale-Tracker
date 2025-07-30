package me.jan_dev.clashroyaletracker.controller.component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.util.function.Consumer;

public class SearchBarController {

    @FXML
    private VBox rootPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    // Callback, das der MainController setzt
    private Consumer<String> onSearch;

    private PlayerViewModel viewModel;

    // Wird durch ControllerFactory aufgerufen
    public SearchBarController(PlayerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void render(Player p) {
    }

    @FXML
    public void initialize() {
        System.out.println("[[DEBUG]]\tSearchBarController");
        // Platform.runLater stellt sicher, dass der Fokus nicht direkt gesetzt wird,
        Platform.runLater(() -> rootPane.requestFocus());

        viewModel.playerProperty().addListener((o, old, neu) -> render(neu));

        searchButton.setOnAction(e -> {
            if (onSearch != null) {
                onSearch.accept(searchField.getText().trim());
                System.out.println("[[DEBUG]]\tSearchButton was pressed");
            }
        });
    }

    // Setter für den Callback
    public void setOnSearch(Consumer<String> onSearch) {
        this.onSearch = onSearch;
    }
}

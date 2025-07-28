package me.jan_dev.clashroyaletracker.controller.component;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    @FXML
    public void initialize() {
        // Platform.runLater stellt sicher, dass der Fokus nicht direkt gesetzt wird,
        Platform.runLater(() -> rootPane.requestFocus());

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

    public Consumer<String> getOnSearch() {
        return onSearch;
    }
}

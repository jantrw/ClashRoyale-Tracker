package me.jan_dev.clashroyaletracker.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class HeaderController {

    @FXML
    private TextArea headerTextArea; // fx:id="headerTextArea"

    private PlayerViewModel viewModel;

    // Wird durch ControllerFactory aufgerufen
    public HeaderController(PlayerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        System.out.println("[[DEBUG]]\tHeaderController");
        viewModel.playerProperty().addListener((o, old, neu) -> render(neu));
    }

    private void render(Player p) {

    }
}

package me.jan_dev.clashroyaletracker.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class ProfileOverviewController {

    @FXML
    private Label nameLabel;

    private PlayerViewModel viewModel;

    // Wird durch ControllerFactory aufgerufen
    public ProfileOverviewController(PlayerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        System.out.println("[[DEBUG]]\tProfileOverviewController initialized");
        viewModel.playerProperty().addListener((o, old, neu) -> render(neu));
    }

    private void render(Player p) {
        if (p != null) {
            nameLabel.setText(p.getName());
        }
    }
}

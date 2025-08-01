package me.jan_dev.clashroyaletracker.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class ProfileOverviewController {

    @FXML
    AnchorPane profileOverviewPane;
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
            if (p.getName() != null) {
                System.out.println("Testtttttttt");
                nameLabel.setText(p.getName());
            } else {
                nameLabel.setText("Spieler nicht gefunden");
            }
        }
    }
}

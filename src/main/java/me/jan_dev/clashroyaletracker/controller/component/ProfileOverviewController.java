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
    @FXML
    private Label kuerzelLabel;
    @FXML
    private Label arenaNameLabel;
    @FXML
    private Label aktuelleTrophLabel;
    @FXML
    private Label besteTrophLabel;

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
                nameLabel.setText(p.getName());
                kuerzelLabel.setText(p.getTag());
                arenaNameLabel.setText(p.getArena().getName());
                aktuelleTrophLabel.setText(String.valueOf(p.getTrophies()));
                besteTrophLabel.setText(String.valueOf(p.getBestTrophies()));
            } else {
                nameLabel.setText("Spieler nicht gefunden");
            }
        }
    }
}

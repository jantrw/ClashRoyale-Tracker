package me.jan_dev.clashroyaletracker.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class ProfileOverviewController {

    @FXML
    AnchorPane profileOverviewPanel;
    @FXML
    private Label besteTrophLabel, nameLabel,kuerzelLabel,arenaNameLabel,aktuelleTrophLabel,arenaNameExpl, akutelleTrophExpl,bestTrophExpl;

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
                setAll(p);
            } else {
                nameLabel.setText("Spieler nicht gefunden");
            }
        }
    }

    // Setzt Spielerdaten, sodass sie im Frontend sichtbar sind
    private void setAll(Player p) {
        nameLabel.setText(p.getName());
        kuerzelLabel.setText(p.getTag());
        arenaNameExpl.setText("Arena");
        arenaNameLabel.setText(p.getArena().getName());
        akutelleTrophExpl.setText("Akt. Troph");
        aktuelleTrophLabel.setText(String.valueOf(p.getTrophies()));
        bestTrophExpl.setText("Best Troph");
        besteTrophLabel.setText(String.valueOf(p.getBestTrophies()));

        System.out.println("[[DEBUG]]\t Player stats was set");
    }
}

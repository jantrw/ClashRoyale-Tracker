package me.jan_dev.clashroyaletracker.controller.component;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.util.Arrays;

public class ProfileOverviewController {

    @FXML
    private Label besteTrophLabel, nameLabel, kuerzelLabel, arenaNameLabel, aktuelleTrophLabel, arenaNameExpl, akutelleTrophExpl, bestTrophExpl;
    @FXML
    private StackPane stackPaneProfileOverview;
    private PlayerViewModel viewModel;

    // Wird durch ControllerFactory aufgerufen
    public ProfileOverviewController(PlayerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        System.out.println("[[DEBUG]]\tProfileOverviewController initialized");
        stackPaneProfileOverview.setVisible(false);

        Arrays.asList(
                nameLabel, kuerzelLabel, arenaNameExpl, arenaNameLabel,
                akutelleTrophExpl, aktuelleTrophLabel,
                bestTrophExpl, besteTrophLabel
        ).forEach(this::addHoverAnimation);

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
        stackPaneProfileOverview.setVisible(true);
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

    private void addHoverAnimation(Label label) {
        // Übergang für Hover-In
        ScaleTransition hoverIn = new ScaleTransition(Duration.millis(150), label);
        hoverIn.setToX(1.1);
        hoverIn.setToY(1.1);

        // Übergang für Hover-Out
        ScaleTransition hoverOut = new ScaleTransition(Duration.millis(150), label);
        hoverOut.setToX(1.0);
        hoverOut.setToY(1.0);

        label.setOnMouseEntered(e -> {
            hoverOut.stop();   // laufende Out-Animation abbrechen
            hoverIn.playFromStart();
        });
        label.setOnMouseExited(e -> {
            hoverIn.stop();    // laufende In-Animation abbrechen
            hoverOut.playFromStart();
        });
    }
}

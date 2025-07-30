package me.jan_dev.clashroyaletracker.controller.component;

import javafx.fxml.FXML;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class WinLossController {

    private PlayerViewModel viewModel;

    // Wird durch ControllerFactory aufgerufen
    public WinLossController(PlayerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        viewModel.playerProperty().addListener((o, old, neu) -> render(neu));
    }

    private void render(Player p) {

    }
}

package me.jan_dev.clashroyaletracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

public class ProfileOverviewController {

    private PlayerViewModel playerViewModel;

    @FXML
    private Label nameLabel;

    public void setPlayerViewModel(PlayerViewModel viewModel) {
        this.playerViewModel = viewModel;
        viewModel.playerProperty().addListener((obs, oldVal, newVal) -> updateUI(newVal));
    }

    private void updateUI(Player player) {
        if (player != null) {
            nameLabel.setText(player.getName());
        }
    }
}


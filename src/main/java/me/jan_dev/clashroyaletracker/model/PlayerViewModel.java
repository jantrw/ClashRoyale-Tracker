package me.jan_dev.clashroyaletracker.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlayerViewModel {
    private final ObjectProperty<Player> player = new SimpleObjectProperty<>();

    public ObjectProperty<Player> playerProperty() {
        return player;
    }

    public Player getPlayer() {
        return player.get();
    }

    public void setPlayer(Player player) {
        this.player.set(player);
    }
}

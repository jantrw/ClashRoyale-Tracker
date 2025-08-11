package me.jan_dev.clashroyaletracker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlayerViewModel {
    private final ObjectProperty<Player> player = new SimpleObjectProperty<>();
    private final BooleanProperty loading = new SimpleBooleanProperty(false);


    public BooleanProperty loadingProperty() {
        return loading;
    }

    public boolean isLoading() {
        return loading.get();
    }

    public void setLoading(boolean value) {
        loading.set(value);
    }

    // setLoading on FX thread
    public void setLoadingOnFx(boolean value) {
        if (javafx.application.Platform.isFxApplicationThread()) {
            setLoading(value);
        } else {
            javafx.application.Platform.runLater(() -> setLoading(value));
        }
    }

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
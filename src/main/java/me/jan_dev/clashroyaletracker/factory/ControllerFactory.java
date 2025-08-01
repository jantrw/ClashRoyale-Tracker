package me.jan_dev.clashroyaletracker.factory;

import javafx.util.Callback;
import me.jan_dev.clashroyaletracker.model.PlayerViewModel;

import java.lang.reflect.Constructor;

/**
 * Erzeugt Controller-Instanzen und injiziert automatisch
 * das gemeinsame PlayerViewModel, wenn der Controller einen
 * entsprechenden Konstruktor anbietet
 */
public class ControllerFactory implements Callback<Class<?>, Object> {

    private final PlayerViewModel playerViewModel;

    public ControllerFactory(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    @Override
    public Object call(Class<?> controllerClass) {
        try {
            // Suche zuerst nach einem Konstruktor (PlayerViewModel)
            Constructor<?> ctor = controllerClass.getConstructor(PlayerViewModel.class);
            return ctor.newInstance(playerViewModel);
        } catch (NoSuchMethodException e) {
            // Kein solcher Konstruktor -> Default-Konstruktor verwenden
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Kann Controller nicht instanziieren: "
                        + controllerClass.getName(), ex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Erzeugen des Controllers: "
                    + controllerClass.getName(), e);
        }
    }
}

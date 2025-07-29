package me.jan_dev.clashroyaletracker.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lädt die application.properties und stellt sie als Singleton zur Verfügung
 */
public class Config {

    private static final String PROPERTIES_FILE = "/me/jan_dev/clashroyaletracker/application.properties";
    private static Config instance;

    private final Properties props;

    //  lädt die Properties
    private Config() {
        props = new Properties();
        try (InputStream in = getClass().getResourceAsStream(PROPERTIES_FILE)) {
            if (in == null) {
                throw new IllegalStateException("Konfigurationsdatei nicht gefunden: " + PROPERTIES_FILE);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Fehler beim Laden der Konfiguration: " + e.getMessage(), e);
        }
    }

    // Singleton
    public static Config load() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Gibt den Wert zur Property 'key' zurück, oder null wenn nicht vorhanden
     */
    public String get(String key) {
        return props.getProperty(key);
    }
}

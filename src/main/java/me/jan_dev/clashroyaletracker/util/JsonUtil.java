package me.jan_dev.clashroyaletracker.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility-Klasse für JSON‑Serialisierung und -Deserialisierung
 */
public final class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            // Unbekannte JSON-Felder einfach ignorieren
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // private Konstruktor, da Utility-Klasse
    private JsonUtil() { }

    /**
     * Deserialisiert die JSON-Antwort in ein Java-Objekt der Zielklasse.
     * Dabei gleicht Jackson automatisch die JSON-Feldnamen mit den Feldern der Zielklasse ab
     * und setzt die entsprechenden Werte, sofern sie vorhanden und kompatibel sind.
     *
     * Nutzt intern Jacksons ObjectMapper. Unbekannte JSON-Felder werden ignoriert.
     *
     * @param json  der JSON-String
     * @param clazz die Zielklasse
     * @param <T>   Typparameter
     * @return das gemappte Objekt
     * @throws RuntimeException bei Parsing-Fehlern
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fehler beim Parsen von JSON nach " + clazz.getSimpleName(), e);
        }
    }

    /**
     * Serialisiert ein Objekt in einen JSON-String.
     *
     * @param object das zu serialisierende Objekt
     * @return der JSON-String
     * @throws RuntimeException bei Fehlern
     */
    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fehler beim Serialisieren von Objekt zu JSON", e);
        }
    }
}

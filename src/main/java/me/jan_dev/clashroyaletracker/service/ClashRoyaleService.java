package me.jan_dev.clashroyaletracker.service;

import me.jan_dev.clashroyaletracker.model.Player;
import me.jan_dev.clashroyaletracker.util.Config;
import me.jan_dev.clashroyaletracker.util.JsonUtil;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClashRoyaleService implements AutoCloseable{

    private final ExecutorService executor;
    private final HttpClient httpClient;
    private final String apiUrl;
    private final String apiToken;

    public ClashRoyaleService() {
        // lädt aus application.properties
        Config cfg = Config.load();
        this.apiUrl   = cfg.get("api.url");
        this.apiToken = cfg.get("api.token");

        // Bestimme Anzahl Worker-Threads -> sicherer Fallback
        int threads = Math.max(2, Runtime.getRuntime().availableProcessors());
        // FixedThreadPool begrenzt gleichzeitige API-Aufrufe und vermeidet
        // unbegrenzten Verbrauch von System-Threads/CPU.
        this.executor = Executors.newFixedThreadPool(threads);
        this.httpClient = HttpClient.newBuilder()
                .executor(executor)
                .build();
    }

    /**
     * Zusätzlicher Konstruktor
     * Thread-Anzahl direkt setzen (z.B. in Tests).
     */
    public ClashRoyaleService(int threadCount) {
        // lädt aus application.properties
        Config cfg = Config.load();
        this.apiUrl   = cfg.get("api.url");
        this.apiToken = cfg.get("api.token");

        int threads = Math.max(1, threadCount);
        this.executor = Executors.newFixedThreadPool(threads);
        this.httpClient = HttpClient.newBuilder()
                .executor(executor)
                .build();
    }

    /**
     * Ruft asynchron die Player‑Daten ab und mapped das JSON in ein Player‑Objekt
     */
    public CompletableFuture<Player> fetchPlayerData(String playerName) {
        // Clash Royale Tags beginnen mit '#'
        String encodedTag = URLEncoder.encode(playerName.startsWith("#") ? playerName : "#" + playerName,
                StandardCharsets.UTF_8);
        System.out.println(encodedTag);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "players/" + encodedTag))
                .header("Authorization", "Bearer " + apiToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        return httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("[[DEBUG]] mapping thread: " + Thread.currentThread().getName());
                    System.out.println("[[DEBUG]] Status Code: " + response.statusCode());
                    return JsonUtil.fromJson(response.body(), Player.class);
                });
    }

    /**
     *  Beendet ExecutorService sauber +  wartet auf laufende Tasks
     *  Erzwingt ggf. ein sofortiges Herunterfahren
     *
     */
    public void shutdown() {
        // fordert herunterfahren an -> keine neuen Task mehr annehmen
        executor.shutdown();
        try {
            // kurz warten, damit laufende Aufgaben sauber abschließen können
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Wenn der Wartethread unterbrochen wird, sofort abbrechen
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private int readThreadCountFromConfig(Config cfg) {
        // erwarteter Key "api.thread.count"
        String val = cfg.get("api.thread.count");
        if (val == null || val.isBlank()) {
            // Fallback mindestens 2 oder anzahl der Prozessoren
            return Math.max(2, Runtime.getRuntime().availableProcessors());
        }
        try {
            int parsed = Integer.parseInt(val.trim());
            // schutz gegen unplausible Werte
            if (parsed <= 0) {
                return Math.max(2, Runtime.getRuntime().availableProcessors());
            }
            return parsed;
        } catch (NumberFormatException e) {
            // fallback bei ungültigem Format
            return Math.max(2, Runtime.getRuntime().availableProcessors());
        }
    }

    // Implementiert AutoClosable.close() -> verweist auf shutdown()
    @Override
    public void close() {
        shutdown();
    }
}

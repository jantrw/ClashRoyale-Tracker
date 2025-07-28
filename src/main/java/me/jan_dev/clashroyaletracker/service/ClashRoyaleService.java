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

public class ClashRoyaleService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiUrl;
    private final String apiToken;

    public ClashRoyaleService() {
        // lädt aus application.properties
        Config cfg = Config.load();
        this.apiUrl   = cfg.get("api.url");
        this.apiToken = cfg.get("api.token");
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
                    System.out.println("[[DEBUG]] Status Code: " + response.statusCode());
                    System.out.println("[[DEBUG]] Body: '" + response.body() + "'");
                    return JsonUtil.fromJson(response.body(), Player.class);
                });

//                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenApply(json -> JsonUtil.fromJson(json, Player.class));
    }
}

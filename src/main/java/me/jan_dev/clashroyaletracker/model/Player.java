package me.jan_dev.clashroyaletracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * Repräsentiert einen Clash Royale Spieler basierend auf der API-Antwort.
 * Diese Klasse wird durch Jackson automatisch mit JSON-Daten befüllt.
 * Mithilfe von Lombok (@Value) werden Getter, Setter, toString(), equals() und hashCode() automatisch generiert.
 *
 * Enthält verschachtelte Klassen für Clan-, Arena-, Karten- und Fortschrittsinformationen.
 */

@Getter
@Setter
@NoArgsConstructor // Wichtig für Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    private String tag;
    private String name;
    private int expLevel;
    private int trophies;
    private int bestTrophies;
    private int wins;
    private int losses;
    private int battleCount;
    private int threeCrownWins;
    private int challengeCardsWon;
    private int challengeMaxWins;
    private int tournamentCardsWon;
    private int tournamentBattleCount;
    private String role;
    private int donations;
    private int donationsReceived;
    private int totalDonations;
    private int warDayWins;
    private int clanCardsCollected;

    private Clan clan;
    private Arena arena;
    private LeagueStatistics leagueStatistics;
    private List<Badge> badges;
    private Card currentFavouriteCard;

    private int starPoints;
    private int expPoints;
    private int legacyTrophyRoadHighScore;

    private SeasonResult currentPathOfLegendSeasonResult;
    private SeasonResult lastPathOfLegendSeasonResult;
    private SeasonResult bestPathOfLegendSeasonResult;

    private Map<String, Progress> progress;
    private int totalExpPoints;

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Clan {
        private String tag;
        private String name;
        private int badgeId;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Arena {
        private int id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueStatistics {
        private Season currentSeason;
        private Season previousSeason;
        private Season bestSeason;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Season {
        private String id;
        private int trophies;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Badge {
        private String name;
        private int level;
        private int maxLevel;
        private int progress;
        private int target;
        private IconUrls iconUrls;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Card {
        private String name;
        private int id;
        private int maxLevel;
        private int elixirCost;
        private IconUrls iconUrls;
        private String rarity;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IconUrls {
        private String large;
        private String medium;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Progress {
        private Arena arena;
        private int trophies;
        private int bestTrophies;
    }

    @Getter
    @Setter
    @NoArgsConstructor // Wichtig für Jackson
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeasonResult {
        private int leagueNumber;
        private int trophies;
        private Integer rank;
    }
}

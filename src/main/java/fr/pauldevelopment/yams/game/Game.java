package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fr.pauldevelopment.yams.app.Engine;

public class Game {

    private Map<Player, Integer> bonusScore = new HashMap<>();
    private Player currentPlayer;
    private List<Dice> diceList = new ArrayList<>();
    private Map<Player, List<Integer>> gridList = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();
    private int remainingHalves = 0;
    private int rollCount = 0;
    private Map<Player, Integer> topScore = new HashMap<>();
    private Map<Player, Integer> totalScore = new HashMap<>();

    /**
     * Game constructor
     */
    public Game() {
        for (int i = 0; i < Engine.NUMBER_OF_DICE; i++) {
            this.diceList.add(new Dice());
        }
    }

    /**
     * Add a player
     *
     * @param player
     */
    public void addPlayer(Player player) {
        int id;

        if (this.players.isEmpty()) {
            id = 1;
        } else {
            id = Collections.max(this.players.keySet()) + 1;
        }

        this.players.put(id, player);
    }

    /**
     * Change the current player
     */
    public Player changeCurrentPlayer() {
        int currentPlayerId = this.currentPlayer.getId();
        int maxPlayerId = this.players.size();

        int id = 1;

        if (currentPlayerId != maxPlayerId) {
            id = currentPlayerId + 1;
        }

        this.currentPlayer = this.players.get(id);

        return this.currentPlayer;
    }

    /**
     * Check if player has finished his grid
     *
     * @param player
     */
    public void checkIfPlayerHasFinished(Player player) {
        this.players.get(player.getId()).setGridFinished(!this.getGridList(player).contains(-1));
    }

    /**
     * Get the bonus score of a player
     *
     * @param player
     *
     * @return the bonus score
     */
    public Integer getBonusScore(Player player) {
        return this.bonusScore.get(player);
    }

    /**
     * Get the combination list given a dice list
     *
     * @return the combination list
     */
    public List<Integer> getCombinationList() {
        return Combo.getCombinationList(this.diceList);
    }

    /**
     * Return the current player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Get the dice list
     *
     * @return the dice list
     */
    public List<Dice> getDiceList() {
        return this.diceList;
    }

    /**
     * Get the grid list of a player
     *
     * @param player
     *
     * @return the grid list of a player
     */
    public List<Integer> getGridList(Player player) {
        return this.gridList.get(player);
    }

    /**
     * Get the player list
     *
     * @return the player list
     */
    public Map<Integer, Player> getPlayers() {
        return this.players;
    }

    /**
     * Get the game podium
     *
     * @return the player list ordered by winner
     */
    public Map<Player, Integer> getPodium() {
        LinkedHashMap<Player, Integer> reverseSortedMap = new LinkedHashMap<>();
        this.totalScore.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }

    /**
     * Get the amount of remaining halves
     *
     * @return the amount of remaining halves
     */
    public int getRemainingHalves() {
        return this.remainingHalves;
    }

    /**
     * Get the roll count
     *
     * @return the roll count
     */
    public int getRollCount() {
        return this.rollCount;
    }

    /**
     * Get the top score of a player
     *
     * @param player
     *
     * @return the top score
     */
    public Integer getTopScore(Player player) {
        return this.topScore.get(player);
    }

    /**
     * Get the total score of a player
     *
     * @param player
     *
     * @return the total score
     */
    public Integer getTotalScore(Player player) {
        return this.totalScore.get(player);
    }

    /**
     * Increment the amount of roll count
     */
    public void incrementRollCount() {
        this.rollCount++;
    }

    /**
     * Init the game
     */
    public void init() {
        for (int i = 1; i < this.players.size() + 1; i++) {
            Player player = this.players.get(i);

            if (this.currentPlayer == null) {
                this.setCurrentPlayer(player);
            }

            this.gridList.put(player, new ArrayList<>(Collections.nCopies(15, -1)));
            this.bonusScore.put(player, 0);
            this.topScore.put(player, 0);
            this.totalScore.put(player, 0);

            player.setId(i);
            player.setGridFinished(false);
        }

        this.setRemainingHalves(this.getRemainingHalves() - 1);
    }

    /**
     * Is game over
     *
     * @return true if all players grid are finished, false if not
     */
    public boolean isGameOver() {
        AtomicInteger isOver = new AtomicInteger(0);

        this.players.forEach((id, player) -> {
            if (player.isGridFinished()) {
                isOver.incrementAndGet();
            }
        });

        return isOver.get() == this.players.size();
    }

    /**
     * Reset dice list
     */
    public void resetDiceList() {
        for (Dice dice : this.diceList) {
            dice.setKeepDice(false);
        }
    }

    /**
     * Reset the amount of roll count
     */
    public void resetRollCount() {
        this.rollCount = 0;
    }

    /**
     * Set the current player
     *
     * @param player
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Set the amount of remaining halves
     *
     * @param remainingHalves
     */
    public void setRemainingHalves(int remainingHalves) {
        this.remainingHalves = remainingHalves;
    }

    /**
     * Update grid value
     *
     * @param player
     * @param comboSquare
     * @param value
     */
    public void updateGridValueAndScore(Player player, int comboSquare, int value) {
        this.gridList.get(player).set(comboSquare, value);
        this.updateScores(player, comboSquare, value);
    }

    /**
     * Update scores
     *
     * @param player
     * @param comboSquare
     * @param value
     */
    public void updateScores(Player player, int comboSquare, int value) {
        if (comboSquare < 6) {
            if (this.getTopScore(player) + value >= Engine.BONUS_ELIGIBILITY && this.getBonusScore(player) == 0) {
                this.bonusScore.put(player, Engine.BONUS_VALUE);
                value += Engine.BONUS_VALUE;
            }

            this.topScore.put(player, this.topScore.get(player) + value);
        }

        this.totalScore.put(player, this.totalScore.get(player) + value);
    }
}

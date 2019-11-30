package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pauldevelopment.yams.app.Engine;

public class Game {

    private List<Dice> diceList = new ArrayList<>();
    private Map<Player, List<Integer>> gridList = new HashMap<>();
    private Map<Player, Integer> bonusScore = new HashMap<>();
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
     * Init the game
     *
     * @param players
     */
    public void init(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            this.gridList.put(player, new ArrayList<>(Collections.nCopies(15, -1)));
            this.topScore.put(player, 0);
            this.bonusScore.put(player, 0);
            this.totalScore.put(player, 0);
            player.setId(i + 1);
        }
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

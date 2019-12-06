package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import fr.pauldevelopment.yams.app.Engine;

public final class ArtificialIntelligence {

    /**
     * Private constructor to hide the implicit public one
     */
    private ArtificialIntelligence() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Apply the default strategy
     *
     * @param game
     * @param player
     *
     * @return the combo to use
     */
    public static int applyDefaultStrategy(Game game, Player player) {
        int maxOccurrencesDiceValue = ArtificialIntelligence.determinateDiceWithMaxOccurrences(game, player);
        int numberOfOccurrences = Combo.countOccurrences(game.getDiceList(), maxOccurrencesDiceValue);
        int combo = 0;

        if (ArtificialIntelligence.isSquareFree(game, player, maxOccurrencesDiceValue - 1) && (numberOfOccurrences >= 3 || Engine.BONUS_ELIGIBILITY - game.getTopScore(player) > 9 && numberOfOccurrences <= 2)) {
            combo = maxOccurrencesDiceValue - 1;
        } else if (!ArtificialIntelligence.isSquareFree(game, player, maxOccurrencesDiceValue - 1) && numberOfOccurrences >= 2) {
            combo = verifyCombos(game, player, Combo.FULL_HOUSE, Combo.FOUR_OF_A_KIND, Combo.THREE_OF_A_KIND, Combo.DOUBLE_PAIR, Combo.PAIR, Combo.CHANCE);
        } else {
            combo = sacrifyCombo(game, player);
        }

        return combo;
    }

    /**
     * Change the different dice given a dice value
     *
     * @param diceList
     * @param diceValueToKeep
     */
    public static void changeDifferentDice(List<Dice> diceList, int diceValueToKeep) {
        for (Dice dice : diceList) {
            if (dice.getValue() != diceValueToKeep) {
                dice.setKeepDice(false);
            }
        }
    }

    /**
     * Determinate the dice with the highest amount of occurrences
     *
     * @param game
     * @param player
     *
     * @return the dice value
     */
    public static int determinateDiceWithMaxOccurrences(Game game, Player player) {
        Map<Integer, Integer> diceOccurrences = new HashMap<>();
        int diceValue = 0;

        for (int i = 1; i <= 6; i++) {
            diceOccurrences.put(i, Combo.countOccurrences(game.getDiceList(), i));
        }

        Optional<Entry<Integer, Integer>> maxKeyReached = diceOccurrences.entrySet().stream().max((entry1, entry2) -> {
            if (entry1.getValue() > entry2.getValue() || entry1.getValue().equals(entry2.getValue()) && !isSquareFree(game, player, entry2.getKey() - 1)) {
                return 1;
            }

            return -1;
        });

        if (maxKeyReached.isPresent()) {
            diceValue = maxKeyReached.get().getKey();
        }

        return diceValue;
    }

    /**
     * Is the square is free on the grid
     *
     * @param game
     * @param player
     * @param gridKey
     *
     * @return true if is free, false if not
     */
    public static boolean isSquareFree(Game game, Player player, Integer gridKey) {
        return game.getGridList(player).get(gridKey).equals(-1);
    }

    /**
     * Prepare straight combo
     *
     * @param game
     * @param player
     */
    public static void prepareStraight(Game game, Player player) {
        int diceToChange = 0;

        if (ArtificialIntelligence.isSquareFree(game, player, Combo.LARGE_STRAIGHT)) {
            diceToChange = game.getDiceList().size() - 1;
        }

        List<Dice> gameDiceList = new ArrayList<>(game.getDiceList());
        gameDiceList.sort((dice1, dice2) -> dice2.getValue() - dice1.getValue());

        for (int i = 0; i < Engine.NUMBER_OF_DICE - 1; i++) {
            if (gameDiceList.get(i).getValue() - 1 != gameDiceList.get(i + 1).getValue()) {
                gameDiceList.get(diceToChange).setKeepDice(false);
                break;
            }
        }
    }

    /**
     * Sacrify combo a combo following a certain order
     *
     * @param game
     * @param player
     *
     * @return the combo sacrified
     */
    public static int sacrifyCombo(Game game, Player player) {
        for (int i = 14; i >= 6; i--) {
            if (isSquareFree(game, player, i)) {
                return i;
            }
        }

        for (int i = 0; i < 6; i++) {
            if (isSquareFree(game, player, i)) {
                return i;
            }
        }

        throw new IllegalStateException("Unable to determinate the category to sacrify");
    }

    /**
     * Use straights combos or prepare them
     *
     * @param game
     * @param player
     */
    public static void thinkAboutStraights(Game game, Player player) {
        int maxOccurrencesDiceValue = ArtificialIntelligence.determinateDiceWithMaxOccurrences(game, player);
        int numberOfOccurrences = Combo.countOccurrences(game.getDiceList(), maxOccurrencesDiceValue);
        boolean isLargeStraightAvailable = ArtificialIntelligence.isSquareFree(game, player, Combo.LARGE_STRAIGHT);
        boolean isSmallStraightAvailable = ArtificialIntelligence.isSquareFree(game, player, Combo.SMALL_STRAIGHT);

        if (game.getRollCount() != 3 && numberOfOccurrences < 2 && (isLargeStraightAvailable || isSmallStraightAvailable)) {
            prepareStraight(game, player);
        } else {
            ArtificialIntelligence.changeDifferentDice(game.getDiceList(), maxOccurrencesDiceValue);
        }
    }

    /**
     * Verify combos
     *
     * @param game
     * @param player
     * @param combos
     *
     * @return the combo used
     */
    public static int verifyCombos(Game game, Player player, int... combos) {
        for (int combo : combos) {
            if (ArtificialIntelligence.isSquareFree(game, player, combo) && !game.getCombinationList().get(combo).equals(0)) {
                return combo;
            }
        }

        return sacrifyCombo(game, player);
    }
}

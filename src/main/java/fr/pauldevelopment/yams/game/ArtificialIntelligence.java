package fr.pauldevelopment.yams.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public final class ArtificialIntelligence {

    /**
     * Private constructor to hide the implicit public one
     */
    private ArtificialIntelligence() {
        throw new IllegalStateException("Utility class");
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
     * If the dice list is not great enough, we will sacrify columns in a certain order
     *
     * @param game
     * @param player
     *
     * @return the column number
     */
    public static int determinateCategoryToSacrifice(Game game, Player player) {
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
     * Determinate the dice with the highest amount of occurrences
     *
     * @param game
     * @param player
     * @param diceList
     *
     * @return the dice value
     */
    public static int determinateDiceWithMaxOccurrences(Game game, Player player, List<Dice> diceList) {
        Map<Integer, Integer> diceOccurrences = new HashMap<>();
        int diceValue = 0;

        for (int i = 1; i <= 6; i++) {
            diceOccurrences.put(i, Combo.countOccurrences(diceList, i));
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
}

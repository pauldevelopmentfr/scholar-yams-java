package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.pauldevelopment.yams.app.Engine;

public class Combo {

    /**
     * Private constructor to hide the implicit public one
     */
    private Combo() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Count the number of occurrences of a dice in the dice list
     *
     * @param diceList
     * @param number of the dice
     *
     * @return the number of occurrences
     */
    public static int countOccurrences(List<Dice> diceList, int number) {
        int count = 0;

        for (int i = 0; i < Engine.NUMBER_OF_DICE; i++) {
            if (diceList.get(i).getValue() == number) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the combination list given a dice list
     *
     * @param diceList
     *
     * @return the combination list
     */
    public static List<Integer> getCombinationList(List<Dice> diceList) {
        List<Integer> combinationList = new ArrayList<>();

        combinationList.add(countOccurrences(diceList, 1));
        combinationList.add(countOccurrences(diceList, 2) * 2);
        combinationList.add(countOccurrences(diceList, 3) * 3);
        combinationList.add(countOccurrences(diceList, 4) * 4);
        combinationList.add(countOccurrences(diceList, 5) * 5);
        combinationList.add(countOccurrences(diceList, 6) * 6);
        combinationList.add(highestPair(diceList));
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(0);
        combinationList.add(countDiceValues(diceList));

        return combinationList;
    }

    /**
     * Sum each dice value of a dice list
     *
     * @param diceList
     *
     * @return the sum of each dice value
     */
    private static int countDiceValues(List<Dice> diceList) {
        return diceList.stream().mapToInt(Dice::getValue).sum();
    }

    /**
     * Get the highest pair score
     *
     * @param diceList
     *
     * @return the highest pair score
     */
    private static int highestPair(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());

        for (Dice dice : sortedDiceList) {
            if (countOccurrences(sortedDiceList, dice.getValue()) >= 2) {
                return dice.getValue() * 2;
            }
        }

        return 0;
    }
}

package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.pauldevelopment.yams.app.Engine;

public class Combo {

    public static final int CHANCE = 14;
    public static final int DOUBLE_PAIR = 7;
    public static final int FOUR_OF_A_KIND = 9;
    public static final int FULL_HOUSE = 10;
    public static final int LARGE_STRAIGHT = 12;
    public static final int PAIR = 6;
    public static final int SMALL_STRAIGHT = 11;
    public static final int THREE_OF_A_KIND = 8;
    public static final int YAMS = 13;

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
        combinationList.add(findHighestPair(diceList));
        combinationList.add(findDoublePair(diceList));
        combinationList.add(findThreeOfAKind(diceList));
        combinationList.add(findFourOfAKind(diceList));
        combinationList.add(findFullHouse(diceList));
        combinationList.add(findSmallStraight(diceList));
        combinationList.add(findLargeStraight(diceList));
        combinationList.add(findYams(diceList));
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
     * Find the double pair
     *
     * @param diceList
     *
     * @return the double pair score
     */
    private static int findDoublePair(List<Dice> diceList) {
        List<Dice> sortedDiceListAsc = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue)).collect(Collectors.toList());
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());

        int bottomPair = findDuplicateDice(sortedDiceListAsc, 2);
        int topPair = findDuplicateDice(sortedDiceList, 2);

        return bottomPair == topPair ? findDuplicateDice(sortedDiceList, 4) : bottomPair + topPair;
    }

    /**
     * Find duplicate dice by a minimum number of occurrences
     *
     * @param diceList
     * @param occurrences
     *
     * @return the score of these dice
     */
    private static int findDuplicateDice(List<Dice> diceList, int occurrences) {
        for (Dice dice : diceList) {
            if (countOccurrences(diceList, dice.getValue()) >= occurrences) {
                return dice.getValue() * occurrences;
            }
        }

        return 0;
    }

    /**
     * Find the four of a kind score
     *
     * @param diceList
     *
     * @return the four of a kind score
     */
    private static int findFourOfAKind(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());
        return findDuplicateDice(sortedDiceList, 4);
    }

    /**
     * Find the full house score
     *
     * @param diceList
     *
     * @return the full house score
     */
    private static int findFullHouse(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());
        Map<Integer, Integer> diceOccurrences = new HashMap<>();

        for (Dice dice : sortedDiceList) {
            if (diceOccurrences.get(dice.getValue()) == null) {
                diceOccurrences.put(dice.getValue(), 0);
            }

            diceOccurrences.put(dice.getValue(), diceOccurrences.get(dice.getValue()) + 1);
        }

        for (Integer values : diceOccurrences.values()) {
            if (values < 2) {
                return 0;
            }
        }

        return 25;
    }

    /**
     * Find the highest pair score
     *
     * @param diceList
     *
     * @return the highest pair score
     */
    private static int findHighestPair(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());
        return findDuplicateDice(sortedDiceList, 2);
    }

    /**
     * Find the large straight score
     *
     * @param diceList
     *
     * @return the large straight score
     */
    private static int findLargeStraight(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue)).collect(Collectors.toList());
        return findStraight(sortedDiceList, sortedDiceList.remove(0).getValue()) == 6 ? 40 : 0;
    }

    /**
     * Find the small straight score
     *
     * @param diceList
     *
     * @return the small straight score
     */
    private static int findSmallStraight(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue)).collect(Collectors.toList());
        return findStraight(sortedDiceList, sortedDiceList.remove(0).getValue()) == 5 ? 30 : 0;
    }

    /**
     * Find straight recursively
     *
     * @param diceList
     * @param currentDiceValue
     *
     * @return highest dice value of the straight if dice list is a straight, 0 if not
     */
    private static int findStraight(List<Dice> diceList, int currentDiceValue) {
        if (diceList.isEmpty()) {
            return currentDiceValue;
        }

        int nextDiceValue = diceList.remove(0).getValue();

        if (currentDiceValue != nextDiceValue - 1) {
            return 0;
        }

        return findStraight(diceList, nextDiceValue);
    }

    /**
     * Find the three of a kind score
     *
     * @param diceList
     *
     * @return the three of a kind score
     */
    private static int findThreeOfAKind(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());
        return findDuplicateDice(sortedDiceList, 3);
    }

    /**
     * Find the yams score
     *
     * @param diceList
     *
     * @return the yams score
     */
    private static int findYams(List<Dice> diceList) {
        List<Dice> sortedDiceList = diceList.stream().sorted(Comparator.comparingInt(Dice::getValue).reversed()).collect(Collectors.toList());
        return findDuplicateDice(sortedDiceList, 5) > 0 ? 50 : 0;
    }
}

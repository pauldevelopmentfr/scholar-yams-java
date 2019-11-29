package fr.pauldevelopment.yams.game;

import java.util.Random;

public class Dice {

    private int value;
    private boolean keepDice = false;

    /**
     * Get the dice value
     *
     * @return dice value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Roll a dice
     *
     * @return a random number between 1 and 6
     */
    public void roll() {
        if (this.keepDice) {
            return;
        }

        Random number = new Random();
        this.value = number.nextInt(6) + 1;
    }

    /**
     * Set if we keep dice or not
     *
     * @param keepDice
     */
    public void setKeepDice(boolean keepDice) {
        this.keepDice = keepDice;
    }
}

package fr.pauldevelopment.yams.game;

import java.util.Random;

public class Dice {

    private boolean keepDice = false;
    private int value;

    /**
     * Dice constructor
     */
    public Dice() {

    }

    /**
     * Dice constructor
     *
     * @param value
     */
    public Dice(int value) {
        this.value = value;
    }

    /**
     * Get the dice value
     *
     * @return dice value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Is keep dice
     *
     * @return true if we keep dice, false if not
     */
    public boolean isKeepDice() {
        return this.keepDice;
    }

    /**
     * Roll a dice
     */
    public void roll() {
        if (this.keepDice) {
            return;
        }

        Random number = new Random();
        this.value = number.nextInt(6) + 1;
        this.keepDice = true;
    }

    /**
     * Set if we keep dice or not
     *
     * @param keepDice
     */
    public void setKeepDice(boolean keepDice) {
        this.keepDice = keepDice;
    }

    /**
     * Update keep status
     */
    public void updateKeepStatus() {
        this.keepDice = !this.keepDice;
    }
}

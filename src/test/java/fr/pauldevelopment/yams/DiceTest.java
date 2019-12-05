package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.game.Dice;

public class DiceTest {

    public Dice dice;

    @BeforeEach
    public void init() {
        this.dice = new Dice();
        this.dice.roll();
    }

    @Test
    public void testIncrementDiceMaxValue() {
        this.dice = new Dice(6);
        this.dice.incrementValue();
        assertEquals(1, this.dice.getValue());
    }

    @Test
    public void testIncrementDiceValue() {
        int oldValue = this.dice.getValue();
        this.dice.incrementValue();

        int futureValue;

        if (oldValue == 6) {
            futureValue = 1;
        } else {
            futureValue = oldValue + 1;
        }

        assertEquals(futureValue, this.dice.getValue());
    }

    @Test
    public void testRollAfterDecidedToChange() {
        int oldValue = this.dice.getValue();

        this.dice.updateKeepStatus();

        while (this.dice.getValue() == oldValue) {
            this.dice.roll();
            this.dice.updateKeepStatus();
        }

        assertNotEquals(oldValue, this.dice.getValue());
    }

    @Test
    public void testRollAfterDecidedToKeep() {
        int oldValue = this.dice.getValue();

        this.dice.updateKeepStatus();
        this.dice.updateKeepStatus();
        this.dice.roll();

        assertEquals(oldValue, this.dice.getValue());
    }

    @Test
    public void testRollDice() {
        assertTrue(Arrays.asList(1, 2, 3, 4, 5, 6).contains(this.dice.getValue()));
    }

    @Test
    public void testRollDiceKept() {
        int oldValue = this.dice.getValue();

        this.dice.setKeepDice(true);
        this.dice.roll();

        assertEquals(oldValue, this.dice.getValue());
    }
}

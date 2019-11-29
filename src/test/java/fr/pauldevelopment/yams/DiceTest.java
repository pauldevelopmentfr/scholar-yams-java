package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.game.Dice;

public class DiceTest {

    @Test
    public void testRollDice() {
        Dice dice = new Dice();
        dice.roll();

        assertTrue(Arrays.asList(1, 2, 3, 4, 5, 6).contains(dice.getValue()));
    }

    @Test
    public void testRollDiceKept() {
        Dice dice = new Dice();
        dice.roll();

        int oldValue = dice.getValue();

        dice.setKeepDice(true);
        dice.roll();

        assertEquals(oldValue, dice.getValue());
    }
}

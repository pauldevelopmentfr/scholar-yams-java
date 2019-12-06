package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.game.ArtificialIntelligence;
import fr.pauldevelopment.yams.game.Combo;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;
import fr.pauldevelopment.yams.game.Player;

public class ArtificialIntelligenceTest {

    Game game = new Game();

    @BeforeEach
    public void init() {
        this.game.getDiceList().clear();
    }

    @Test
    public void testApplyDefaultStrategyAndSacrifyCombo() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(2));

        this.game.updateGridValueAndScore(paul, 0, 1);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 8);
        this.game.updateGridValueAndScore(paul, 4, 20);
        this.game.updateGridValueAndScore(paul, 5, 12);
        this.game.updateGridValueAndScore(paul, Combo.PAIR, 10);

        assertEquals(Combo.CHANCE, ArtificialIntelligence.applyDefaultStrategy(this.game, paul));
    }

    @Test
    public void testApplyDefaultStrategyFillOneAndEnough() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));

        this.game.updateGridValueAndScore(paul, 4, 20);

        assertEquals(0, ArtificialIntelligence.applyDefaultStrategy(this.game, paul));
    }

    @Test
    public void testApplyDefaultStrategyFillOneButNotEnough() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));

        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 4, 20);
        this.game.updateGridValueAndScore(paul, 5, 30);

        assertEquals(Combo.THREE_OF_A_KIND, ArtificialIntelligence.applyDefaultStrategy(this.game, paul));
    }

    @Test
    public void testApplyDefaultStrategyFillThree() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));

        this.game.updateGridValueAndScore(paul, 4, 20);

        assertEquals(2, ArtificialIntelligence.applyDefaultStrategy(this.game, paul));
    }

    @Test
    public void testChangeDifferentDice() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        ArtificialIntelligence.changeDifferentDice(this.game.getDiceList(), 5);

        int count = 0;

        for (Dice dice : this.game.getDiceList()) {
            if (dice.isKeepDice() == false) {
                count++;
            }
        }

        assertEquals(2, count);
    }

    @Test
    public void testComboConstructorIsPrivate() throws NoSuchMethodException, SecurityException {
        Constructor<ArtificialIntelligence> constructor = ArtificialIntelligence.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> constructor.newInstance());
    }

    @Test
    public void testDeterminateDiceWithMaxOccurrencesDoublePair() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        assertEquals(5, ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, paul));
    }

    @Test
    public void testDeterminateDiceWithMaxOccurrencesDoublePairButHighestIsNotFree() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        this.game.updateGridValueAndScore(paul, 4, 15);

        assertEquals(2, ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, paul));
    }

    @Test
    public void testDeterminateDiceWithMaxOccurrencesFullHouse() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));

        assertEquals(2, ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, paul));
    }

    @Test
    public void testDeterminateDiceWithMaxOccurrencesNothing() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(3));

        assertEquals(6, ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, paul));
    }

    @Test
    public void testDeterminateDiceWithMaxOccurrencesPair() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));

        assertEquals(1, ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, paul));
    }

    @Test
    public void testPrepareLargeStraight() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        Dice smallestDice = new Dice(1);

        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(smallestDice);
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        ArtificialIntelligence.prepareStraight(this.game, paul);

        assertFalse(smallestDice.isKeepDice());
    }

    @Test
    public void testPrepareSmallStraight() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        Dice highestDice = new Dice(6);

        this.game.getDiceList().add(highestDice);
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        this.game.updateGridValueAndScore(paul, Combo.LARGE_STRAIGHT, 0);
        ArtificialIntelligence.prepareStraight(this.game, paul);

        assertFalse(highestDice.isKeepDice());
    }

    @Test
    public void testSacrifyCombo() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, Combo.PAIR, 4);
        this.game.updateGridValueAndScore(paul, Combo.DOUBLE_PAIR, 14);
        this.game.updateGridValueAndScore(paul, Combo.THREE_OF_A_KIND, 15);
        this.game.updateGridValueAndScore(paul, Combo.FOUR_OF_A_KIND, 0);
        this.game.updateGridValueAndScore(paul, Combo.FULL_HOUSE, 25);
        this.game.updateGridValueAndScore(paul, Combo.SMALL_STRAIGHT, 30);
        this.game.updateGridValueAndScore(paul, Combo.LARGE_STRAIGHT, 0);
        this.game.updateGridValueAndScore(paul, Combo.YAMS, 0);
        this.game.updateGridValueAndScore(paul, Combo.CHANCE, 21);

        this.game.updateGridValueAndScore(paul, Combo.YAMS, 22);

        assertEquals(1, ArtificialIntelligence.sacrifyCombo(this.game, paul));
    }

    @Test
    public void testSacrifyComboButFull() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 6);
        this.game.updateGridValueAndScore(paul, 3, 4);
        this.game.updateGridValueAndScore(paul, 4, 20);
        this.game.updateGridValueAndScore(paul, 5, 12);
        this.game.updateGridValueAndScore(paul, Combo.PAIR, 4);
        this.game.updateGridValueAndScore(paul, Combo.DOUBLE_PAIR, 14);
        this.game.updateGridValueAndScore(paul, Combo.THREE_OF_A_KIND, 15);
        this.game.updateGridValueAndScore(paul, Combo.FOUR_OF_A_KIND, 0);
        this.game.updateGridValueAndScore(paul, Combo.FULL_HOUSE, 25);
        this.game.updateGridValueAndScore(paul, Combo.SMALL_STRAIGHT, 30);
        this.game.updateGridValueAndScore(paul, Combo.LARGE_STRAIGHT, 0);
        this.game.updateGridValueAndScore(paul, Combo.YAMS, 0);
        this.game.updateGridValueAndScore(paul, Combo.CHANCE, 21);

        this.game.updateGridValueAndScore(paul, Combo.YAMS, 22);

        assertThrows(IllegalStateException.class, () -> ArtificialIntelligence.sacrifyCombo(this.game, paul));
    }

    @Test
    public void testThinkAboutStraightsButNotFree() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        Dice highestDice = new Dice(6);

        this.game.getDiceList().add(highestDice);
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        this.game.updateGridValueAndScore(paul, Combo.SMALL_STRAIGHT, 30);
        this.game.updateGridValueAndScore(paul, Combo.LARGE_STRAIGHT, 0);

        ArtificialIntelligence.thinkAboutStraights(this.game, paul);

        int count = 0;

        for (Dice dice : this.game.getDiceList()) {
            if (dice.isKeepDice() == false) {
                count++;
            }
        }

        assertEquals(4, count);
    }

    @Test
    public void testThinkAboutStraightsButTooManyRolls() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        Dice highestDice = new Dice(6);

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(highestDice);

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        this.game.incrementRollCount();
        this.game.incrementRollCount();
        this.game.incrementRollCount();

        ArtificialIntelligence.thinkAboutStraights(this.game, paul);

        int count = 0;

        for (Dice dice : this.game.getDiceList()) {
            if (dice.isKeepDice() == false) {
                count++;
            }
        }

        assertEquals(4, count);
    }

    @Test
    public void testThinkAboutStraightsWithMultipleOccurrences() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(6));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        ArtificialIntelligence.thinkAboutStraights(this.game, paul);

        int count = 0;

        for (Dice dice : this.game.getDiceList()) {
            if (dice.isKeepDice() == false) {
                count++;
            }
        }

        assertEquals(3, count);
    }

    @Test
    public void testThinkAboutStraightsWithStraightDraw() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        Dice smallestDice = new Dice(1);

        this.game.getDiceList().add(smallestDice);
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(6));

        for (Dice dice : this.game.getDiceList()) {
            dice.setKeepDice(true);
        }

        ArtificialIntelligence.thinkAboutStraights(this.game, paul);

        assertFalse(smallestDice.isKeepDice());
    }

    @Test
    public void testVerifyComboFreeAndNotEmpty() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        assertEquals(Combo.CHANCE, ArtificialIntelligence.verifyCombos(this.game, paul, Combo.CHANCE));
    }

    @Test
    public void testVerifyComboFreeButEmpty() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        assertEquals(Combo.CHANCE, ArtificialIntelligence.verifyCombos(this.game, paul, Combo.YAMS));
    }

    @Test
    public void testVerifyComboNotFreeButNotEmpty() {
        Player paul = new Player("Paul");

        this.game.addPlayer(paul);
        this.game.init();

        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        this.game.updateGridValueAndScore(paul, Combo.DOUBLE_PAIR, 22);

        assertEquals(Combo.PAIR, ArtificialIntelligence.verifyCombos(this.game, paul, Combo.DOUBLE_PAIR, Combo.PAIR));
    }
}

package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.app.Engine;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;
import fr.pauldevelopment.yams.game.Human;
import fr.pauldevelopment.yams.game.Player;

public class GameTest {

    Game game;

    @BeforeEach
    public void init() {
        this.game = new Game();
    }

    @Test
    public void testAddPlayer() {
        int oldSize = this.game.getPlayers().size();

        this.game.addPlayer(new Human("Paul"));

        assertEquals(oldSize + 1, this.game.getPlayers().size());
    }

    @Test
    public void testAddTwoPlayers() {
        int oldSize = this.game.getPlayers().size();

        this.game.addPlayer(new Human("Paul"));
        this.game.addPlayer(new Human("Fred"));

        assertEquals(oldSize + 2, this.game.getPlayers().size());
    }

    @Test
    public void testBonusValueIsGivenOnceOnlyBonusScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 5, 30);
        this.game.updateGridValueAndScore(paul, 4, 25);
        this.game.updateGridValueAndScore(paul, 3, 20);
        this.game.updateGridValueAndScore(paul, 2, 15);
        this.game.updateGridValueAndScore(paul, 1, 10);
        this.game.updateGridValueAndScore(paul, 0, 5);
        assertEquals(35, this.game.getBonusScore(paul));
    }

    @Test
    public void testBonusValueIsGivenOnceOnlyTopScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 5, 30);
        this.game.updateGridValueAndScore(paul, 4, 25);
        this.game.updateGridValueAndScore(paul, 3, 20);
        this.game.updateGridValueAndScore(paul, 2, 15);
        this.game.updateGridValueAndScore(paul, 1, 10);
        this.game.updateGridValueAndScore(paul, 0, 5);
        assertEquals(140, this.game.getTopScore(paul));
    }

    @Test
    public void testBonusValueIsGivenOnceOnlyTotalScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 5, 30);
        this.game.updateGridValueAndScore(paul, 6, 12);
        this.game.updateGridValueAndScore(paul, 4, 25);
        this.game.updateGridValueAndScore(paul, 3, 20);
        this.game.updateGridValueAndScore(paul, 2, 15);
        this.game.updateGridValueAndScore(paul, 1, 10);
        this.game.updateGridValueAndScore(paul, 0, 5);
        assertEquals(152, this.game.getTotalScore(paul));
    }

    @Test
    public void testChangePlayerFirstPlayer() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.changeCurrentPlayer();
        this.game.changeCurrentPlayer();

        assertEquals(paul, this.game.getCurrentPlayer());
    }

    @Test
    public void testChangePlayerNextPlayer() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.changeCurrentPlayer();

        assertEquals(fred, this.game.getCurrentPlayer());
    }

    @Test
    public void testCreateGame() {
        assertEquals(5, this.game.getDiceList().size());
    }

    @Test
    public void testCurrentPlayer() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));

        assertEquals(paul, this.game.getCurrentPlayer());
    }

    @Test
    public void testGameInit() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));

        assertEquals(1, paul.getId());
    }

    @Test
    public void testGameInitWithMultiplePlayers() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));

        assertEquals(2, fred.getId());
    }

    @Test
    public void testGameIsNotOver() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(paul, 6, 0);
        this.game.updateGridValueAndScore(paul, 7, 0);
        this.game.updateGridValueAndScore(paul, 8, 0);
        this.game.updateGridValueAndScore(paul, 9, 0);
        this.game.updateGridValueAndScore(paul, 10, 0);
        this.game.updateGridValueAndScore(paul, 11, 0);
        this.game.updateGridValueAndScore(paul, 12, 0);
        this.game.updateGridValueAndScore(paul, 13, 0);
        this.game.updateGridValueAndScore(paul, 14, 20);
        this.game.updateGridValueAndScore(fred, 0, 3);
        this.game.updateGridValueAndScore(fred, 1, 6);
        this.game.updateGridValueAndScore(fred, 2, 9);
        this.game.updateGridValueAndScore(fred, 3, 12);
        this.game.updateGridValueAndScore(fred, 4, 15);
        this.game.updateGridValueAndScore(fred, 5, 18);
        this.game.updateGridValueAndScore(fred, 6, 0);
        this.game.updateGridValueAndScore(fred, 7, 0);
        this.game.updateGridValueAndScore(fred, 8, 0);
        this.game.updateGridValueAndScore(fred, 9, 0);
        this.game.updateGridValueAndScore(fred, 10, 0);
        this.game.updateGridValueAndScore(fred, 11, 0);
        this.game.updateGridValueAndScore(fred, 12, 0);
        this.game.updateGridValueAndScore(fred, 13, 0);

        this.game.checkIfPlayerHasFinished(paul);
        this.game.checkIfPlayerHasFinished(fred);

        assertFalse(this.game.isGameOver());
    }

    @Test
    public void testGameIsOver() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(paul, 6, 0);
        this.game.updateGridValueAndScore(paul, 7, 0);
        this.game.updateGridValueAndScore(paul, 8, 0);
        this.game.updateGridValueAndScore(paul, 9, 0);
        this.game.updateGridValueAndScore(paul, 10, 0);
        this.game.updateGridValueAndScore(paul, 11, 0);
        this.game.updateGridValueAndScore(paul, 12, 0);
        this.game.updateGridValueAndScore(paul, 13, 0);
        this.game.updateGridValueAndScore(paul, 14, 20);
        this.game.updateGridValueAndScore(fred, 0, 3);
        this.game.updateGridValueAndScore(fred, 1, 6);
        this.game.updateGridValueAndScore(fred, 2, 9);
        this.game.updateGridValueAndScore(fred, 3, 12);
        this.game.updateGridValueAndScore(fred, 4, 15);
        this.game.updateGridValueAndScore(fred, 5, 18);
        this.game.updateGridValueAndScore(fred, 6, 0);
        this.game.updateGridValueAndScore(fred, 7, 0);
        this.game.updateGridValueAndScore(fred, 8, 0);
        this.game.updateGridValueAndScore(fred, 9, 0);
        this.game.updateGridValueAndScore(fred, 10, 0);
        this.game.updateGridValueAndScore(fred, 11, 0);
        this.game.updateGridValueAndScore(fred, 12, 0);
        this.game.updateGridValueAndScore(fred, 13, 0);
        this.game.updateGridValueAndScore(fred, 14, 20);

        this.game.checkIfPlayerHasFinished(paul);
        this.game.checkIfPlayerHasFinished(fred);

        assertTrue(this.game.isGameOver());
    }

    @Test
    public void testGameWithFullPlayerGrid() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(paul, 6, 0);
        this.game.updateGridValueAndScore(paul, 7, 0);
        this.game.updateGridValueAndScore(paul, 8, 0);
        this.game.updateGridValueAndScore(paul, 9, 0);
        this.game.updateGridValueAndScore(paul, 10, 0);
        this.game.updateGridValueAndScore(paul, 11, 0);
        this.game.updateGridValueAndScore(paul, 12, 0);
        this.game.updateGridValueAndScore(paul, 13, 0);
        this.game.updateGridValueAndScore(paul, 14, 20);

        this.game.checkIfPlayerHasFinished(paul);

        assertTrue(paul.isGridFinished());
    }

    @Test
    public void testGameWithNotFullPlayerGrid() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(paul, 6, 0);
        this.game.updateGridValueAndScore(paul, 7, 0);
        this.game.updateGridValueAndScore(paul, 8, 0);
        this.game.updateGridValueAndScore(paul, 9, 0);
        this.game.updateGridValueAndScore(paul, 10, 0);
        this.game.updateGridValueAndScore(paul, 11, 0);
        this.game.updateGridValueAndScore(paul, 12, 0);
        this.game.updateGridValueAndScore(paul, 13, 0);

        this.game.checkIfPlayerHasFinished(paul);

        assertFalse(paul.isGridFinished());
    }

    @Test
    public void testGameWithoutInit() {
        Player paul = new Human("Paul");

        assertEquals(0, paul.getId());
    }

    @Test
    public void testGridWithBonusValue() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        assertEquals(35, this.game.getBonusScore(paul));
    }

    @Test
    public void testGridWithBonusValueTopScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);

        assertEquals(63 + Engine.BONUS_VALUE, this.game.getTopScore(paul));
    }

    @Test
    public void testGridWithBonusValueTotalScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 3);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);

        assertEquals(63 + Engine.BONUS_VALUE, this.game.getTotalScore(paul));
    }

    @Test
    public void testGridWithoutBonusValue() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 0, 2);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);

        assertEquals(0, this.game.getBonusScore(paul));
    }

    @Test
    public void testPlayerNameThatWillBeDisplayed() {
        Player paul = new Human("Paul");

        assertEquals("Paul", paul.getName());
    }

    @Test
    public void testPodiumFirstPlayerScore() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.updateGridValueAndScore(paul, 0, 2);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(fred, 0, 3);
        this.game.updateGridValueAndScore(fred, 1, 6);
        this.game.updateGridValueAndScore(fred, 2, 9);
        this.game.updateGridValueAndScore(fred, 3, 12);
        this.game.updateGridValueAndScore(fred, 4, 15);
        this.game.updateGridValueAndScore(fred, 5, 18);

        assertEquals(98, this.game.getPodium().get(fred));
    }

    @Test
    public void testPodiumFirstSecondScore() {
        Player paul = new Human("Paul");
        Player fred = new Human("Fred");

        this.game.init(new ArrayList<>(Arrays.asList(paul, fred)));
        this.game.updateGridValueAndScore(paul, 0, 2);
        this.game.updateGridValueAndScore(paul, 1, 6);
        this.game.updateGridValueAndScore(paul, 2, 9);
        this.game.updateGridValueAndScore(paul, 3, 12);
        this.game.updateGridValueAndScore(paul, 4, 15);
        this.game.updateGridValueAndScore(paul, 5, 18);
        this.game.updateGridValueAndScore(fred, 0, 3);
        this.game.updateGridValueAndScore(fred, 1, 6);
        this.game.updateGridValueAndScore(fred, 2, 9);
        this.game.updateGridValueAndScore(fred, 3, 12);
        this.game.updateGridValueAndScore(fred, 4, 15);
        this.game.updateGridValueAndScore(fred, 5, 18);

        assertEquals(62, this.game.getPodium().get(paul));
    }

    @Test
    public void testResetDiceList() {
        List<Dice> diceList = this.game.getDiceList();
        diceList.get(0).setKeepDice(true);
        diceList.get(1).setKeepDice(true);
        diceList.get(2).setKeepDice(true);
        diceList.get(3).setKeepDice(true);
        diceList.get(4).setKeepDice(true);
        this.game.resetDiceList();

        for (Dice dice : diceList) {
            if (dice.isKeepDice()) {
                fail("Dice is keep but shoudln't");
            }
        }
    }

    @Test
    public void testRollCount() {
        this.game.incrementAndGetRollCount();
        this.game.incrementAndGetRollCount();
        this.game.incrementAndGetRollCount();

        this.game.resetRollCount();

        assertEquals(1, this.game.incrementAndGetRollCount());
    }

    @Test
    public void testUpdateGridValueCheckTopScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 5, 6);

        assertEquals(6, this.game.getTopScore(paul));
    }

    @Test
    public void testUpdateGridValueCheckTopScoreWithoutPoints() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 6, 6);

        assertEquals(0, this.game.getTopScore(paul));
    }

    @Test
    public void testUpdateGridValueCheckTotalScore() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 6, 6);

        assertEquals(6, this.game.getTotalScore(paul));
    }

    @Test
    public void testUpdateGridValueCheckValue() {
        Player paul = new Human("Paul");

        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        this.game.updateGridValueAndScore(paul, 6, 6);

        assertEquals(6, this.game.getGridList(paul).get(6));
    }
}

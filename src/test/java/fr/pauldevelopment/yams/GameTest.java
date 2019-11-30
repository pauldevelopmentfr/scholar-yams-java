package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
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
    public void testCreateGame() {
        assertEquals(5, this.game.getDiceList().size());
    }

    @Test
    public void testGameInit() {
        Player paul = new Human("Paul");
        this.game.init(new ArrayList<>(Collections.singleton(paul)));
        assertEquals(1, paul.getId());
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

package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.game.Combo;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;

public class ComboTest {

    Game game = new Game();

    @BeforeEach
    public void init() {
        this.game.getDiceList().clear();
    }

    @Test
    public void testChanceWithRandomValues() {
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(22, combinationList.get(14));
    }

    @Test
    public void testComboConstructorIsPrivate() throws NoSuchMethodException, SecurityException {
        Constructor<Combo> constructor = Combo.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> constructor.newInstance());
    }

    @Test
    public void testTotalOfFive() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(4));
    }

    @Test
    public void testTotalOfFour() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(4, combinationList.get(3));
    }

    @Test
    public void testTotalOfOne() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(3, combinationList.get(0));
    }

    @Test
    public void testTotalOfSix() {
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(30, combinationList.get(5));
    }

    @Test
    public void testTotalOfThree() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(9, combinationList.get(2));
    }

    @Test
    public void testTotalOfTwo() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(4, combinationList.get(1));
    }
}

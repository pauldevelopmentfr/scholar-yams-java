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
    public void testChanceWithAcceptanceValue11336() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(14, combinationList.get(14));
    }

    @Test
    public void testChanceWithAcceptanceValue45561() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(21, combinationList.get(14));
    }

    @Test
    public void testComboConstructorIsPrivate() throws NoSuchMethodException, SecurityException {
        Constructor<Combo> constructor = Combo.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> constructor.newInstance());
    }

    @Test
    public void testHighestPairWithAcceptanceValues11626() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(12, combinationList.get(6));
    }

    @Test
    public void testHighestPairWithAcceptanceValues33341() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(6, combinationList.get(6));
    }

    @Test
    public void testHighestPairWithAcceptanceValues33344() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(6));
    }

    @Test
    public void testHighestPairWithoutPair() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(6));
    }

    @Test
    public void testPairWithFourOfAKind() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(6));
    }

    @Test
    public void testPairWithFull() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(10, combinationList.get(6));
    }

    @Test
    public void testPairWithThreeOfAKind() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(6, combinationList.get(6));
    }

    @Test
    public void testPairWithYams() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(2, combinationList.get(6));
    }

    @Test
    public void testTotalOfFourWithAcceptanceValues11244() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(3));
    }

    @Test
    public void testTotalOfOneWithAcceptanceValues33345() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(0));
    }

    @Test
    public void testTotalOfTwoWithAcceptanceValues23251() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(4, combinationList.get(1));
    }
}

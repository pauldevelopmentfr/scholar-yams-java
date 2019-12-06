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
        assertEquals(14, combinationList.get(Combo.CHANCE));
    }

    @Test
    public void testChanceWithAcceptanceValue45561() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(21, combinationList.get(Combo.CHANCE));
    }

    @Test
    public void testComboConstructorIsPrivate() throws NoSuchMethodException, SecurityException {
        Constructor<Combo> constructor = Combo.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> constructor.newInstance());
    }

    @Test
    public void testDoublePairWithAcceptanceValues11211() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(4, combinationList.get(Combo.DOUBLE_PAIR));
    }

    @Test
    public void testDoublePairWithAcceptanceValues11222() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(6, combinationList.get(Combo.DOUBLE_PAIR));
    }

    @Test
    public void testDoublePairWithAcceptanceValues11233() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(Combo.DOUBLE_PAIR));
    }

    @Test
    public void testDoublePairWithAcceptanceValues11234() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.DOUBLE_PAIR));
    }

    @Test
    public void testDoublePairWithYams() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(20, combinationList.get(Combo.DOUBLE_PAIR));
    }

    @Test
    public void testFourOfAKindWithAcceptanceValues22222() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(Combo.FOUR_OF_A_KIND));
    }

    @Test
    public void testFourOfAKindWithAcceptanceValues22225() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(Combo.FOUR_OF_A_KIND));
    }

    @Test
    public void testFourOfAKindWithAcceptanceValues22255() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.FOUR_OF_A_KIND));
    }

    @Test
    public void testFullHouseWithAcceptanceValues11222() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(25, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithAcceptanceValues22334() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithAcceptanceValues66666() {
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(25, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithFourOfAKind() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithPairOnly() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithReverseAcceptanceValue22211() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(25, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testFullHouseWithThreeOfAKindOnly() {
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.FULL_HOUSE));
    }

    @Test
    public void testHighestPairWithAcceptanceValues11626() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(12, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testHighestPairWithAcceptanceValues33341() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(6, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testHighestPairWithAcceptanceValues33344() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testHighestPairWithoutPair() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testLargeStraight() {
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(40, combinationList.get(Combo.LARGE_STRAIGHT));
    }

    @Test
    public void testPairWithFourOfAKind() {
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(8, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testPairWithYams() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(2, combinationList.get(Combo.PAIR));
    }

    @Test
    public void testSmallStraight() {
        this.game.getDiceList().add(new Dice(1));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(2));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(30, combinationList.get(Combo.SMALL_STRAIGHT));
    }

    @Test
    public void testThreeOfAKindWithAcceptanceValue33331() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(1));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(9, combinationList.get(Combo.THREE_OF_A_KIND));
    }

    @Test
    public void testThreeOfAKindWithAcceptanceValue33345() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(9, combinationList.get(Combo.THREE_OF_A_KIND));
    }

    @Test
    public void testThreeOfAKindWithAcceptanceValue33456() {
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(3));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.THREE_OF_A_KIND));
    }

    @Test
    public void testThreeOfAKindWithFull() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(15, combinationList.get(Combo.THREE_OF_A_KIND));
    }

    @Test
    public void testThreeOfAKindWithYams() {
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));
        this.game.getDiceList().add(new Dice(6));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(18, combinationList.get(Combo.THREE_OF_A_KIND));
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

    @Test
    public void testYamsOf5() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(50, combinationList.get(Combo.YAMS));
    }

    @Test
    public void testYamsWithoutYams() {
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(4));
        this.game.getDiceList().add(new Dice(5));
        this.game.getDiceList().add(new Dice(5));

        List<Integer> combinationList = this.game.getCombinationList();
        assertEquals(0, combinationList.get(Combo.YAMS));
    }
}

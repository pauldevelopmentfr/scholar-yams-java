package fr.pauldevelopment.yams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.pauldevelopment.yams.game.Game;

public class GameTest {

    @Test
    public void testGameInit() {
        int numberOfDices = 5;

        Game game = new Game(numberOfDices);
        assertEquals(numberOfDices, game.getDiceList().size());
    }
}

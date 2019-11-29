package fr.pauldevelopment.yams.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Dice> diceList = new ArrayList<>();

    public Game(int numberOfDice) {
        for (int i = 0; i < numberOfDice; i++) {
            this.diceList.add(new Dice());
        }
    }

    /**
     * Get the dice list
     *
     * @return the dice list
     */
    public List<Dice> getDiceList() {
        return this.diceList;
    }
}

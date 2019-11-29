package fr.pauldevelopment.yams.app;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.pauldevelopment.yams.app.gui.UserInterface;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;

public class Engine {

    private static Engine instance;
    private static final int NUMBER_OF_DICE = 5;
    private UserInterface userInterface;
    private Map<Dice, JLabel> diceList = new HashMap<>();

    private Game game;

    /**
     * Private constructor to hide the implicit public one
     */
    private Engine() {
        this.userInterface = new UserInterface(NUMBER_OF_DICE);
        this.game = new Game(NUMBER_OF_DICE);

        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            this.diceList.put(this.game.getDiceList().get(i), this.userInterface.getDiceList().get(i));
        }
    }

    /**
     * Get singleton instance
     *
     * @return engine instance
     */
    public static Engine getInstance() {
        if (instance == null) {
            return new Engine();
        }

        return instance;
    }

    /**
     * Roll a dice
     */
    public void rollDice() {
        JButton rollButton = this.userInterface.getRollButton();

        rollButton.addActionListener(e -> {
            for (Dice dice : this.game.getDiceList()) {
                dice.roll();
                this.userInterface.updateDice(this.diceList.get(dice), dice.getValue());
            }
        });
    }
}

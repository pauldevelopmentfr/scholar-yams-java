package fr.pauldevelopment.yams.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.pauldevelopment.yams.app.gui.UserInterface;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;

public class Engine {

    private static Engine instance;
    private static final int NUMBER_OF_DICE = 5;
    private static final int ROLL_LIMIT = 3;
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
     * Roll the dice list
     */
    public void rollDice() {
        JButton rollButton = this.userInterface.getRollButton();
        AtomicInteger rollCount = new AtomicInteger(0);

        rollButton.addActionListener(e -> {
            for (Dice dice : this.game.getDiceList()) {
                dice.roll();
                this.userInterface.updateDice(this.diceList.get(dice), dice.getValue());
            }

            if (rollCount.incrementAndGet() == ROLL_LIMIT) {
                rollButton.setEnabled(false);
            }
        });
    }
}

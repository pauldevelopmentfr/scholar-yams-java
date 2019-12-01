package fr.pauldevelopment.yams.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.pauldevelopment.yams.app.gui.UserInterface;
import fr.pauldevelopment.yams.exceptions.TooMuchPlayersException;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;
import fr.pauldevelopment.yams.game.Human;
import fr.pauldevelopment.yams.game.Player;

public class Engine {

    public static final int BONUS_ELIGIBILITY = 63;
    public static final int BONUS_VALUE = 35;
    public static final int NUMBER_OF_DICE = 5;
    private static Engine instance;
    private static final int ROLL_LIMIT = 3;
    private Map<Dice, JButton> diceList = new HashMap<>();
    private Game game;
    private UserInterface userInterface;

    /**
     * Private constructor to hide the implicit public one
     */
    private Engine() {
        this.userInterface = new UserInterface();
        this.game = new Game();

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
            instance = new Engine();
        }

        return instance;
    }

    /**
     * Add listener on every grid labels
     *
     * @param player
     */
    public void addGridListener(Player player) {
        for (JLabel label : this.userInterface.getGridList().get(player)) {
            label.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    label.removeMouseListener(this);
                    label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    int comboSquare = Engine.this.userInterface.getComboSquareByLatitude(label.getY());
                    int value = Integer.parseInt(label.getText().substring(0, label.getText().length() - 2).trim());

                    Engine.this.game.updateGridValueAndScore(player, comboSquare, value);
                    Engine.this.userInterface.updateGridValue(label, value);
                    Engine.this.userInterface.updateScores(player, comboSquare, value);
                    Engine.this.resetGameStatus(player);
                    Engine.this.game.resetRollCount();
                    Engine.this.changePlayer();
                    Engine.this.game.checkIfPlayerHasFinished(player);

                    if (Engine.this.game.isGameOver()) {
                        Engine.this.userInterface.createPodiumWindow(Engine.this.game.getPodium());

                        if (Engine.this.game.getRemainingHalves() > 0) {
                            Engine.this.resetEngine();
                        } else {
                            Engine.this.userInterface.getRollButton().setEnabled(false);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(Color.GRAY);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // Useless for this mouse listener
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // Useless for this mouse listener
                }
            });
        }
    }

    /**
     * Initialize game
     */
    public void initGame() {
        List<Player> playerList = new ArrayList<>(this.game.getPlayers().values());

        this.game.init(playerList);
        this.userInterface.init(playerList);
        this.init(playerList);
    }

    /**
     * Run the engine
     *
     * @throws TooMuchPlayersException
     */
    public void run() throws TooMuchPlayersException {
        if (this.game.getPlayers().size() > 4) {
            throw new TooMuchPlayersException("You can't run a game with more than 4 players");
        }

        for (Dice dice : this.game.getDiceList()) {
            JButton graphicalDiceRelated = this.diceList.get(dice);

            graphicalDiceRelated.addActionListener(e -> {
                if (dice.getValue() != 0 && graphicalDiceRelated.getCursor().getName().equals(new Cursor(Cursor.HAND_CURSOR).getName())) {
                    dice.updateKeepStatus();
                    this.userInterface.updateDiceSelection(graphicalDiceRelated);
                }
            });
        }

        JButton rollButton = this.userInterface.getRollButton();

        rollButton.addActionListener(e -> {
            for (Dice dice : this.game.getDiceList()) {
                JButton graphicalDiceRelated = this.diceList.get(dice);
                dice.roll();

                this.userInterface.updateDice(graphicalDiceRelated, dice.getValue() + ".png");
                this.userInterface.removeDiceBorder(graphicalDiceRelated);
            }

            this.getCurrentPlayerGrid();

            if (this.game.incrementAndGetRollCount() == ROLL_LIMIT) {
                rollButton.setEnabled(false);
            }
        });
    }

    /**
     * Start the engine
     */
    public void start() {
        this.game.addPlayer(new Human("Player 1"));
        this.game.addPlayer(new Human("Player 2"));
        this.game.addPlayer(new Human("Player 3"));
        this.game.addPlayer(new Human("Player 4"));
        this.game.setRemainingHalves(2);
    }

    /**
     * Change the current player
     */
    private void changePlayer() {
        this.userInterface.changeCurrentPlayer(this.game.changeCurrentPlayer());
    }

    /**
     * Get the grid of the current player
     */
    private void getCurrentPlayerGrid() {
        this.userInterface.updateGridSuggestions(this.game.getCurrentPlayer(), this.game.getCombinationList());
        this.userInterface.showGridSuggestions(this.game.getCurrentPlayer());
    }

    /**
     * Initialize engine
     *
     * @param playerList
     */
    private void init(List<Player> playerList) {
        for (Player player : playerList) {
            this.addGridListener(player);
        }

        this.userInterface.changeCurrentPlayer(this.game.getCurrentPlayer());
    }

    /**
     * Reset engine
     *
     * @throws TooMuchPlayersException
     */
    private void resetEngine() {
        this.userInterface.resetInterface();
        this.initGame();
    }

    /**
     * Reset game status
     *
     * @param player
     */
    private void resetGameStatus(Player player) {
        this.game.resetDiceList();
        this.userInterface.hideGridSuggestions(player);
        this.userInterface.resetDiceSelection();
    }
}

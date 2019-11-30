package fr.pauldevelopment.yams.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.pauldevelopment.yams.app.gui.UserInterface;
import fr.pauldevelopment.yams.exceptions.TooMuchPlayersException;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;
import fr.pauldevelopment.yams.game.Player;

public class Engine {

    public static final int BONUS_ELIGIBILITY = 63;
    public static final int BONUS_VALUE = 35;
    public static final int NUMBER_OF_DICE = 5;
    private static Engine instance;
    private static final int ROLL_LIMIT = 3;
    private Player currentPlayer;
    private Map<Dice, JButton> diceList = new HashMap<>();
    private Game game;
    private Map<Player, Boolean> playerHasFinished = new HashMap<>();
    private Map<Integer, Player> players = new HashMap<>();
    private int remainingHalves = 0;
    private AtomicInteger rollCount = new AtomicInteger(0);
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
                    Engine.this.rollCount.set(0);
                    Engine.this.changePlayer();
                    Engine.this.checkIfPlayerHasFinished(player);
                    Engine.this.checkIfGameIsOver();
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
     *
     * @param players
     * @param halves
     *
     * @throws TooMuchPlayersException
     */
    public void initGame(List<Player> players, int halves) throws TooMuchPlayersException {
        if (players.size() > 4) {
            throw new TooMuchPlayersException("You can't run a game with more than 4 players");
        }

        this.remainingHalves = halves - 1;
        this.game.init(players);

        this.userInterface.init(players);

        for (Player player : players) {
            this.players.put(player.getId(), player);
            if (this.currentPlayer == null) {
                this.currentPlayer = player;
            }
            this.userInterface.createGridValues(player);
            this.addGridListener(player);
            this.userInterface.hideGridSuggestions(player);
            this.playerHasFinished.put(player, false);
        }

        this.userInterface.changeCurrentPlayer(this.currentPlayer);
    }

    /**
     * Start the engine
     */
    public void start() {
        for (Dice dice : this.game.getDiceList()) {
            JButton graphicalDiceRelated = this.diceList.get(dice);

            graphicalDiceRelated.addActionListener(e -> {
                if (dice.getValue() != 0) {
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

            if (this.rollCount.incrementAndGet() == ROLL_LIMIT) {
                rollButton.setEnabled(false);
            }
        });
    }

    /**
     * Change the current player
     */
    private void changePlayer() {
        int currentPlayerId = this.currentPlayer.getId();
        int maxPlayerId = this.players.size();

        int id = 1;

        if (currentPlayerId != maxPlayerId) {
            id = currentPlayerId + 1;
        }

        this.currentPlayer = this.players.get(id);
        this.userInterface.changeCurrentPlayer(this.currentPlayer);
    }

    /**
     * Check if game is over
     */
    private void checkIfGameIsOver() {
        AtomicInteger isOver = new AtomicInteger(0);

        this.playerHasFinished.forEach((k, v) -> {
            if (v.booleanValue()) {
                isOver.incrementAndGet();
            }
        });

        if (isOver.get() == this.playerHasFinished.size()) {
            this.userInterface.createPodiumWindow(this.game.getPodium());

            if (this.remainingHalves > 0) {
                this.resetEngine();
            } else {
                this.userInterface.getRollButton().setEnabled(false);
            }
        }
    }

    /**
     * Check if player has finished his grid
     *
     * @param player
     */
    private void checkIfPlayerHasFinished(Player player) {
        if (!Engine.this.game.getGridList(player).contains(-1)) {
            Engine.this.playerHasFinished.put(player, true);
        }
    }

    /**
     * Get the grid of the current player
     */
    private void getCurrentPlayerGrid() {
        this.userInterface.updateGridSuggestions(this.currentPlayer, this.game.getCombinationList());
        this.userInterface.showGridSuggestions(this.currentPlayer);
    }

    /**
     * Reset engine
     *
     * @throws TooMuchPlayersException
     */
    private void resetEngine() {
        this.userInterface.resetInterface();

        try {
            this.initGame(new ArrayList<>(this.players.values()), this.remainingHalves);
        } catch (TooMuchPlayersException e) {
            e.printStackTrace();
            System.exit(1);
        }
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

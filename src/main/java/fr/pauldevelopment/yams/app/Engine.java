package fr.pauldevelopment.yams.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;

import fr.pauldevelopment.yams.app.gui.UserInterface;
import fr.pauldevelopment.yams.exceptions.TooMuchPlayersException;
import fr.pauldevelopment.yams.game.ArtificialIntelligence;
import fr.pauldevelopment.yams.game.Combo;
import fr.pauldevelopment.yams.game.Dice;
import fr.pauldevelopment.yams.game.Game;
import fr.pauldevelopment.yams.game.Player;

public class Engine {

    public static final int BONUS_ELIGIBILITY = 63;
    public static final int BONUS_VALUE = 35;
    public static final int NUMBER_OF_DICE = 5;
    private static final int BOT_REACTION_TIME = 1000;
    private static Engine instance;
    private static final int ROLL_LIMIT = 3;
    private Map<Dice, JButton> diceList = new HashMap<>();
    private Game game;
    private UserInterface userInterface;
    private boolean containsHumanPlayer = false;

    /**
     * Private constructor to hide the implicit public one
     */
    private Engine() {
        this.game = new Game();
        this.userInterface = new UserInterface();
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

                    Engine.this.updateGame(player, comboSquare, value);
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
                    label.setForeground(Color.BLACK);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setForeground(Color.GRAY);
                }
            });
        }
    }

    /**
     * Start the engine
     */
    public void start() {
        Thread gameConfiguration = new Thread() {

            @Override
            public void run() {
                Engine.this.userInterface.start();

                while (!Engine.this.userInterface.isReadyToConfigure()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Thread gameStart = new Thread() {

            @Override
            public void run() {
                try {
                    gameConfiguration.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                for (Player player : Engine.this.userInterface.getPlayersToCreate()) {
                    Engine.this.game.addPlayer(player);

                    if (!player.isComputer()) {
                        Engine.this.containsHumanPlayer = true;
                    }
                }

                Engine.this.game.setRemainingHalves(Engine.this.userInterface.getAmountOfHalvesToInit());
                Engine.this.initGame();

                try {
                    Engine.this.run();
                } catch (TooMuchPlayersException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        };

        gameConfiguration.start();
        gameStart.start();
    }

    /**
     * Make the artificial intelligence play
     *
     * @param player
     */
    private void artificialIntelligencePlay(Player player) {
        if (!this.containsHumanPlayer) {
            try {
                TimeUnit.MILLISECONDS.sleep(BOT_REACTION_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        this.rollDice();

        List<Dice> gameDiceList = this.game.getDiceList();
        List<Integer> combinationList = Combo.getCombinationList(gameDiceList);
        int maxOccurrencesDiceValue = ArtificialIntelligence.determinateDiceWithMaxOccurrences(this.game, player);
        int numberOfOccurrences = Combo.countOccurrences(gameDiceList, maxOccurrencesDiceValue);
        int combo = 0;

        if (numberOfOccurrences == 5 && ArtificialIntelligence.isSquareFree(this.game, player, Combo.YAMS)) {
            this.updateGame(player, Combo.YAMS, combinationList.get(Combo.YAMS));
            return;
        } else if (!combinationList.get(Combo.LARGE_STRAIGHT).equals(0) && ArtificialIntelligence.isSquareFree(this.game, player, Combo.LARGE_STRAIGHT)) {
            this.updateGame(player, Combo.LARGE_STRAIGHT, combinationList.get(Combo.LARGE_STRAIGHT));
            return;
        } else if (!combinationList.get(Combo.SMALL_STRAIGHT).equals(0) && ArtificialIntelligence.isSquareFree(this.game, player, Combo.SMALL_STRAIGHT)) {
            this.updateGame(player, Combo.SMALL_STRAIGHT, combinationList.get(Combo.SMALL_STRAIGHT));
            return;
        }

        ArtificialIntelligence.thinkAboutStraights(this.game, player);

        if (this.game.getRollCount() == 3) {
            combo = ArtificialIntelligence.applyDefaultStrategy(this.game, player);
            this.updateGame(player, combo, combinationList.get(combo));
            return;
        }

        if (!player.isGridFinished()) {
            this.artificialIntelligencePlay(player);
        }
    }

    /**
     * Change the current player
     */
    private void changePlayer() {
        this.userInterface.changeCurrentPlayer(this.game.changeCurrentPlayer());
    }

    /**
     * Create a cheated dice list
     */
    private void createCheatedDiceList() {
        for (Dice dice : this.game.getDiceList()) {
            dice.incrementValue();
            JButton graphicalDiceRelated = this.diceList.get(dice);
            this.userInterface.updateDice(graphicalDiceRelated, dice.getValue() + ".png");

            graphicalDiceRelated.addActionListener(e -> {
                dice.incrementValue();
                this.userInterface.updateDice(graphicalDiceRelated, dice.getValue() + ".png");
                this.getCurrentPlayerGrid();
            });
        }
    }

    /**
     * Create a normal dice list
     */
    private void createDiceList() {
        for (Dice dice : this.game.getDiceList()) {
            JButton graphicalDiceRelated = this.diceList.get(dice);

            graphicalDiceRelated.addActionListener(e -> {
                if (dice.getValue() != 0 && graphicalDiceRelated.getCursor().getName().equals(new Cursor(Cursor.HAND_CURSOR).getName())) {
                    dice.updateKeepStatus();
                    this.userInterface.updateDiceSelection(graphicalDiceRelated);
                }
            });
        }
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
            if (player.isComputer()) {
                continue;
            }

            this.addGridListener(player);
        }

        this.userInterface.changeCurrentPlayer(this.game.getCurrentPlayer());

        if (this.game.getCurrentPlayer().isComputer()) {
            this.artificialIntelligencePlay(this.game.getCurrentPlayer());
        }
    }

    /**
     * Initialize game
     */
    private void initGame() {
        List<Player> playerList = new ArrayList<>(this.game.getPlayers().values());

        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            this.diceList.put(this.game.getDiceList().get(i), this.userInterface.getDiceList().get(i));
        }

        this.game.init();
        this.userInterface.init(playerList);
        this.init(playerList);
    }

    /**
     * Reset engine
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
    }

    /**
     * Roll the dice list
     */
    private void rollDice() {
        for (Dice dice : this.game.getDiceList()) {
            JButton graphicalDiceRelated = this.diceList.get(dice);
            dice.roll();

            this.userInterface.updateDice(graphicalDiceRelated, dice.getValue() + ".png");
            this.userInterface.removeDiceBorder(graphicalDiceRelated);
        }

        this.getCurrentPlayerGrid();
        this.game.incrementRollCount();

        if (this.game.getRollCount() == ROLL_LIMIT) {
            this.userInterface.getRollButton().setEnabled(false);
        }
    }

    /**
     * Run the engine
     *
     * @throws TooMuchPlayersException
     */
    private void run() throws TooMuchPlayersException {
        if (this.game.getPlayers().size() > 4) {
            throw new TooMuchPlayersException("You can't run a game with more than 4 players");
        }

        JButton rollButton = this.userInterface.getRollButton();

        rollButton.addActionListener(e -> {
            File sound = new File("src/main/resources/sounds/dice.wav");

            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(sound.toURI().toURL());
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            } catch (Exception exception) {
                exception.getMessage();
            }

            this.rollDice();
        });

        if (this.userInterface.getCheatModeStatus()) {
            rollButton.setEnabled(false);
            this.createCheatedDiceList();
            this.getCurrentPlayerGrid();
        } else {
            this.createDiceList();
        }
    }

    /**
     * Update game
     *
     * @param player
     * @param comboSquare
     * @param value
     */
    private void updateGame(Player player, int comboSquare, int value) {
        JLabel label = this.userInterface.getGridList().get(player).get(comboSquare);

        if (label.getMouseListeners().length > 0) {
            label.removeMouseListener(label.getMouseListeners()[0]);
            label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        this.game.updateGridValueAndScore(player, comboSquare, value);
        this.userInterface.updateGridValue(label, value);
        this.userInterface.updateScores(player, comboSquare, value);
        this.resetGameStatus(player);
        this.game.resetRollCount();
        this.changePlayer();
        this.game.checkIfPlayerHasFinished(player);

        if (this.game.isGameOver()) {
            this.userInterface.createPodiumWindow(this.game.getPodium(), this.game.getRemainingHalves());

            if (this.game.getRemainingHalves() > 0) {
                this.resetEngine();
            }
        } else {
            if (this.game.getCurrentPlayer().isComputer()) {
                this.artificialIntelligencePlay(this.game.getCurrentPlayer());
            }
        }

        if (this.userInterface.getCheatModeStatus()) {
            this.getCurrentPlayerGrid();
        } else {
            this.userInterface.resetDiceSelection();
        }

        if (this.game.isGameOver() && this.game.getRemainingHalves() == 0) {
            this.userInterface.getRollButton().setEnabled(false);
        }
    }
}

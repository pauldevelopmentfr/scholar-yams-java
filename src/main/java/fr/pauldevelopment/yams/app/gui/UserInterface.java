package fr.pauldevelopment.yams.app.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import fr.pauldevelopment.yams.app.Engine;
import fr.pauldevelopment.yams.game.Player;

public class UserInterface {

    private static final int BOTTOM_SIZE = 9;
    private static final int MARGIN_LEFT = 48;
    private static final int SPACE_BETWEEN_DICE = 75;
    private static final int TOP_SIZE = 6;
    private static final int UNDER_GRID_Y = 486;

    private JLabel bonusScore;
    private JLabel bottomScore;
    private JLabel diceContainer;
    private List<JButton> diceList;
    private JLabel grid;
    private Map<Player, List<JLabel>> gridList = new HashMap<>();
    private JPanel panel = new CustomPanel();
    private JButton rollButton;
    private JLabel topScore;
    private JLabel totalScore;
    private JFrame window = new JFrame();

    /**
     * UserInterface constructor
     */
    public UserInterface() {
        this.window.setTitle("Yams!");
        this.window.setSize(810, 610);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setResizable(false);
        this.window.setIconImage(this.getIconImage());
        this.window.setVisible(true);
        this.window.add(this.panel);
        this.panel.setLayout(null);

        this.diceContainer = this.createDiceContainer();
        this.diceList = this.createDiceList();
        this.addDiceListToContainer();
        this.rollButton = this.createRollButton();

        this.grid = this.createGrid();

        this.addElementsToPanel();
    }

    /**
     * Get the combo square using y coordinate
     *
     * @param y the lattitude
     *
     * @return the combo square
     */
    public int getComboSquareByLatitude(int y) {
        int square = 1;

        if (y > 138) {
            square += 2;
        }

        return y / 23 - square;
    }

    /**
     * Get the dice list
     *
     * @return the dice list
     */
    public List<JButton> getDiceList() {
        return this.diceList;
    }

    /**
     * Get the grid list
     *
     * @return the grid list
     */
    public Map<Player, List<JLabel>> getGridList() {
        return this.gridList;
    }

    /**
     * Get the roll button
     *
     * @return the roll button
     */
    public JButton getRollButton() {
        return this.rollButton;
    }

    /**
     * Hide grid suggestions
     *
     * @param player
     */
    public void hideGridSuggestions(Player player) {
        for (JLabel label : this.gridList.get(player)) {
            if (label.getForeground().equals(Color.BLACK)) {
                continue;
            }

            label.setVisible(false);
        }
    }

    /**
     * Initialize game
     *
     * @param players
     */
    public void init(List<Player> players) {
        for (Player player : players) {
            JLabel playerName = this.createLabelOnGrid(player.getName(), player.getId(), 0);
            playerName.setForeground(Color.BLUE);
            this.grid.add(playerName);
            this.gridList.put(player, new ArrayList<>());
        }
    }

    /**
     * Remove dice border
     *
     * @param dice
     */
    public void removeDiceBorder(JButton dice) {
        dice.setBorderPainted(false);
    }

    /**
     * Reset dice selection
     */
    public void resetDiceSelection() {
        for (JButton dice : this.diceList) {
            this.updateDice(dice, "default.png");
            this.removeDiceBorder(dice);
        }

        this.rollButton.setEnabled(true);
        this.updatePanel();
    }

    /**
     * Show grid suggestions
     *
     * @param player
     */
    public void showGridSuggestions(Player player) {
        for (JLabel label : this.gridList.get(player)) {
            if (label.getForeground().equals(Color.BLACK)) {
                continue;
            }

            label.setVisible(true);
        }
    }

    /**
     * Update a dice
     *
     * @param dice
     * @param fileName
     */
    public void updateDice(JButton dice, String fileName) {
        try {
            dice.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/dice/" + fileName))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add/remove a border when a dice is selected/unselected
     *
     * @param dice
     */
    public void updateDiceSelection(JButton dice) {
        dice.setBorderPainted(!dice.isBorderPainted());
    }

    /**
     * Update grid value
     *
     * @param label
     * @param value
     */
    public void updateGridValue(JLabel label, int value) {
        label.setText(Integer.toString(value));
        label.setForeground(Color.BLACK);
        this.updatePanel();
    }

    /**
     * Update grid with scores depending on dice list
     *
     * @param player
     */
    public void updateGridValues(Player player) {
        if (this.gridList.get(player).isEmpty()) {
            this.createGridValues(player);
        }

        this.updatePanel();
    }

    /**
     * Update scores
     *
     * @param player
     * @param comboSquare
     * @param value
     */
    public void updateScores(Player player, int comboSquare, int value) {
        if (this.topScore == null && this.bottomScore == null) {
            this.createScores(player);
        }

        if (comboSquare < 6) {
            int score = Integer.parseInt(this.topScore.getText()) + value;
            this.topScore.setText(Integer.toString(score));
        } else {
            int score = Integer.parseInt(this.bottomScore.getText()) + value;
            this.bottomScore.setText(Integer.toString(score));
        }

        int score = Integer.parseInt(this.totalScore.getText()) + value;
        this.totalScore.setText(Integer.toString(score));

        this.updatePanel();
    }

    /**
     * Add each dice to the container
     */
    private void addDiceListToContainer() {
        for (JButton dice : this.diceList) {
            this.diceContainer.add(dice);
        }
    }

    /**
     * Add elements to panel
     */
    private void addElementsToPanel() {
        this.panel.add(this.grid);
        this.panel.add(this.rollButton);
        this.panel.add(this.diceContainer);
        this.updatePanel();
    }

    /**
     * Create a dice button
     *
     * @param fileName
     * @param x
     * @param y
     *
     * @return the dice button created
     */
    private JButton createDiceButton(String fileName, int x, int y) {
        JButton dice = new JButton();

        try {
            BufferedImage diceImage = ImageIO.read(new File("src/main/resources/" + fileName));

            dice = new JButton(new ImageIcon(diceImage));

            dice.setBorder(new LineBorder(Color.RED, 3));
            dice.setBorderPainted(false);
            dice.setContentAreaFilled(false);
            dice.setSize(diceImage.getWidth(), diceImage.getHeight());
            dice.setLocation(x, y);
            dice.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dice;
    }

    /**
     * Create dice container
     *
     * @return the dice container created
     */
    private JLabel createDiceContainer() {
        return this.createElement("container.png", MARGIN_LEFT, UNDER_GRID_Y);
    }

    /**
     * Create dice list
     *
     * @return the dice list
     */
    private List<JButton> createDiceList() {
        List<JButton> buttonList = new ArrayList<>();
        int variableMargin = 35;

        for (int i = 0; i < Engine.NUMBER_OF_DICE; i++) {
            buttonList.add(this.createDiceButton("dice/default.png", variableMargin, 10));
            variableMargin += UserInterface.SPACE_BETWEEN_DICE;
        }

        return buttonList;
    }

    /**
     * Create an element
     *
     * @param fileName
     * @param x
     * @param y
     *
     * @return the element created
     */
    private JLabel createElement(String fileName, int x, int y) {
        JLabel label = new JLabel();

        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/" + fileName));

            label = new JLabel(new ImageIcon(image));
            label.setSize(image.getWidth(), image.getHeight());
            label.setLocation(x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return label;
    }

    /**
     * Create the grid
     *
     * @return the grid created
     */
    private JLabel createGrid() {
        return this.createElement("grid.png", MARGIN_LEFT, 10);
    }

    /**
     * Create grid values
     *
     * @param player
     */
    private void createGridValues(Player player) {
        List<JLabel> labelList = new ArrayList<>();

        for (int i = 0; i < TOP_SIZE; i++) {
            JLabel label = this.createLabelOnGrid("0 ?", 1, i + 1);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            labelList.add(label);
            this.grid.add(label);
        }

        for (int i = 0; i < BOTTOM_SIZE; i++) {
            JLabel label = this.createLabelOnGrid("0 ?", 1, TOP_SIZE + 3 + i);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            labelList.add(label);
            this.grid.add(label);
        }

        this.gridList.put(player, labelList);
    }

    /**
     * Create a label on grid
     *
     * @param text
     * @param gridPositionX
     * @param gridPositionY
     *
     * @return the label created
     */
    private JLabel createLabelOnGrid(String text, int gridPositionX, int gridPositionY) {
        int x = 153 * gridPositionX + 136 * (gridPositionX - 1);
        int y = 23 * gridPositionY;

        JLabel label = new JLabel(text);
        label.setLocation(x + 10, y);
        label.setSize(135, 22);
        label.setForeground(Color.GRAY);

        return label;
    }

    /**
     * Create the roll button
     *
     * @return the roll button
     */
    private JButton createRollButton() {
        JButton button = new JButton();

        try {
            BufferedImage rollButtonImage = ImageIO.read(new File("src/main/resources/roll.png"));
            button.setIcon(new ImageIcon(rollButtonImage));
            button.setSize(rollButtonImage.getWidth(), rollButtonImage.getHeight());
            button.setLocation(MARGIN_LEFT + 497, UNDER_GRID_Y);
        } catch (IOException e) {
            e.printStackTrace();
        }

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    /**
     * Create scores
     *
     * @param player
     */
    private void createScores(Player player) {
        this.topScore = this.createLabelOnGrid("0", player.getId(), 8);
        this.bonusScore = this.createLabelOnGrid("0", player.getId(), 7);
        this.bottomScore = this.createLabelOnGrid("0", player.getId(), 18);
        this.totalScore = this.createLabelOnGrid("0", player.getId(), 19);
        this.topScore.setForeground(Color.BLACK);
        this.bonusScore.setForeground(Color.BLACK);
        this.bottomScore.setForeground(Color.BLACK);
        this.totalScore.setForeground(Color.BLACK);
        this.grid.add(this.topScore);
        this.grid.add(this.bonusScore);
        this.grid.add(this.bottomScore);
        this.grid.add(this.totalScore);
    }

    /**
     * Get icon image
     *
     * @return icon image
     */
    private Image getIconImage() {
        try {
            return ImageIO.read(new File("src/main/resources/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Update panel
     */
    private void updatePanel() {
        this.panel.revalidate();
        this.panel.repaint();
    }
}

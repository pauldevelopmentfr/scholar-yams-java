package fr.pauldevelopment.yams.app.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class UserInterface {

    private static final int MARGIN_TOP = 486;
    private static final int MARGIN_LEFT = 48;
    private static final int SPACE_BETWEEN_DICE = 75;
    private JFrame window = new JFrame();
    private JPanel panel = new CustomPanel();
    private List<JButton> diceList = new ArrayList<>();
    private JButton rollButton;

    /**
     * UserInterface constructor
     */
    public UserInterface(int numberOfDice) {
        this.window.setTitle("Yams!");
        this.window.setSize(810, 610);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setResizable(false);
        this.window.setIconImage(this.getIconImage());
        this.window.setVisible(true);

        this.window.add(this.panel);
        this.panel.setLayout(null);

        this.createRollButton();
        this.createDiceList(numberOfDice);
        this.createDiceContainer();
        this.panel.revalidate();
        this.panel.repaint();
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
     * Get the roll button
     *
     * @return the roll button
     */
    public JButton getRollButton() {
        return this.rollButton;
    }

    /**
     * Reset dice selection
     *
     * @param dice
     */
    public void resetDiceSelection(JButton dice) {
        dice.setBorderPainted(false);
    }

    /**
     * Update a dice
     *
     * @param dice to update
     */
    public void updateDice(JButton dice, int diceValue) {
        try {
            dice.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/dice/" + diceValue + ".png"))));

            Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

            if (!dice.getCursor().getName().equals(handCursor.getName())) {
                dice.setCursor(handCursor);
            }
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
     * Create dice container
     */
    private void createDiceContainer() {
        try {
            BufferedImage diceContainerImage = ImageIO.read(new File("src/main/resources/container.png"));
            JLabel diceContainer = new JLabel(new ImageIcon(diceContainerImage));
            diceContainer.setSize(diceContainerImage.getWidth(), diceContainerImage.getHeight());
            diceContainer.setLocation(MARGIN_LEFT, MARGIN_TOP);
            this.panel.add(diceContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create dice list
     */
    private void createDiceList(int numberOfDice) {
        try {
            BufferedImage diceImage = ImageIO.read(new File("src/main/resources/dice/default.png"));
            int variableMargin = 35;

            for (int i = 0; i < numberOfDice; i++) {
                JButton dice = new JButton(new ImageIcon(diceImage));

                dice.setBorder(new LineBorder(Color.RED, 3));
                dice.setBorderPainted(false);
                dice.setContentAreaFilled(false);
                dice.setSize(diceImage.getWidth(), diceImage.getHeight());
                dice.setLocation(MARGIN_LEFT + variableMargin, MARGIN_TOP + 10);

                this.diceList.add(dice);
                this.panel.add(dice);
                variableMargin += UserInterface.SPACE_BETWEEN_DICE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create roll button
     */
    private void createRollButton() {
        this.rollButton = new JButton();

        try {
            Image rollButtonImage = ImageIO.read(new File("src/main/resources/roll.png"));
            this.rollButton.setIcon(new ImageIcon(rollButtonImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.rollButton.setBounds(545, 486, 200, 75);
        this.rollButton.setOpaque(false);
        this.rollButton.setContentAreaFilled(false);
        this.rollButton.setBorderPainted(false);
        this.rollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.panel.add(this.rollButton);
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
}

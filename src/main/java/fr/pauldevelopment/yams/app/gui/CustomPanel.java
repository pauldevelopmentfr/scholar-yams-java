package fr.pauldevelopment.yams.app.gui;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    public void paintComponent(Graphics graphics) {
        try {
            graphics.drawImage(ImageIO.read(new File("src/main/resources/background.png")), 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

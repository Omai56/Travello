package ui;

import javax.swing.*;
import java.awt.*;

// Represents a panel with a background image.
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // EFFECTS: constructs a background panel that displays the image at path.
    public BackgroundPanel(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
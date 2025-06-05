package spriteframework.sprite;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import javax.swing.ImageIcon;



public class ScaleImage extends JPanel {

    private Image scaledImage;
    private ImageIcon resizedIcon;

    public ScaleImage(String localizacao, int width, int height){
        ImageIcon ii = new ImageIcon(this.getClass().getResource(localizacao));
        scaledImage = ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(scaledImage);
    }

    public ImageIcon getScaledImage() {
        return resizedIcon;
    }


    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(scaledImage,50, 50, this);
    }
}
package spriteframework.sprite;

import javax.swing.ImageIcon;

import spriteframework.Commons;

import java.awt.Image;
import java.awt.event.KeyEvent;


public class Player extends Sprite {

    private int width;
    private int height;
    private String direction;
    private MovStrategy movStrategy;
    private Commons config;

    public Player(String localizacao, int larg, int alt, Commons config){
        loadImage(localizacao, larg, alt);
		getImageDimensions();
        this.config = config;
        resetState();
        movStrategy = new HorizontalMoviment();
    }

    protected void loadImage (String localizacao, int larg, int alt) {

        ScaleImage scaledImage = new ScaleImage(localizacao,  larg, alt);
        ImageIcon ii = scaledImage.getScaledImage();
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        setImage(ii.getImage());
    }
    
    public void act() {

        x += dx;
        y += dy;

        if (x <= 2) x = 2;

        if (y <= 2) y = 2;


        if (x >= config.getBoardWidth()- config.getBorderRight() - (width/2)) {
            x = config.getBoardWidth() - config.getBorderRight() - (width/2);
        }

        if (y >= config.getBoardHeight() - config.getBorderBotton() - height) {
            y = config.getBoardHeight() - config.getBorderBotton() - height;
        }

    }

    public void keyPressed(KeyEvent e) {

        movStrategy.updatePosition(this, e);


    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String dir) { this.direction = dir;}

    public void setMovStrategy(MovStrategy movStrategy) {
        this.movStrategy = movStrategy;
    }

    public void setConfig(Commons config){this.config = config;}

    private void resetState() {

        setX(config.getInitPlayerX() );
        setY(config.getInitPlayerY());
    }
}

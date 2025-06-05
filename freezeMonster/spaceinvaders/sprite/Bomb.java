package spaceinvaders.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.ScaleImage;

public class Bomb extends BadSprite {

    private boolean destroyed;

    public Bomb(int x, int y) {

        initBomb(x, y);
    }

    private void initBomb(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;
        //
        ScaleImage scaledImage = new ScaleImage("/images/bomb.png",  2, 6);
        ImageIcon ii = scaledImage.getScaledImage();
        //

        //String bombImg = "images/bomb.png";
        //ImageIcon ii = new ImageIcon(bombImg);
        setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }
    
    
}

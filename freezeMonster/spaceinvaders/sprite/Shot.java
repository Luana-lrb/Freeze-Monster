package spaceinvaders.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.ScaleImage;


public class Shot extends BadSprite {

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {
        //
        ScaleImage scaledImage = new ScaleImage("/images/shot.png",  1, 5);
        ImageIcon ii = scaledImage.getScaledImage();
        //
       // String shotImg = "images/shot.png";
        //ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}

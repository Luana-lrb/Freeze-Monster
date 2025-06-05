package spaceinvaders.sprite;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import spriteframework.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.ScaleImage;

public class  BomberSprite extends BadnessBoxSprite {

    private Bomb bomb;

    public BomberSprite(String path, int x, int y, int larg, int alt) {

        initBomber(path,x, y, larg, alt);
    }

    private void initBomber(String path, int x, int y , int larg, int alt) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        ScaleImage scaledImage = new ScaleImage(path,  larg, alt);
        ImageIcon ii = scaledImage.getScaledImage();

        setImage(ii.getImage());
    }




    public Bomb getBomb() {

        return bomb;
    }


	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(bomb);
		return aBomb;
	}


}

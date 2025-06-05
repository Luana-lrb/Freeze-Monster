package freezeMonster.sprite;

import javax.swing.ImageIcon;

import freezeMonster.EngineCommons;
import spriteframework.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.ScaleImage;

import java.util.Random;

public class Slime extends BadSprite {

    private boolean destroyed;
    private int direction;
    private final Commons config = EngineCommons.getNewInstance();

    public Slime(String loc, int x, int y, int larg, int alt) {

        initSlime(loc,x, y, larg, alt);
    }

    private void initSlime(String loc, int x, int y, int larg, int alt) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        ScaleImage scaledImage = new ScaleImage(loc, larg, alt);
        ImageIcon ii = scaledImage.getScaledImage();
        setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    public void setDirection(){
        direction = new Random().nextInt(4);// 0 - left,  1- right, 2 - up, 3 down
    }

    public void move(){
        int velocity = config.getAttackVelocity();
        if(direction == 0){
            setX(getX()-velocity);
        } else if(direction == 1){
            setX(getX()+velocity);
        } else if(direction == 2){
            setY(getY()-velocity);
        } else {
            setY(getY()+velocity);
        }
        outBoard();
    }

    public void outBoard(){
        if ( getY() >= config.getGround() ) { // chão
            setDestroyed(true);
        } else if (getY() <= config.getTop()){
            setDestroyed(true);
        } else if (getX() >= config.getBoardWidth() - config.getAttackHeight()) {
            setDestroyed(true);
        } else if (getX() <= config.getBorderLeft()) {
            setDestroyed(true);
        }
    }
    
    
}

package freezeMonster.sprite;

import java.util.LinkedList;

import javax.swing.*;
import java.util.Random;

import freezeMonster.EngineCommons;
import spriteframework.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.ScaleImage;

/// é o alien
public class MonsterSprite extends BadnessBoxSprite {

    private Slime slime;
    private String froozenPath;
    private int larg, alt;
    private boolean isFrozen = false;
    private int directionX, directionY;
    private final Commons config = EngineCommons.getNewInstance();
    Random rand = new Random();



    // cada alien tem SUA bomba que quando é destruída pode ser reutilizada,
    // por isso lança uma única por vez

    public MonsterSprite(String primaryPath, int x, int y, int larg, int alt) {

        initMonster(primaryPath, x, y,larg, alt);
    }

    private void initMonster(String path, int x, int y, int larg, int alt) {

        this.x = x;
        this.y = y;
        this.larg = larg;
        this.alt = alt;

        slime = new Slime("/images/gosma.png",x, y, 25, 25);

        ScaleImage scaledImage = new ScaleImage(path,  larg, alt);
        ImageIcon ii = scaledImage.getScaledImage();

        setImage(ii.getImage());
        setRandomDirection();
    }

    public void setFroozenPath(String path) {
        froozenPath = path;
    }

    public Slime getSlime() {
        return slime;
    }


    public void froozen(){
        ScaleImage scaledImage = new ScaleImage(froozenPath, larg , alt);
        ImageIcon ii = scaledImage.getScaledImage();
        setImage(ii.getImage());
        slime.die(); //slime.setDestroyed(true);
        isFrozen = true;
    }

	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aSlime = new LinkedList<BadSprite>();
		aSlime.add(slime);
		return aSlime;
	}

    public boolean isFrozen(){
        return isFrozen;
    }

       public void setRandomDirection(){
        if(isFrozen) return;

        do{
            directionX = ( rand.nextInt(3) - 1 ) * config.getMonsterVelocity();
            directionY = ( rand.nextInt(3) - 1 ) * config.getMonsterVelocity();
        } while(directionX == 0 && directionY == 0);

    }

    public void initMovimentation(){
        if(isFrozen) return;
        checkColisionAndMove(directionX, directionY);
    }

    private void checkColisionAndMove(int dX, int dY){

        int newX = getX() + dX;
        int newY = getY() + dY;

        // Verifica se a nova posição está dentro dos limites
        boolean noLimitesXright = newX >= config.getBoardWidth() - config.getBorderRight() - config.getBad_sprite_width()/2;
        boolean noLimiteXleft = newX <= config.getBorderLeft();
        boolean noLimitesYup = newY <= config.getTop();
        boolean noLimitesYdown = newY >= config.getGround();


        if (noLimitesXright ){
            moveX(-4);
            setRandomDirection();
        }else if(noLimiteXleft){
            moveX(4);
            setRandomDirection();
        }else if(noLimitesYdown){
            moveY(-4);
            setRandomDirection();
        }else if(noLimitesYup){
            moveY(4);
            setRandomDirection();
        }

        /*if (noLimitesY){
            setRandomDirection();
        }*/

        moveX(dX);
        moveY(dY);

    }
}

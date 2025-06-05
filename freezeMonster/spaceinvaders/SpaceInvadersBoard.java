package spaceinvaders;


import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.HorizontalMoviment;
import spriteframework.sprite.Player;
import spriteframework.Commons;
import spaceinvaders.sprite.*;

public class SpaceInvadersBoard extends AbstractBoard{  

    private Shot shot;    
    
    // define global control vars   
    private int direction = -1;
    private int deaths = 0;

    private String explImg = "images/explosion.png";

    private final Commons config = SpaceCommons.getNewInstance();

    SpaceInvadersBoard(Commons config){
        super(config);

    }

    protected void setPlayerMovementStrategy(Player p) {
        p.setMovStrategy(new HorizontalMoviment());
    }

    protected Dimension setDimensions(){
        return new Dimension(358,350);
    }
    @Override
    protected Player createPlayer(Commons config) {
        return new Player("/images/naveplayer.png", 15, 10, config);
    }

    protected void createBadSprites(Commons config) {  // create sprites
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                BomberSprite alien = new BomberSprite("/images/alien.png",config.get_Bad_sprite_init_x() + 18 * j,
                        config.get_Bad_sprite_init_y() + 18 * i,20,20);
                badSprites.add(alien);
            }
        }
    }
    
    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    // Override
    protected void drawOtherSprites(Graphics g) {
            drawShot(g);
    }
    
    protected void processOtherSprites(Player player, KeyEvent e) {
		int x = player.getX();
		int y = player.getY();

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {

			if (inGame) {

				if (!shot.isVisible()) {

					shot = new Shot(x, y);
				}
			}
		}
	}


    protected void update() {

        if (deaths == config.getNumber_bad_sprites_to_destoy()) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        for (Player player: players) 
        	player.act();


        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite alien : badSprites) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + config.getBad_sprite_width())
                            && shotY >= (alienY)
                            && shotY <= (alienY + config.getBad_sprite_height())) {

                        String explGif = "/images/miniExplosion.gif";
                        ImageIcon ii = new ImageIcon(getClass().getResource(explGif));
                        alien.setImage(ii.getImage());
                        deaths++;
                        shot.die();

                        new javax.swing.Timer(450, e -> {
                            ((Timer)e.getSource()).stop();
                            alien.setDying(true);
                        }).start();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens
        for (BadSprite alien : badSprites) {

            int x = alien.getX();

            if (x >= config.getBoardWidth() - config.getBorderRight() && direction != -1) {

                direction = -1;

                Iterator<BadSprite> i1 = badSprites.iterator();

                while (i1.hasNext()) {
                    BadSprite a2 = i1.next();
                    a2.setY(a2.getY() + config.get_Go_down());
                }
            }

            if (x <= config.getBorderLeft() && direction != 1) {

                direction = 1;

                Iterator<BadSprite> i2 = badSprites.iterator();

                while (i2.hasNext()) {

                    BadSprite a = i2.next();
                    a.setY(a.getY() + config.get_Go_down());
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > config.getGround() - config.getBad_sprite_height()) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.moveX(direction);
            }
        }

        // bombs
        
        updateOtherSprites();
    }

	

    
    protected void updateOtherSprites() {
		Random generator = new Random();

        for (BadSprite alien : badSprites) {

            int shot = generator.nextInt(15);
            Bomb bomb = ((BomberSprite)alien).getBomb();

            if (shot == config.getChance() && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + config.getPlayerWidth())
                        && bombY >= (playerY)
                        && bombY <= (playerY + config.getPlayerHeight())){

                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {
                int velocity = config.getAttackVelocity();
                bomb.setY(bomb.getY() + velocity);

                if (bomb.getY() >= config.getGround() - config.getAttackHeight() ) {

                    bomb.setDestroyed(true);
                }
            }
        }
	}    
}


package freezeMonster;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.FourMoviment;
import spriteframework.sprite.Player;
import freezeMonster.sprite.*;
import spriteframework.Commons;
import spriteframework.sprite.ScaleImage;

public class FreezeMonsterBoard extends AbstractBoard{
    //define sprites
    //private List<BadSprite> monsters;
    private Shot shot;    
    
    // define global control vars   
    private int direction = -1;
    private int deaths = 0;
    private Commons configCommons;

    private String explImg = "images/explosion.png";
    private String woodyBeaten = "/images/woodypreso.png";

    private Random generator = new Random();

    FreezeMonsterBoard(Commons config){
        super(config);
        this.configCommons = config;
    }

    protected void setPlayerMovementStrategy(Player player) {
        player.setMovStrategy(new FourMoviment());
    }

    protected void createBadSprites(Commons config) {  // create sprites

        int maxX = config.getBoardWidth();
        int maxY = config.getBoardHeight()-150;

        int monster_y_initial, monster_x_initial, num = 1;
        String primaryPath, froozenPath;

        Random rand = new Random();
        for(int i = 1; i < config.getNumber_bad_sprites_to_destoy() + 1 ; i++){
            do{
                monster_x_initial = rand.nextInt((maxX) + 1);
                monster_y_initial = rand.nextInt((maxY) + 1);
            } while(monster_x_initial > config.getInitPlayerX() - 30 &&
                monster_x_initial < config.getInitPlayerX() + 30 ||
                monster_y_initial > config.getInitPlayerY() - 30 &&
                monster_y_initial < config.getInitPlayerY() + 30
            );

            num = i % 10;
            if(num ==0) num = 1;
            primaryPath = "/images/monster" + num + ".png";
            froozenPath = "/images/monster" + num + "bg.png";
            MonsterSprite monster = new MonsterSprite(primaryPath,monster_x_initial,
                    monster_y_initial, 40, 40);
            monster.setFroozenPath(froozenPath);
            badSprites.add(monster);
        }

    }

    protected Dimension setDimensions(){
        return new Dimension(700,600);
    }
    
    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    @Override
    protected Player createPlayer(Commons config) { // falta fazer o mesmo no spaceinvaders
        return new Player("/images/woody.png", 50,70, config);
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

					shot = new Shot("/images/ray.png", x, y,25,25 );
                    shot.setDirection(player.getDirection());
				}
			}
		}
	}

    protected void update() {

        if (deaths == configCommons.getNumber_bad_sprites_to_destoy()) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        for (Player player: players) 
        	player.act();



        /// Tiro do woody com os monstros
        if (shot.isVisible()) {

            shot.updatePosition();

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite monster : badSprites) {

                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if ((!((MonsterSprite) monster).isFrozen()) && shot.isVisible()) {
                    if (shotX >= (monsterX)
                            && shotX <= (monsterX + configCommons.getBad_sprite_width())
                            && shotY >= (monsterY)
                            && shotY <= (monsterY + configCommons.getBad_sprite_height())) {

                        ((MonsterSprite)monster).froozen();
                        deaths++;
                        shot.die();
                    }
                }

                Slime s = (((MonsterSprite) monster).getSlime() );
                if(!s.isDestroyed() && shot.isVisible()){

                    int slimeX = s.getX();
                    int slimeY = s.getY();
                    int slimeBorder = config.getAttackHeight()/2;

                    if (shotX >= (slimeX - slimeBorder )
                            && shotX <= (slimeX + slimeBorder)
                            && shotY >= (slimeY - slimeBorder)
                            && shotY <= (slimeY + slimeBorder)) {

                        s.setDestroyed(true);
                        shot.die();
                        break;
                    }
                }

            }

        }

        for (BadSprite monster : badSprites) {

            ((MonsterSprite)monster).initMovimentation();
        }

        // slimes
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
        for (BadSprite monster : badSprites) {

            Slime slime = ((MonsterSprite)monster).getSlime(); // pega o slime de cada monstro
            if (!slime.isDestroyed()) {
                slime.move();
            }

            if (((MonsterSprite) monster).isFrozen()) continue;

            int shot = generator.nextInt(50);

            if (shot == config.getChance() && (!((MonsterSprite) monster).isFrozen()) && slime.isDestroyed()) {

                slime.setDestroyed(false);
                slime.setDirection();
                slime.setX(monster.getX());
                slime.setY(monster.getY());
            }

            int slimeX = slime.getX();
            int slimeY = slime.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !slime.isDestroyed()) {

                if (slimeX >= (playerX)
                        && slimeX <= (playerX + configCommons.getPlayerWidth())
                        && slimeY >= (playerY)
                        && slimeY <= (playerY + configCommons.getPlayerWidth())){

                    ScaleImage scaledImage = new ScaleImage(woodyBeaten,  70, 70);
                    ImageIcon ii = scaledImage.getScaledImage();
                    players.get(0).setImage(ii.getImage());

                    new javax.swing.Timer(400, e -> {
                        ((Timer)e.getSource()).stop();
                        players.get(0).setDying(true);
                    }).start();

                    slime.setDestroyed(true);
                }
            }

            if (!slime.isDestroyed()) {
                slime.move();
            }
        }
	}
}


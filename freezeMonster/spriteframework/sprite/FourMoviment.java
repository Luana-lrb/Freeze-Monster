package spriteframework.sprite;

import java.awt.event.KeyEvent;

public class FourMoviment implements MovStrategy {

    public FourMoviment(){
    }
    @Override
    public void updatePosition(Player player, KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            player.setDirection("left");
            player.dx = -4;
        }

        if (key == KeyEvent.VK_RIGHT) {
            player.setDirection("right");
            player.dx = 4;
        }

        if (key == KeyEvent.VK_UP) {
            player.setDirection("up");
            player.dy = -4;
        }

        if (key == KeyEvent.VK_DOWN) {
            player.setDirection( "down");
            player.dy = 4;
        }
    }

}

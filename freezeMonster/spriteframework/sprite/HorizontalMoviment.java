package spriteframework.sprite;
import java.awt.event.KeyEvent;

public class HorizontalMoviment implements MovStrategy {

    @Override
    public void updatePosition(Player player, KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            player.setDirection("left");
            player.dx = - 3;
        }

        if (key == KeyEvent.VK_RIGHT) {
            player.setDirection("right");
            player.dx = 3;
        }


    }
}

package spriteframework.sprite;

import java.awt.event.KeyEvent;

public interface MovStrategy {
    void updatePosition(Player player, KeyEvent e);
}

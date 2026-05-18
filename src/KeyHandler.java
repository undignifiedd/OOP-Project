import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upKeyPressed, downKeyPressed, rightKeyPressed, leftKeyPressed, spaceBarPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upKeyPressed = true;
        }  else if (code == KeyEvent.VK_A) {
            leftKeyPressed = true;
        }  else if (code == KeyEvent.VK_S) {
            downKeyPressed = true;
        }  else if (code == KeyEvent.VK_D) {
            rightKeyPressed = true;
        }  else if (code == KeyEvent.VK_SPACE) {
            spaceBarPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upKeyPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftKeyPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downKeyPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightKeyPressed = false;
        } else if (code == KeyEvent.VK_SPACE) {
            spaceBarPressed = false;
        }
    }

}

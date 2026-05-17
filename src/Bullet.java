import java.awt.*;
import javax.swing.ImageIcon;

public class Bullet {
    int x, y;
    Image img;
    boolean isVisible;

    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
        ImageIcon newBullet = new ImageIcon("/player/bullet.png");
        img = newBullet.getImage();
        isVisible = true;
    }

    public void move() {
        x += 2;
        if (x > 100) { // to keep the bullet visible in the frame
            isVisible = false;
        }
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet {
    int x, y;
    Image img;
    int speed;
    int width;
    int height;
    public BufferedImage bulletImg;

    public Bullet(int startX, int startY, BufferedImage bulletImg2) {
        this.x = startX;
        this.y = startY;
        this.speed = 7;
        this.width = 16;
        this.height = 16;

        try {
            bulletImg = ImageIO.read(getClass().getResourceAsStream("/player/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics2D g2) {
        if (bulletImg != null) {
            g2.drawImage(bulletImg, x, y, width, height, null);
        }
    }
}

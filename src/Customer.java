import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Customer extends Entity implements GameObject {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    private int shotCounter = 0;
    private static int shotInterval = 90; // 90 frames
    private static ArrayList<Bullet> bullets = new ArrayList<>();

    public Customer(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setCustomerDefault();
        getCustomerImage();
    }

    public void getCustomerImage() {

        try {
            customer = ImageIO.read(getClass().getResourceAsStream("/player/customer_one.png"));
            gunFiring = ImageIO.read(getClass().getResourceAsStream("/player/gun_firing.png"));
            bulletImg = ImageIO.read(getClass().getResourceAsStream("/player/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerDefault() {
        x = 500;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void fire() {
        Bullet z = new Bullet(x - 10, y + 30, bulletImg); // x and y for where the bullet comes out of a gun
        bullets.add(z);
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void update() {
        if (direction.equals("down")) {
            y += speed;
        } else if (direction.equals("up")) {
            y -= speed;
        }

        // boundary check
        if (y >= 400) {
            direction = "up";
        } else if (y <= 100) {
            direction = "down";
        }

        shotCounter++;
        if (shotCounter >= shotInterval) {
            fire();
            shotCounter = 0; // Reset timer
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update();

            if (b.x < 0) {
                bullets.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = customer; // Default fallback image

        image = gunFiring;

        if (image != null) {
            g2.drawImage(image, x, y, 48, 96, null);
        }

        for (Bullet b : bullets) {
            b.draw(g2);
        }
    }
}

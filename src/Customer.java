import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Customer extends Entity implements GameObject {
    KeyHandler keyHandler;

    private int shotCounter = 0;
    private final static int shotInterval = 45; // 90 frames
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private GamePanel gamePanel;
    private int batchNo;

    public Customer(GamePanel gamePanel, KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        batchNo=1;
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
        x = 680;
        y = 229;
        speed = 0;
        direction = "down";
    }

    public void fire() throws InterruptedException {
        int randomX = (int)(Math.random()*30) +50;
        int randomY = (int) (Math.random()*50) + 1;
        Bullet z = new Bullet(randomX*10, randomY*10, bulletImg);
        bullets.add(z);
        if (bullets.size() >= batchNo * 10) {
            for (Bullet bullet : bullets) {
                bullet.move();
            }
        }
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

        if (y >= 500) {
            direction = "up";
        } else if (y <= 10) {
            direction = "down";
        }

        shotCounter++;
        try {
            if (shotCounter + 20 >= shotInterval) {
                fire();
                shotCounter = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        BufferedImage image = customer;
        image = gunFiring;

        if (image != null) {
            g2.drawImage(image, x, y, 60, 110, null);
        }

        for (Bullet b : bullets) {
            b.draw(g2);
        }
    }
}
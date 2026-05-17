import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Customer extends Entity implements GameObject {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Customer(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setCustomerDefault();
        getCustomerImage();
    }

    public void getCustomerImage() {

        try {
            customer = ImageIO.read(getClass().getResourceAsStream("/player/customer_one.png"));
            gunHolding = ImageIO.read(getClass().getResourceAsStream("/player/gun_holding.png"));
            gunFiring = ImageIO.read(getClass().getResourceAsStream("/player/gun_firing.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerDefault() {
        x = 100;
        y = 100;
        speed = 0;
    }

    static ArrayList<Bullet> bullets;

    public void fire() {
        Bullet z = new Bullet((150 + 60), (172 + 154 / 2)); // x and y for where the bullet comes out of a gun
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
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = gunHolding;
                break;
            case "down":
                image = gunFiring;
                break;
            default:
                break;
        }
        if (image != null) {
            g2.drawImage(image, x, y, 48, 96, null);
        }
    }
}

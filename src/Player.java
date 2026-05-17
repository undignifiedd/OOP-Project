import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity implements GameObject {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
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

    public void getPlayerImage() {

        try {
            chef_right = ImageIO.read(getClass().getResourceAsStream("/player/cheffaceright.png"));
            chef_kick_right = ImageIO.read(getClass().getResourceAsStream("/player/chefkickright.png"));
            chef_left = ImageIO.read(getClass().getResourceAsStream("/player/cheffaceleft.png"));
            chef_kick_left = ImageIO.read(getClass().getResourceAsStream("/player/chefkickleft.png"));
            chef_up = ImageIO.read(getClass().getResourceAsStream("/player/chefback.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "left";
    }

    public void update() {
        if (keyHandler.upKeyPressed == true) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.downKeyPressed == true) {
            direction = "down";
            y += speed;
        } else if (keyHandler.leftKeyPressed == true) {
            direction = "left";
            x -= speed;
        } else if (keyHandler.rightKeyPressed == true) {
            System.out.println(chef_right.getWidth() + " " + chef_right.getHeight());
            direction = "right";
            x += speed;
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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = chef_up;
                break;
            case "down":
                image = chef_right;
                break;
            case "left":
                image = chef_left;
                break;
            case "right":
                image = chef_right;
                break;
            default:
                break;
        }
        if (image != null) {
            g2.drawImage(image, x, y, 48, 96, null);
        }
    }
}

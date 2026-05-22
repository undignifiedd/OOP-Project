import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity implements GameObject {
    KeyHandler keyHandler;
    private StateManager stateManager;
    private int health = 100;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;

        stateManager= StateManager.getInstance();

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            chef_right = ImageIO.read(getClass().getResourceAsStream("player/cheffaceright.png"));
            chef_kick_right = ImageIO.read(getClass().getResourceAsStream("player/chefkickright.png"));
            chef_left = ImageIO.read(getClass().getResourceAsStream("player/cheffaceleft.png"));
            chef_kick_left = ImageIO.read(getClass().getResourceAsStream("player/chefkickleft.png"));
            chef_up = ImageIO.read(getClass().getResourceAsStream("player/chefback.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 50;
        y = 229;
        speed = 4;
        direction = "left";
    }

    public void update() {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x>226){   //226
            x = 226;
        }
        if (y>495){  //495
            y= 495;
        }
        if (keyHandler.upKeyPressed) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.downKeyPressed) {
            direction = "down";
            y += speed;
        } else if (keyHandler.leftKeyPressed) {
            direction = "left";
            x -= speed;
        } else if (keyHandler.rightKeyPressed) {
            direction = "right";
            x += speed;
        }
        if (health<=0){
            stateManager.setState(3);
        }
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
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString(Integer.toString(health),x+5,y-3);
        if (image != null) {
            g2.drawImage(image, x, y, 48, 96, null);
        }
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health= health;
    }
}

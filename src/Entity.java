import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int x;
    protected int y;
    protected int health = 100;
    int speed;

    public BufferedImage chef_right, chef_kick_right, chef_left, chef_kick_left, chef_up;
    public BufferedImage customer, gunFiring, bulletImg, belt;
    public BufferedImage conveyor_belt;
    public BufferedImage batter, frosting, toppings;
    public BufferedImage chef, pan;

    public String direction;

    public void setHealth(int damage){
        health-=damage;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 48,96);
    }
}

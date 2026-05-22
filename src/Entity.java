import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int x;
    protected int y;
    int speed;

    public BufferedImage chef_right, chef_kick_right, chef_left, chef_kick_left, chef_up;
    public BufferedImage customer, gunFiring, bulletImg, belt;

    public BufferedImage conveyor_belt;
    public BufferedImage batter, frosting, toppings;
    public BufferedImage chef, pan;

    public BufferedImage panEmptyImg, chocoBatter, vanillaBatter, strawberryBatter;
    public BufferedImage choco, strawberry, vanilla;
    public BufferedImage smiley, candy, clover;

    public String direction;

    public Rectangle getBounds() {
        return new Rectangle(x, y, 48,96);
    }
}

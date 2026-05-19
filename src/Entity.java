import java.awt.image.BufferedImage;

public class Entity {
    protected int x;
    protected int y;
    protected int health;
    int speed;

    public BufferedImage chef_right, chef_kick_right, chef_left, chef_kick_left, chef_up;
    public BufferedImage customer, gunFiring, bulletImg, belt;
    public BufferedImage conveyor_belt;

    public String direction;
}

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet implements GameObject{
    private int x, y;
    private int speed;
    private int width;
    private int height;
    private boolean active;
    public BufferedImage bulletImg;
    public static final int bulletDamage = 10;

    public Bullet(int startX, int startY, BufferedImage bulletImg2) {
        this.x = startX;
        this.y = startY;
        this.speed = 0;
        this.width = 30;
        this.height = 25;
        active =true;

        try {
            bulletImg = ImageIO.read(getClass().getResourceAsStream("/player/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (active) {
            x -= speed;
        }
    }

    public void draw(Graphics2D g2) {
        if (active) {
            if (bulletImg != null) {
                g2.drawImage(bulletImg, x, y, width, height, null);
            }
        }
    }

    public void move() {
        speed = 10;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width-10,height-15);
    }
    public void setActive(boolean activation){
        active= activation;
    }
    public boolean getActive(){
        return active;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
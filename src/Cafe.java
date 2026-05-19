import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Cafe extends Entity implements GameObject {

    public Cafe (GamePanel gamePanel) {

        getMachineImages();
    }

    public void getMachineImages() {
        try {
            batter = ImageIO.read(getClass().getResourceAsStream("Cake/batter.png"));
            frosting = ImageIO.read(getClass().getResourceAsStream("Cake/frosting.png"));
            toppings = ImageIO.read(getClass().getResourceAsStream("Cake/toppings.png"));
            chef = ImageIO.read(getClass().getResourceAsStream("Cake/chef.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update() {
        // Any physics, floating animations, or logic updates go here
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(batter, 0, 0, 150, 200, null);
        g2.drawImage(frosting, 200, 0, 170, 200, null);
        g2.drawImage(toppings, 400, 0, 150, 200, null);
        g2.drawImage(chef,600, 310, 205, 275, null);
    }
}

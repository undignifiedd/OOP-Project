import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ingredient extends Entity implements GameObject {
    private String name, type;
    private boolean falling = false;
    private int beltY = 320;
    private BufferedImage image;

    public Ingredient(String name, String type) {
        this.name = name;
        this.type = type;
        this.speed = 2;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void loadImage() {
        try {
            String path = "Ingredients/" + name.toLowerCase() + type.charAt(0) + type.substring(1).toLowerCase() + ".png";
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startFalling(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.falling = true;
    }

    public void update() {
        if (falling) {
            y += speed;
            if (y >= beltY) {
                falling = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (falling && image != null) {
            g2.drawImage(image, x, y - 80, 50, 80, null);
        }
    }

    public boolean isFalling() {
        return falling;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}

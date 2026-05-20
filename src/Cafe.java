import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Cafe extends Entity implements GameObject {

    private int x, y;
    private int targetX;
    private final int slideSpeed = 4;
    private BufferedImage currentBatterImg = null;
    private BufferedImage currentFrostingImg = null;
    private BufferedImage currentToppingImg = null;

    public Cafe (GamePanel gamePanel) {

        getMachineImages();
    }

    public Cafe(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX; // Starts standing still at the first slot

        // Ensure you load your empty pan asset here as well
    }

    public void getMachineImages() {
        try {
            batter = ImageIO.read(getClass().getResourceAsStream("Cake/batter.png"));
            frosting = ImageIO.read(getClass().getResourceAsStream("Cake/frosting.png"));
            toppings = ImageIO.read(getClass().getResourceAsStream("Cake/toppings.png"));
            chef = ImageIO.read(getClass().getResourceAsStream("Cake/chef.png"));
            pan = ImageIO.read(getClass().getResourceAsStream("Cake/pan.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addIngredient(Ingredient ingredient) {
        String type = ingredient.getType().toLowerCase();

        switch (type) {
            case "batter" -> {
                if (this.currentBatterImg == null) { // Only accept if empty
                    this.currentBatterImg = ingredient.getImage();
                    this.targetX = 215; // Move pan forward to the Icing Station!
                    System.out.println("Batter added! Moving to Icing Station...");
                }
            }
            case "icing", "frosting" -> {
                // Must have a batter base, and must not have icing yet
                if (this.currentBatterImg != null && this.currentFrostingImg == null) {
                    this.currentFrostingImg = ingredient.getImage();
                    this.targetX = 410; // Move pan forward to the Toppings Station!
                    System.out.println("Icing added! Moving to Toppings Station...");
                }
            }
            case "topping" -> {
                // Must have icing, and must not have toppings yet
                if (this.currentFrostingImg != null && this.currentToppingImg == null) {
                    this.currentToppingImg = ingredient.getImage();
                    // Optional: targetX = 600; // Move forward to a serving/delivery area!
                    System.out.println("Topping added! Cake Assembly Complete!");
                }
            }
        }
    }

    @Override
    public void update() {
        // Smoothly animate the pan towards its target position on the assembly line
        if (this.x < this.targetX) {
            this.x += slideSpeed;
            if (this.x > targetX) this.x = targetX; // Prevent overshooting
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(batter, 5, 0, 150, 200, null);
        g2.drawImage(frosting, 205, 0, 170, 200, null);
        g2.drawImage(toppings, 405, 0, 150, 200, null);
        g2.drawImage(chef,600, 350, 175, 235, null);
        // g2.drawImage(pan, 5, 200, 150, 200, null);
        if (pan != null) {
            g2.drawImage(pan, 5, 200, 150, 200, null);
            // g2.drawImage(pan, this.x, this.y, 150, 200, null);
        } else {
            // Backup placeholder if panImg isn't initialized yet
            g2.setColor(Color.GRAY);
            g2.fillRect(this.x, this.y, 150, 20);
        }

        // 2. Draw the loaded components layered relative to the pan's moving X coordinate!
        if (currentBatterImg != null) {
            g2.drawImage(currentBatterImg, this.x + 15, this.y + 40, 120, 80, null);
        }

        if (currentFrostingImg != null) {
            g2.drawImage(currentFrostingImg, this.x + 25, this.y + 30, 100, 40, null);
        }

        if (currentToppingImg != null) {
            g2.drawImage(currentToppingImg, this.x + 55, this.y + 15, 40, 30, null);
        }
    }
}

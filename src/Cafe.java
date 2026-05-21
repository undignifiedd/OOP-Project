import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Cafe extends Entity implements GameObject {

    private int x, y;
    private int targetX;
    private final int speed = 2;

    // stage 0: empty pan, 1: batter, 2: icing, 3: toppings
    private int stage = 0;
    private String flavor = "";
    private String icing = "";
    private String topping = "";

    private BufferedImage currentImage;

    public Cafe (GamePanel gamePanel) {

        getMachineImages();
    }

    public Cafe (int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        loadImages();
        currentImage = panEmptyImg; // Start empty
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

    private void loadImages() {
        try {
            panEmptyImg = ImageIO.read(getClass().getResourceAsStream("Cake/pan.png"));
            // batter images
            chocoBatter = ImageIO.read(getClass().getResourceAsStream("Cake/chocolate_batter.png"));
            vanillaBatter = ImageIO.read(getClass().getResourceAsStream("Cake/vanilla_batter.png"));
            strawberryBatter = ImageIO.read(getClass().getResourceAsStream("Cake/strawberry_batter.png"));

            // icing images
            choco = ImageIO.read(getClass().getResourceAsStream("Ingredients/chocolateIcing.png"));
            strawberry = ImageIO.read(getClass().getResourceAsStream("Ingredients/strawberryIcing.png"));
            vanilla = ImageIO.read(getClass().getResourceAsStream("Ingredients/vanillaIcing.png"));

            // toppings images
            smiley = ImageIO.read(getClass().getResourceAsStream("Ingredients/smileyTopping.png"));
            candy = ImageIO.read(getClass().getResourceAsStream("Ingredients/candyTopping.png"));
            clover = ImageIO.read(getClass().getResourceAsStream("Ingredients/cloverTopping.png"));

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addBatter(String flavor) {
        if (stage == 0) {
            this.flavor = flavor;
            this.stage = 1;
            if (flavor.equals("Chocolate")) {
                currentImage = chocoBatter;
            } else if (flavor.equals("Strawberry")) {
                currentImage = strawberryBatter;
            } else if (flavor.equals("Vanilla")) {
                currentImage = vanillaBatter;
            }
            // Move forward to the Icing station
            this.targetX = this.x + 200;
        }
    }

    public void addIcing(String icingType) {
        if (stage == 1) {
            this.icing = icingType;
            this.stage = 2;
            this.targetX = this.x + 200; // Move forward to toppings station
        }
    }

    public void addTargetTopping(String toppingType) {
        if (stage == 2) {
            this.topping = toppingType;
            this.stage = 3;
            this.targetX = this.x + 200;
        }
    }

    @Override
    public void update() {
        if (x < targetX) {
            x += speed;
            if (x > targetX) x = targetX;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(batter, 5, 0, 150, 200, null);
        g2.drawImage(frosting, 205, 0, 170, 200, null);
        g2.drawImage(toppings, 405, 0, 150, 200, null);
        g2.drawImage(chef, 600, 350, 175, 235, null);
        // g2.drawImage(pan, 5, 200, 150, 200, null);

        if (currentImage != null) {
            g2.drawImage(currentImage, x, y, 150, 200, null);
        }

        if (stage >= 2) {
            if (icing.equals("Chocolate")) {
                g2.drawImage(choco, x + 27, y + 20, 110, 100, null);
            } else if (icing.equals("Strawberry")) {
                g2.drawImage(strawberry, x + 27, y + 20, 110, 100, null);
            } else if (icing.equals("Vanilla")) {
                g2.drawImage(vanilla, x + 27, y + 20, 110, 100, null);
            }
        }

        if (stage >= 3) {
            if (topping.equals("Candy")) {
                g2.drawImage(candy, x + 50, y + 30, 60, 60, null);
            } else if (topping.equals("Clover")) {
                g2.drawImage(clover, x + 50, y + 30, 60, 60, null);
            } else if (topping.equals("Smiley")) {
                g2.drawImage(smiley, x + 50, y + 30, 60, 60, null);
            }
        }
    }

    public int getX() { return x; }
    public int getStage() { return stage; }

}


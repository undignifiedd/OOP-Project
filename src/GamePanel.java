import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements Runnable {
    private static final int originalTileSize = 16; // actual size of each object
    private static final int scale = 3; // main canvas scaled
    private static final int tileSize = originalTileSize * scale; // displayed tile size
    private static final int maxScreenCol = 16;
    private static final int maxScreenRow = 12;
    private static final int screenWidth = tileSize * maxScreenCol; // screen dimension x
    private static final int screenHeight = tileSize * maxScreenRow; // screen dimension y
    private static final int FPS = 60;
    private final int bossFightTimer = 60 * 60;
    private int currentTime = 0;

    private static ArrayList<GameObject> cafeObjects;
    private static java.util.concurrent.CopyOnWriteArrayList<GameObject> bossFightObjects;
    private Thread gameThread;

    private BufferedImage menuBackground, cafeBackground, bossFightBackground;
    private BufferedImage targetBackground;
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;

    private StateManager stateManager;
    private Player player;
    private Customer customer;
    private KeyHandler keyHandler;
    private ConveyerBelt conveyerBelt;
    private Cafe cafe;

    private java.util.concurrent.CopyOnWriteArrayList<CafeButton> cafeButtons;

    Rectangle startButton = new Rectangle(310, 130, 150, 60);
    Rectangle replayButton = new Rectangle(290, 210, 200, 40);
    Rectangle brownRectangle = new Rectangle(310, 270, 150, 100);
    private boolean startButtonPressed = false;
    private boolean replayButtonPressed = false;
    private int rainSpawnCounter = 0; // COUNTER FOR RAIN SPAWN TIME IN BOSSFIGHT
    private int dodgeTextCounter = 180;

    private Cafe activeCafe;

    public GamePanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        cafeObjects = new ArrayList<>();
        bossFightObjects = new CopyOnWriteArrayList<>();
        cafeButtons = new CopyOnWriteArrayList<>();

        this.stateManager = StateManager.getInstance();

        keyHandler = new KeyHandler();
        conveyerBelt = new ConveyerBelt(keyHandler);
        cafeObjects.add(conveyerBelt);
        cafe = new Cafe(this);
        cafeObjects.add(cafe);

        player = new Player(keyHandler);
        bossFightObjects.add(player);
        customer = new Customer(this);
        bossFightObjects.add(customer);

        addKeyListener(keyHandler);
        int btnWidth = 145;
        int btnHeight = 60;
        int startX = 20;
        int startY = 378;
        int padding = 45;


        activeCafe = new Cafe(5, 200);
        cafeObjects.add(activeCafe);

        String[] sayings = {
                "Chocolate", "Chocolate", "Candy",
                "Strawberry", "Strawberry", "Smiley",
                "Vanilla", "Vanilla", "Clover"
        };

        Runnable[] functions = {
                () -> {
                    Ingredient ing = new Ingredient("Chocolate", "Batter");
                    ing.loadImage();
                    ing.startFalling(55, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("Chocolate", "Icing");
                    ing.loadImage();
                    ing.startFalling(265, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("candy", "Topping");
                    ing.loadImage();
                    ing.startFalling(460, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("Strawberry", "Batter");
                    ing.loadImage();
                    ing.startFalling(55, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("Strawberry", "Icing");
                    ing.loadImage();
                    ing.startFalling(265, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("smiley", "Topping");
                    ing.loadImage();
                    ing.startFalling(460, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("Vanilla", "Batter");
                    ing.loadImage();
                    ing.startFalling(55, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("Vanilla", "Icing");
                    ing.loadImage();
                    ing.startFalling(265, 250);
                    cafeObjects.add(ing);
                },
                () -> {
                    Ingredient ing = new Ingredient("clover", "Topping");
                    ing.loadImage();
                    ing.startFalling(460, 250);
                    cafeObjects.add(ing);
                }
        };

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            int x = startX + col * (btnWidth + padding);
            int y = startY + row * (btnHeight + 5);
            cafeButtons.add(new CafeButton(sayings[i], x, y, btnWidth, btnHeight, functions[i]));
        }

        // single mouse listener for everything
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (stateManager.getState() == 0) {
                    if (startButton.contains(e.getPoint())) {
                        startButtonPressed = true;
                        repaint();
                    }
                } else if (stateManager.getState() == 1) {
                    for (CafeButton button : cafeButtons) {
                        if (button.getBounds().contains(e.getPoint())) {
                            button.setPressed(true);
                            repaint();
                            break;
                        }
                    }
                } else if (stateManager.getState() == 3) {
                    if (replayButton.contains(e.getPoint())) {
                        replayButtonPressed = true;
                        repaint();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (stateManager.getState() == 0) {
                    if (startButton.contains(e.getPoint()) && startButtonPressed) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException x) {
                            x.printStackTrace();
                        }
                        stateManager.setState(1);
                    }
                } else if (stateManager.getState() == 1) {
                    for (CafeButton button : cafeButtons) {
                        if (button.getBounds().contains(e.getPoint())) {
                            button.setPressed(false);
                            button.triggerAction();
                            repaint();
                            break;
                        }
                    }
                } else if (stateManager.getState() == 3) {
                    if (replayButton.contains(e.getPoint()) && replayButtonPressed) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException x) {
                            x.printStackTrace();
                        }
                        stateManager.setState(0);
                    }
                }
                startButtonPressed = false;
                replayButtonPressed = false;
                repaint();
            }
        });

        try {
            menuBackground = ImageIO.read(getClass().getResourceAsStream("Backgrounds/Menu Background.jpeg"));
            cafeBackground = ImageIO.read(getClass().getResourceAsStream("Cake/pastel.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("Ingredients/candyTopping.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("Ingredients/smileyTopping.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("Ingredients/cloverTopping.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startThread() { // starting thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) { // game loop
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (stateManager.getFading()) {
            stateManager.setFadeAlpha(stateManager.getFadeAlpha() - .02f);
        }
        if (stateManager.getFadeAlpha() <= 0) {
            stateManager.setFadeAlpha(0);
            stateManager.setFading(false);
        }
        if (stateManager.getState() == 0) {
            targetBackground = menuBackground;
        } else if (stateManager.getState() == 1) {
            targetBackground = cafeBackground;
            for (GameObject object : cafeObjects) {
                object.update();
            }



        } else if (stateManager.getState() == 2) {
            rainSpawnCounter++;  //SPAWNING RAIN
            if (rainSpawnCounter >= 2) {
                rainSpawnCounter = 0;
                bossFightObjects.add(new Rain());
            }
            targetBackground = bossFightBackground;
            for (int i = 0; i < bossFightObjects.size(); i++) {
                GameObject obj = bossFightObjects.get(i);
                if (obj instanceof Rain rain && rain.getY() > 600) {
                    bossFightObjects.remove(i);
                    i--;
                }
                if (obj instanceof Bullet b && b.getActive()) {
                    if (b.getBounds().intersects(player.getBounds())) {
                        player.setHealth(Bullet.bulletDamage);
                        b.setActive(false);
                    }
                }
                obj.update();
            }
            currentTime++;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (targetBackground != null) {
            g2.drawImage(targetBackground, 0, 0, getWidth(), getHeight(), this);
        }

        if (stateManager.getState() == 0) {
            // main button
            g2.setColor(startButtonPressed ? new Color(100, 65, 30) : new Color(139, 90, 43)); // brownish wooden color
            g2.fillRoundRect(startButton.x, startButton.y, startButton.getSize().width, startButton.getSize().height,
                    20, 20);
            // borders
            g2.setColor(Color.darkGray);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(startButton.x, startButton.y, startButton.getSize().width, startButton.getSize().height,
                    20, 20);
            // text
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 25));
            g2.drawString("START", startButton.x + 34, startButton.y + 35);
        }

        if (stateManager.getState() == 1) {
            for (GameObject object : cafeObjects) {
                if (object != null) {
                    object.draw(g2);
                }
            }
            for (CafeButton button : cafeButtons) {
                button.draw(g2);
            }
        } else if (stateManager.getState() == 2) {
            if (dodgeTextCounter > 0) {
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 50));
                g2.drawString("DODGE INCOMING", 150, 281);
                g2.drawString("BULLETS", 245, 341);
                dodgeTextCounter--;
            }
            if (currentTime < bossFightTimer) {
                g2.setColor(Color.GREEN);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.drawString("Time Left: " + Integer.toString((bossFightTimer - currentTime) / 60), 550, 30);

                for (GameObject object : bossFightObjects) {
                    object.draw(g2);
                }
            } else {
                stateManager.setState(3);
            }
        } else if (stateManager.getState() == 3) {
            //Game over text
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("GAME OVER", 235, 190);

            //Personality text
            g2.setColor(Color.ORANGE);
            g2.setFont(new Font("Italic", Font.ITALIC, 30));
            g2.drawString("The instructions we gave were simple enough😕", 50, 130);

            //Cake representation
            g2.setColor(new Color(71, 50, 7));
            g2.draw(brownRectangle);
            g2.fill(brownRectangle);

            //Text under brown rectangle

            g2.setColor(Color.ORANGE);
            g2.setFont(new Font("Italic", Font.ITALIC, 30));
            g2.drawString("This is the closest we could get to visualize the", 50, (int) (brownRectangle.getY() + brownRectangle.getHeight() + 40));
            g2.drawString("mess you made", 280, (int) (brownRectangle.getY() + brownRectangle.getHeight()) + 70);

            BufferedImage [] images = new BufferedImage[] {image1,image2,image3};
                for (int i = 0; i < 10; i++) {
                    double randomX = brownRectangle.x + Math.random() * brownRectangle.width;
                    double randomY = brownRectangle.y + Math.random() * brownRectangle.height;
                    g2.drawImage(images[(i % 3)], (int) randomX, (int) randomY, 30, 30, null);
                }

            g2.setColor(replayButtonPressed ? new Color(100, 65, 30) : new Color(117, 32, 32)); // crimson red color
            g2.fillRoundRect(replayButton.x, replayButton.y, replayButton.getSize().width, replayButton.getSize().height,
                    20, 20);
            // borders
            g2.setColor(Color.darkGray);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(replayButton.x, replayButton.y, replayButton.getSize().width, replayButton.getSize().height,
                    20, 20);

            //text
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.drawString("REPLAY", replayButton.x + 58, replayButton.y + 28);
        }
        if (stateManager.getFading()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, stateManager.getFadeAlpha()));
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // reset
        }

        g2.dispose();
    }


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public void addGameObjectInCafe(GameObject object) {
        cafeObjects.add(object);
    }

    public static void addGameObjectInBossFight(GameObject object) {
        bossFightObjects.add(object);
    }
}

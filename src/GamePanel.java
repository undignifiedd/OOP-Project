import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    private static ArrayList<GameObject> cafeObjects;
    private static java.util.concurrent.CopyOnWriteArrayList<GameObject> bossFightObjects;
    private Thread gameThread;

    private BufferedImage menuBackground, cafeBackground, bossFightBackground;
    private BufferedImage targetBackground;

    private StateManager stateManager;
    private Player player;
    private Customer customer;
    private KeyHandler keyHandler;
    private ConveyerBelt conveyerBelt;
    private Cafe cafe;

    private java.util.concurrent.CopyOnWriteArrayList<CafeButton> cafeButtons;

    Rectangle startButton = new Rectangle(310, 130, 150, 60);
    private boolean buttonPressed = false;
    private int rainSpawnCounter = 0; // COUNTER FOR RAIN SPAWN TIME IN BOSSFIGHT
    private int dodgeTextCounter = 180;

    public GamePanel() {
        this.setLayout(null);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // setting base configuration for GamePanel
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        cafeObjects = new ArrayList<>(); // instantiating arraylists
        bossFightObjects = new CopyOnWriteArrayList<>();

        this.stateManager = StateManager.getInstance(); // calling stateManager instance

        keyHandler = new KeyHandler();// needed to take keyInput
        conveyerBelt = new ConveyerBelt(keyHandler);
        cafeObjects.add(conveyerBelt);
        cafe = new Cafe(this);
        cafeObjects.add(cafe);

        player = new Player( keyHandler); // player instantiation
        bossFightObjects.add(player);
        customer = new Customer(this); // customer instantiation
        bossFightObjects.add(customer);

        addKeyListener(keyHandler); // needed for input. keyHandler handles input through KeyEvent. keyEvent needs
                                    // KeyListener

        // mouseListener to track when we click
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (stateManager.getState() == 0) {
                    if (startButton.contains(e.getPoint())) {
                        buttonPressed = true;
                        repaint();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (stateManager.getState() == 0) {
                    if (startButton.contains(e.getPoint()) && buttonPressed) {
                        try {
                            Thread.sleep(10);
                        }
                        catch (InterruptedException x){
                            x.printStackTrace();
                        }
                        stateManager.setState(1);
                    }
                }
                buttonPressed = false;
                repaint();
            }
        });

        // setting background pictures
        try {
            menuBackground = ImageIO.read(getClass().getResourceAsStream("Backgrounds/Menu Background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // setting cafe background
        try {
            cafeBackground = ImageIO.read(getClass().getResourceAsStream("Cake/pastel.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cafeButtons = new java.util.concurrent.CopyOnWriteArrayList<>();
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
                    System.out.println("Bullet: " + b.getX() + "," + b.getY() + " Player: " + player.x + "," + player.y);
                    if (b.getBounds().intersects(player.getBounds())) {
                        player.setHealth(Bullet.bulletDamage);
                        b.setActive(false);
                    }
                }
                if (obj instanceof Bullet b){
                    System.out.println("Bullet: " + b.getActive() + "POSITION : {" + b.getX() + b.getY() +"}");
                }
                obj.update();
            }
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
            g2.setColor(buttonPressed ? new Color(100, 65, 30) : new Color(139, 90, 43)); // brownish wooden color
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


        int btnWidth = 145;
        int btnHeight = 60;
        int startX = 20;
        int startY = 378;
        int padding = 45; // Gap between buttons

        String[] sayings = {
                "Chocolate", "Chocolate", "Candy",
                "Strawberry", "Strawberry", "Smiley",
                "Vanilla", "Vanilla", "Clover"
        };

        Runnable[] functions = {
                () -> { System.out.println("Mixing the cake batter..."); },
                () -> { System.out.println("Cake placed in the oven!"); },
                () -> { System.out.println("Applying sweet frosting..."); },
                () -> { System.out.println("Sprinkles added cleanly."); },
                () -> { System.out.println("Topping cake with fresh berries."); },
                () -> { System.out.println("Slicing the cake into portions."); },
                () -> { System.out.println("Order served! Earned cash."); },
                () -> { System.out.println("Cleared the workstation counter."); },
                () -> { System.out.println("Oven upgraded! Baking speed increased."); }
        };

        for (int i = 0; i < 9; i++) {
            int row = i / 3; // Row indices: 0, 1, 2
            int col = i % 3; // Column indices: 0, 1, 2

            int x = startX + col * (btnWidth + padding);
            int y = startY + row * (btnHeight + 5);

            cafeButtons.add(new CafeButton(sayings[i], x, y, btnWidth, btnHeight, functions[i]));
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
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (stateManager.getState() == 1) {
                        for (CafeButton button : cafeButtons) {
                            if (button.getBounds().contains(e.getPoint())) {
                                button.setPressed(true);
                                repaint();
                                break;
                            }
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (stateManager.getState() == 1) {
                        for (CafeButton button : cafeButtons) {
                            if (button.getBounds().contains(e.getPoint())) {
                                button.setPressed(false);

                                button.triggerAction();

                                repaint();
                                break;
                            }
                        }
                    }
                }
            });
        } else if (stateManager.getState() == 2) {
           if (dodgeTextCounter>0) {
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 50));
                g2.drawString("DODGE INCOMING", 150, 281);
                g2.drawString ("BULLETS",245,341);
                dodgeTextCounter--;
            }
            for (GameObject object : bossFightObjects) {
                object.draw(g2);
            }
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

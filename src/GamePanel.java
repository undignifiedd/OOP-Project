import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private static final int originalTileSize = 16; // actual size of each object
    private static final int scale = 3; // main canvas scaled
    private static final int tileSize = originalTileSize * scale; // displayed tile size
    private static final int maxScreenCol = 16;
    private static final int maxScreenRow = 12;
    private static final int screenWidth = tileSize * maxScreenCol; // screen dimension x
    private static final int screenHeight = tileSize * maxScreenRow; // screen dimension y
    private static final int FPS = 60;

    private ArrayList<GameObject> cafeObjects;
    private ArrayList<GameObject> bossFightObjects;
    private Thread gameThread;

    private BufferedImage menuBackground, cafeBackground, bossFightBackground;
    private BufferedImage targetBackground;

    private StateManager stateManager;
    private Player player;
    private KeyHandler keyHandler;

    Rectangle startButton = new Rectangle(315,150,150,50);

    public GamePanel() {
        this.setLayout(null);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //setting base configuration for GamePanel
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        cafeObjects = new ArrayList<>();   // instantiating arraylists
        bossFightObjects = new ArrayList<>();

        this.stateManager = StateManager.getInstance(); // calling stateManager instance

        keyHandler = new KeyHandler();  // needed to take keyInput

        player = new Player(this, keyHandler);  // player instantiation
        bossFightObjects.add(player);

        addKeyListener(keyHandler); // needed for input. keyHandler handles input through KeyEvent. keyEvent needs KeyListener

        //mouseListener to track when we click
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (stateManager.getState() == 0) {
                    if (startButton.contains(e.getPoint())) {
                        stateManager.setState(1);
                    }
                }
            }
        });

        // setting background pictures
        try {
            menuBackground = ImageIO.read(getClass().getResourceAsStream("Backgrounds/Menu Background.jpeg"));
        }
        catch (IOException e){
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
        if (stateManager.getState() == 0) {
            targetBackground = menuBackground;
        } else if (stateManager.getState() == 1) {
            targetBackground = cafeBackground;
            for (GameObject object : cafeObjects) {
                object.update();
            }
        } else if (stateManager.getState() == 2) {
            targetBackground = bossFightBackground;
            for (GameObject object : bossFightObjects) {
                object.update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(targetBackground,0,0,null);

        if (stateManager.getState()== 0){
            // main button
            g2.setColor(new Color(139, 90, 43)); // brownish wooden color
            g2.fillRoundRect(startButton.x,startButton.y,startButton.getSize().width, startButton.getSize().height,20,20);
            //borders
            g2.setColor(Color.darkGray);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(startButton.x,startButton.y,startButton.getSize().width,startButton.getSize().height,20,20);
            //text
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial",Font.BOLD,20));
            g2.drawString("START",startButton.x+40,startButton.y+30);
        }

        if (stateManager.getState() == 1) {
            for (GameObject object : cafeObjects) {
                object.draw(g2);
            }
        } else if (stateManager.getState() == 2) {
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

    public void addGameObjectInBossFight(GameObject object) {
        bossFightObjects.add(object);
    }
}

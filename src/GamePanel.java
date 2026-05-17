import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    private static final int originalTileSize=16;    //actual size of each object
    private static final int scale= 3;               // main canvas scaled
    private static final int tileSize = originalTileSize*scale;  //displayed tile size
    private static final int maxScreenCol=16;
    private static final int maxScreenRow=12;
    private static final int screenWidth = tileSize*maxScreenCol; //screen dimension x
    private static final int screenHeight = tileSize*maxScreenRow; //screen dimension y
    private static final int FPS= 60;

    private ArrayList<GameObject> cafeObjects;
    private ArrayList<GameObject> bossFightObjects;
    private Thread gameThread;


    private BufferedImage menuBackground, cafeBackground, bossFightBackground;
    private BufferedImage targetBackground;

    private StateManager stateManager;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        cafeObjects= new ArrayList<>();
        bossFightObjects = new ArrayList<>();
        this.stateManager= StateManager.getInstance();
    }

    public void startThread(){              //starting thread
        gameThread= new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta= 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread!=null) {        //game loop
            currentTime= System.nanoTime();
            delta += (currentTime-lastTime)/drawInterval;

            lastTime = currentTime;

            if (delta>=1) {
                update();
                repaint();
                delta--;
            }
            try{
                Thread.sleep(1);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if (stateManager.getState()==0){
            targetBackground = menuBackground;
        }
        else if (stateManager.getState()==1) {
            targetBackground = cafeBackground;
            for (GameObject object : cafeObjects) {
                object.update();
            }
        }
        else if (stateManager.getState()==2){
            targetBackground = bossFightBackground;
            for (GameObject object : bossFightObjects){
                object.update();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(targetBackground,0,0,null);
        if (stateManager.getState()==1) {
            for (GameObject object : cafeObjects) {
                object.draw(g2);
            }
        }
        else if (stateManager.getState()==2) {
            for (GameObject object : bossFightObjects) {
                object.draw(g2);
            }
        }

        g2.dispose();
    }

    public static int getScreenWidth(){
        return screenWidth;
    }
    public static int getScreenHeight(){
        return screenHeight;
    }
    public void addGameObjectInCafe(GameObject object){
        cafeObjects.add(object);
    }
    public void addGameObjectInBossFight(GameObject object){
        bossFightObjects.add(object);
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    private final int originalTileSize=16;    //actual size of each object
    private final int scale= 3;               // main canvas scaled
    private final int tileSize = originalTileSize*scale;  //displayed tile size
    private final int maxScreenCol=16;
    private final int maxScreenRow=12;
    private final int screenWidth = tileSize*maxScreenCol; //screen dimension x
    private final int screenHeight = tileSize*maxScreenRow; //screen dimension y
    final int FPS= 60;

    private ArrayList<GameObject> gameObjects;
    private Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        gameObjects= new ArrayList<>();
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
        for (GameObject object: gameObjects){
            object.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (GameObject object: gameObjects){
            object.draw(g2);
        }
        g2.dispose();
    }



    public void addGameObject(GameObject object){
        gameObjects.add(object);
    }
}

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Rain implements GameObject {
    private static int counter=0;
    private static int x0Counter=0;
    private  int x,y;
    private BufferedImage rainImage;
    private int speed;
    private int rainSpawnCounter;

    public Rain(){
        try{
            rainImage = ImageIO.read(getClass().getResourceAsStream("Backgrounds/RainDrop.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        setDefaults();
            counter++;
        if (counter>=22){
            counter = 0;
        }
    }

    public void setDefaults(){
        switch(counter) {
            case 0: x = 0; break;
            case 1: x = 50; break;
            case 2: x = 100; break;
            case 3: x = 150; break;
            case 4: x = 200; break;
            case 5: x = 250; break;
            case 6: x = 300; break;
            case 7: x = 350; break;
            case 8: x = 400; break;
            case 9: x = 450; break;
            case 10: x = 500; break;
            case 11: x = 550; break;
            case 12: x = 600; break;
            case 13: x = 650; break;
            case 14: x = 700; break;
            case 15: x = 750; break;
            case 16: x = 800; break;
            case 17: x = 850; break;
            case 18: x = 900; break;
            case 19: x = 950; break;
            case 20: x = 1000; break;
            case 21: x = 1050; break;
            case 22: x = 1100; break;
        }
        speed = 2;
        y=-100;
    }
    @Override
    public void update() {
        x-=speed/2;
        y+=speed;
    }

    @Override
    public void draw(Graphics2D g2) {
      g2.drawImage(rainImage,x,y,null);
    }
    public int getY(){
        return y;
    }
}

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
public class ConveyerBelt extends Entity implements GameObject{
        private ArrayList<Cake> cakes = new ArrayList<>();
        private int timer = 5;
        private int beltSpeed = 5;
        private String direction = "Forward";
        KeyHandler keyHandler;
        public ConveyerBelt(KeyHandler keyHandler){
            this.keyHandler=keyHandler;
            getBeltImage();
            getDefaultValues();
        }

        public void addCake(Cake cake) {
            cakes.add(cake);
        }

        public void moveForward() {
            for (Cake c : cakes) {
                c.setPosition(c.getPosition() + beltSpeed);
            }
        }

        public void moveBackward() {
            for (Cake c : cakes) {
                c.setPosition(c.getPosition() - beltSpeed);
            }
        }

        public void trashCake(Cake cake) {
            cake.setTrashed(true);
            cakes.remove(cake);
        }

        public void deliverCake(Cake cake) {
            cake.setComplete(true);
            cakes.remove(cake);
        }

        public boolean checkCompletion(Cake cake) {
            return cake.isComplete();
        }

        public int getBeltSpeed() { return beltSpeed; }
        public int getTimer() { return timer; }
        public String getDirection() { return direction; }
        public ArrayList<Cake> getCakes() { return cakes; }
        public void setBeltSpeed(int beltSpeed) { this.beltSpeed = beltSpeed; }
        public void setTimer(int timer) { this.timer = timer; }
        public void setDirection(String direction) { this.direction = direction; }
        public void setCakes(ArrayList<Cake> cakes) { this.cakes = cakes; }
    public void getBeltImage() {
        try { belt=ImageIO.read(getClass().getResourceAsStream("Belt/ConveyerBelt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getDefaultValues(){
            x=0;
            y=350;
            speed=4;
    }
    @Override
    public void update() {
            if (keyHandler.rightKeyPressed==true){
                moveForward();
            }
            else if (keyHandler.leftKeyPressed==true){
                moveBackward();
            }

    }
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(belt, x, y, 800, 50, null);

    }
}


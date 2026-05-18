import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setResizable(false);
        window.setTitle(":3 :3, :3, :3");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();
        window.setVisible(true);
        panel.startThread();
    }

}

import java.awt.*;

public class CafeButton {
    private String text;
    private Rectangle bounds;
    private Runnable action;
    private boolean isPressed = false;

    public CafeButton(String text, int x, int y, int width, int height, Runnable action) {
        this.text = text;
        this.bounds = new Rectangle(x, y, width, height);
        this.action = action;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(isPressed ? new Color(90, 24, 154) : new Color(123, 44, 191));
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15);

        g2.setColor(new Color(224, 170, 255)); // Light lavender border
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospace", Font.BOLD, 17));

        FontMetrics fm = g2.getFontMetrics();
        int textX = bounds.x + (bounds.width - fm.stringWidth(text)) / 2;
        int textY = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();

        g2.drawString(text, textX, textY);
    }

    public void triggerAction() {
        if (action != null) {
            action.run();
        }
    }

    public Rectangle getBounds() { return bounds; }
    public void setPressed(boolean pressed) { this.isPressed = pressed; }
}
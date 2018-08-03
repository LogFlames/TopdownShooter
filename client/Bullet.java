import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Bullet {

    private float x;
    private float y;
    private float rotation;
    private float speed = 500f;

    public boolean toRemove;

    private Color drawingColor;

    public Bullet(float startX, float startY, float rotation) {
        x = startX;
        y = startY;
        this.rotation = rotation;
        toRemove = false;

        drawingColor = Color.BLACK;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(drawingColor);
        g2d.fill(new Ellipse2D.Double(x, y, 6, 6));
    }

    public void update(float delta_time) {
        float delta_x = (float)Math.cos(Math.toRadians(rotation)) * speed * delta_time;
        float delta_y = (float)Math.sin(Math.toRadians(rotation)) * speed * delta_time;

        x += delta_x;
        y += delta_y;

        if (x > 1440 || x < 0 || y > 900 || y < 0) {
            toRemove = true;
        }
    }
}

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
    private float speed = 800f;

    private double hitDistance = 24;

    private int damage = 20;

    public boolean toRemove;

    public int id;

    private Color drawingColor;

    public Bullet(float startX, float startY, float rotation, Color c, int id) {
        x = startX;
        y = startY;
        this.rotation = rotation;
        this.id = id;
        toRemove = false;

        if (c == null) {
            drawingColor = Color.BLACK;
        } else {
            drawingColor = c;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if (drawingColor != null) {
            g2d.setColor(drawingColor);
            g2d.fill(new Ellipse2D.Double(x * TopdownShooter.instance.scaleX, y * TopdownShooter.instance.scaleY,
                                          10 * TopdownShooter.instance.scaleX, 10 * TopdownShooter.instance.scaleY));
        }
    }

    public void update(float delta_time) {
        float delta_x = (float)Math.cos(Math.toRadians(rotation)) * speed * delta_time;
        float delta_y = (float)Math.sin(Math.toRadians(rotation)) * speed * delta_time;

        x += delta_x;
        y += delta_y;

        if (x > 1440 || x < 0 || y > 900 || y < 0) {
            toRemove = true;
            return;
        }

        if (id != Player.instance.id) {
            double distance = Math.hypot(x - Player.instance.x, y - Player.instance.y);

            if (distance <= hitDistance) {
                Player.instance.hit(damage);
                toRemove = true;
            }
        }
    }
}

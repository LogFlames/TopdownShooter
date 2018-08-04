import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public abstract class Creature extends Entity {
    public int health;
    public float vel_x;
    public float vel_y;

    // Time until completely stil
    protected float drag = 0.1f;

    protected void move(float delta_time, boolean use_drag) {
        x += vel_x * delta_time;
        y += vel_y * delta_time;

        if (x > 1395) {
            x = 1395;
        }
        if (x < 0) {
            x = 1;
        }
        if (y > 855) {
            y = 855;
        }
        if (y < 0) {
            y = 0;
        }

        if (use_drag) {
            vel_x *= (1 - 1 / drag * delta_time);
            vel_y *= (1 - 1 / drag * delta_time);
        }
    }

    public void drawHealthBar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        AffineTransform old = g2d.getTransform();
        g2d.translate((x + 23) * TopdownShooter.instance.scaleX, (y + 23) * TopdownShooter.instance.scaleY);

        g2d.setColor(Color.GREEN);

        float filled = health / 100f * 56f;
        g2d.fillRoundRect((int)(-28 * TopdownShooter.instance.scaleX), (int)(-64 * TopdownShooter.instance.scaleY),
                          (int)(filled * TopdownShooter.instance.scaleX), (int)(12 * TopdownShooter.instance.scaleY),
                          (int)(12 * TopdownShooter.instance.scaleX), (int)(12 * TopdownShooter.instance.scaleY));

        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3.0f));
        double x = -28 * TopdownShooter.instance.scaleX;
        double y = -64 * TopdownShooter.instance.scaleY;
        double w = 56 * TopdownShooter.instance.scaleX;
        double h = 12 * TopdownShooter.instance.scaleY;

        g2d.draw(new RoundRectangle2D.Double(x, y, w, h, 12 * TopdownShooter.instance.scaleX, 12 * TopdownShooter.instance.scaleY));
       
        g2d.setTransform(old);
    }

    @Override
    public void setNewData(PositionData data) {
        super.setNewData(data);

        vel_x = data.vel_x;
        vel_y = data.vel_y;

        health = data.health;
    }

    public void hit(int amount) {
        health -= amount;
        if (health <= 0) {
            die();
        }
    }

    public void die() {
        x = 0;
        y = 0;
        health = 100;
    }
}

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

public class BulletManager {

    public static BulletManager instance;

    public ArrayList<Bullet> bullets;

    public BulletManager() {
        instance = this;
        bullets = new ArrayList<Bullet>();
    }

    public void draw(Graphics g) {
        if (bullets == null || bullets.size() <= 0) {
            return;
        }
        Bullet[] bullet_ar = bullets.toArray(new Bullet[bullets.size()]);
        for (Bullet b : bullet_ar) {
            if (b != null) {
                b.draw(g);
            }
        }
    }

    public void update(float delta_time) {
        Iterator<Bullet> iter = bullets.iterator();

        while (iter.hasNext()) {
            Bullet b = iter.next();

            b.update(delta_time);

            if (b.toRemove) {
                iter.remove();
            }
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void delteBulletsFromPositionWithRadius(float x, float y, float rad, float minRad) {
        Iterator<Bullet> iter = bullets.iterator();

        while (iter.hasNext()) {
            Bullet b = iter.next();

            double dist = Math.hypot(b.x - x, b.y - y);
            if (dist < rad && dist > minRad) {
                iter.remove();
            }
        }
    }
}

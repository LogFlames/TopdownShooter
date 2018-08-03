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
        if (bullets.size() > 0) {
            for (Bullet b : bullets) {
                b.draw(g);
            }
        }
    }

    public void update(float delta_time) {
        for (Bullet b : bullets) {
            b.update(delta_time);
        }

        for (int n = bullets.size() - 1; n >= 0; n--) {
            if (bullets.get(n).toRemove) {
                bullets.remove(n);
            }
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
}

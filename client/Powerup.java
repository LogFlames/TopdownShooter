// Powerup.java

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Powerup extends Entity {

    private float pickup_distance = 30;

    private BufferedImage ammobox;

    public Powerup(float startX, float startY) {
        try {
            ammobox = ImageIO.read(new File("assets/ammobox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        x = startX;
        y = startY;
    }

    @Override
    public void update(float delta_time) {
        double distance = Math.hypot(x - (Player.instance.x + 23), y - (Player.instance.y + 23));

        if (distance < pickup_distance) {
            Player.instance.ammo = 10;
            toRemove = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(ammobox, (int)((x - 32) * TopdownShooter.instance.scaleX), (int)((y - 32) * TopdownShooter.instance.scaleY),
                    (int)(56 * TopdownShooter.instance.scaleX), (int)(56 * TopdownShooter.instance.scaleY), null);
    }
}

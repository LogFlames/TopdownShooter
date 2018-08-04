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

    private float pickup_distance = 27;

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
        double distance = Math.hypot(x - Player.instance.x, y - Player.instance.y);

        if (distance < pickup_distance) {
            Player.instance.ammo = 10;
            toRemove = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(ammobox, (int)(x - 32), (int)(y - 32), 64, 64, null);
    }
}

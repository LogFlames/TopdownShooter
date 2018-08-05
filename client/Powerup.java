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

    private float pickup_distance = 32;

    private BufferedImage image;

    private boolean activted;

    private String typeOfPowerup;

    // This is for clear-bullets type of powerup
    private float ringSpeed = 400f;
    private boolean ringStarted;
    private boolean ringDone;
    private float ringRadius = 0f;

    public Powerup(float startX, float startY, int id, String type) {
        try {
            if ("ammo".equals(type)) {
                image = ImageIO.read(new File("assets/ammobox.png"));
            } else if ("clear_bullets".equals(type)) {
                image = ImageIO.read(new File("assets/clear_bullets.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        x = startX;
        y = startY;

        this.typeOfPowerup = type;

        this.id = id;
    }

    @Override
    public void update(float delta_time) {
        double distance = Math.hypot(x - (Player.instance.x + 23), y - (Player.instance.y + 23));

        if (distance < pickup_distance) {
            toRemove = true;
        }

        if (ringStarted) {
            ringRadius += ringSpeed * delta_time;

            if (ringRadius > 1440) {
                ringDone = true;
                toRemove = true;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        if (ringStarted) {
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(10f));
            g2d.drawOval((int)(x - ringRadius / 2), (int)(y - ringRadius / 2), (int)ringRadius, (int)ringRadius);
        } else {
            g2d.drawImage(image, (int)((x - 32) * TopdownShooter.instance.scaleX), (int)((y - 32) * TopdownShooter.instance.scaleY),
                    (int)(56 * TopdownShooter.instance.scaleX), (int)(56 * TopdownShooter.instance.scaleY), null);
        }
    }

    @Override
    public boolean onDeath() {
        if ("ammo".equals(typeOfPowerup)) {
            Player.instance.ammo = 10;
            Player.instance.pickedupPowerup = true;
            Player.instance.powerupPickedupId = id;
            return true;
        } else if ("clear_bullets".equals(typeOfPowerup)) {
            toRemove = false;
            ringStarted = true;
            return false;
        } else if (ringStarted && ringDone) {
            System.out.println("Removed");
            return true;
        }
        return true;
    }
}

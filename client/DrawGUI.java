// DrawGUI.java

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class DrawGUI {
    private BufferedImage heart;
    private BufferedImage ammo;

    public DrawGUI() {
        heart = null;
        ammo = null;
        try {
            heart = ImageIO.read(new File("assets/heart.png"));
            ammo = ImageIO.read(new File("assets/ammo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        g.setFont(new Font("Lucida Sans", Font.PLAIN, (int)(40 * TopdownShooter.instance.scaleX)));
        g.drawString(Integer.toString(Player.instance.health), (int)(85 * TopdownShooter.instance.scaleX), (int)(857 * TopdownShooter.instance.scaleY));
        g.drawImage(heart, (int)(15 * TopdownShooter.instance.scaleX), (int)(820 * TopdownShooter.instance.scaleY),
                    (int)(56 * TopdownShooter.instance.scaleX), (int)(56 * TopdownShooter.instance.scaleY), null);
    }
}

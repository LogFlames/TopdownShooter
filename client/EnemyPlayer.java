// Enemy Player

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class EnemyPlayer extends Creature {

    public EnemyPlayer(PositionData startData) {
        setNewData(startData);
        health = 100;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        AffineTransform old = g2d.getTransform();
        g2d.translate((x + 23) * TopdownShooter.instance.scaleX, (y + 23) * TopdownShooter.instance.scaleY);
        g2d.rotate(Math.toRadians(rotation));

        g2d.setColor(Color.BLACK);

        g2d.fillRect((int)(-6 * TopdownShooter.instance.scaleX), (int)(20 * TopdownShooter.instance.scaleY), (int)(12 * TopdownShooter.instance.scaleX), (int)(24 * TopdownShooter.instance.scaleY));

        g2d.fillRoundRect((int)(-9 * TopdownShooter.instance.scaleX), (int)(36 * TopdownShooter.instance.scaleY),
                          (int)(18 * TopdownShooter.instance.scaleX), (int)(10 * TopdownShooter.instance.scaleY),
                          (int)(4 * TopdownShooter.instance.scaleX), (int)(4 * TopdownShooter.instance.scaleY));

        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect((int)(-23 * TopdownShooter.instance.scaleX), (int)(-23 * TopdownShooter.instance.scaleY),
                          (int)(46 * TopdownShooter.instance.scaleX), (int)(46 * TopdownShooter.instance.scaleY),
                          (int)(12 * TopdownShooter.instance.scaleX), (int)(12 * TopdownShooter.instance.scaleY));

        g2d.setTransform(old);
    }

    @Override
    public void update(float delta_time) {
        move(delta_time, true);
    }
}

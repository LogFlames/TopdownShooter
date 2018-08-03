import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;

public class Player extends Creature {
    public static Player instance;
    private float speed = 30;
    public int id;

    public Player() {
        instance = this;
        rotation = 0f;
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

        g2d.setColor(Color.RED);
        g2d.fillRoundRect((int)(-23 * TopdownShooter.instance.scaleX), (int)(-23 * TopdownShooter.instance.scaleY),
                          (int)(46 * TopdownShooter.instance.scaleX), (int)(46 * TopdownShooter.instance.scaleY),
                          (int)(12 * TopdownShooter.instance.scaleX), (int)(12 * TopdownShooter.instance.scaleY));

        g2d.setTransform(old);
    }

    @Override
    public void update(float delta_time) {
        float toMoveX = 0;
        float toMoveY = 0;

        // TODO: Fix holding W and S at the same time and moving left and/or right
        if (TopdownShooter.instance.inputData.forward && (TopdownShooter.instance.inputData.lastVert == "forward" || TopdownShooter.instance.inputData.lastVert == "")) {
            toMoveY = -1;
        }
        if (TopdownShooter.instance.inputData.backwards && (TopdownShooter.instance.inputData.lastVert == "backwards" || TopdownShooter.instance.inputData.lastVert == "")) {
            toMoveY = 1;
        }

        if (TopdownShooter.instance.inputData.left && (TopdownShooter.instance.inputData.lastHori == "left" || TopdownShooter.instance.inputData.lastHori == "")) {
            toMoveX = -1;
        }
        if (TopdownShooter.instance.inputData.right && (TopdownShooter.instance.inputData.lastHori == "right" || TopdownShooter.instance.inputData.lastHori == "")) {
            toMoveX = 1;
        }

        if (toMoveX != 0 && toMoveY != 0) {
            toMoveX *= 0.707;
            toMoveY *= 0.707;
        }

        vel_x += toMoveX * speed;
        vel_y += toMoveY * speed;

        move(delta_time, true);

        float del_x = TopdownShooter.instance.mouseX - (x + 23);
        float del_y = TopdownShooter.instance.mouseY - (y + 23);

        if (del_x < 0) {
            rotation = (float)Math.toDegrees(Math.atan2(Math.abs(del_x), del_y));
        } else {
            rotation = 360f - (float)Math.toDegrees(Math.atan2(del_x, del_y));
        }
        if (rotation < 0) {
            rotation = 359;
        }
        rotation %= 360;
    }
}

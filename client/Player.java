import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Player extends Creature {
    private float speed = 130;
    private int id;

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, 100, 100);
    }

    @Override
    public void update(float delta_time) {
        float toMoveX = 0;
        float toMoveY = 0;

        if (TopdownShooter.instance.inputData.forward) {
            toMoveY = -1;
        } else if (TopdownShooter.instance.inputData.backwards) {
            toMoveY = 1;
        }

        if (TopdownShooter.instance.inputData.left) {
            toMoveX = -1;
        } else if (TopdownShooter.instance.inputData.right) {
            toMoveX = 1;
        }

        vel_x = toMoveX * speed;
        vel_y = toMoveY * speed;

        move(delta_time);
    }
}

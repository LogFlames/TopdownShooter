import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Player extends Creature {
    public static Player instance;
    private float speed = 40;
    public int id;

    public Player() {
        instance = this;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, 100, 100);
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

        vel_x += toMoveX * speed;
        vel_y += toMoveY * speed;

        move(delta_time);
    }
}

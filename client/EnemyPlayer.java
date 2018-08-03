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

public class EnemyPlayer extends Creature {

    public EnemyPlayer(PositionData startData) {
        setNewData(startData);
        health = 100;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y, 35, 36);
    }

    @Override
    public void update(float delta_time) {
        move(delta_time, true);
    }
}

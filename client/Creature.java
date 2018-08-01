import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public abstract class Creature extends Entity {
    protected float health;
    protected float vel_x;
    protected float vel_y;

    protected void move(float delta_time) {
        x += vel_x * delta_time;
        y += vel_y * delta_time;
    }
}

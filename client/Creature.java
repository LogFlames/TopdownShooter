import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public abstract class Creature extends Entity {
    public int health;
    public float vel_x;
    public float vel_y;

    // Time until completely stil
    protected float drag = 0.1f;

    protected void move(float delta_time) {
        x += vel_x * delta_time;
        y += vel_y * delta_time;

        vel_x *= (1 - 1 / drag * delta_time);
        vel_y *= (1 - 1 / drag * delta_time);
    }
}

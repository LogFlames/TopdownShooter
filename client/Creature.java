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

    protected void move(float delta_time, boolean use_drag) {
        x += vel_x * delta_time;
        y += vel_y * delta_time;

        if (x < 0) {
            x = TopdownShooter.instance.width - 1;
        }
        x %= TopdownShooter.instance.width;
        if (y < 0) {
            y = TopdownShooter.instance.height - 1;
        }
        y %= TopdownShooter.instance.height;

        if (use_drag) {
            vel_x *= (1 - 1 / drag * delta_time);
            vel_y *= (1 - 1 / drag * delta_time);
        }
    }

    @Override
    public void setNewData(PositionData data) {
        super.setNewData(data);

        vel_x = data.vel_x;
        vel_y = data.vel_y;

        health = data.health;
    }
}

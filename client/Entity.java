import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public abstract class Entity {
    protected float x;
    protected float y;

    protected float rotation;

    public int id;

    protected abstract void draw(Graphics g);
    protected abstract void update(float delta_time);

    public boolean toRemove;

    public void setNewData(PositionData data) {
        x = data.pos_x;
        y = data.pos_y;

        rotation = data.rotation;
    }
}

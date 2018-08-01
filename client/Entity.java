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

    protected abstract void draw(Graphics g);
    protected abstract void update(float delta_time);
}

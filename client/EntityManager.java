import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class EntityManager {
    private ArrayList<Entity> entities;
    private Player player;

    public EntityManager(Player player) {
        entities = new ArrayList<Entity>();
        this.player = player;
    }

    public void draw(Graphics g) {
        player.draw(g);
        for (Entity e : entities) {
            e.draw(g);
        }
    }

    public void update(float delta_time) {
        player.update(delta_time);
        for (Entity e : entities) {
            e.update(delta_time);
        }
    }

    public Player getPlayer() {
        return player;
    }
}

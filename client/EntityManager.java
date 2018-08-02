// EntityManager.java

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class EntityManager {
    private HashMap<Integer, Entity> entities;
    private Player player;

    public EntityManager(Player player) {
        entities = new HashMap<Integer, Entity>();
        this.player = player;
    }

    public void draw(Graphics g) {
        player.draw(g);
        for (Entity e : entities.values()) {
            e.draw(g);
        }
    }

    public void update(float delta_time) {
        player.update(delta_time);
        for (Entity e : entities.values()) {
            e.update(delta_time);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void addEntity(int id, Entity entity) {
        if (entities.containsKey(id)) {
            System.out.println("An entity with the id: " + id + " already exists.");
            return;
        }
        entities.put(id, entity);
    }

    public void removeEntity(int id) {
        if (!entities.containsKey(id)) {
            System.out.println("No entity with the id: " + id + " exists.");
            return;
        }
        entities.remove(id);
    }

    public void updateEntities() {

    }
}

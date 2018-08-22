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
    public static EntityManager instance;

    private HashMap<Integer, Entity> entities;
    private Player player;

    public EntityManager(Player player) {
        entities = new HashMap<Integer, Entity>();
        this.player = player;
        instance = this;
    }

    public void draw(Graphics g) {
/*
        if (entities != null) {
            for (Entity e : entities.values()) {
                if (e != null) {
                    try {
                        e.draw(g);
                    } catch (Exception ex) {

                    }
                }
            }
        }
*/

        Iterator<HashMap.Entry<Integer, Entity>> iter = entities.entrySet().iterator();

        while (iter.hasNext()) {
            HashMap.Entry<Integer, Entity> e = (HashMap.Entry<Integer, Entity>)iter.next();
            e.getValue().draw(g);
        }

        if (player != null) {
            player.draw(g);
        }
    }

    public void update(float delta_time) {
        player.update(delta_time);
        for (Entity e : entities.values()) {
            e.update(delta_time);
        }

        Iterator<HashMap.Entry<Integer, Entity>> iter = entities.entrySet().iterator();

        while (iter.hasNext()) {
            HashMap.Entry<Integer, Entity> e = (HashMap.Entry<Integer, Entity>)iter.next();

            if (e.getValue().toRemove) {
                if (e.getValue().onDeath()) {
                    iter.remove();
                } else {
                    e.getValue().toRemove = false;
                }
            }
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
            //System.out.println("No entity with the id: " + id + " exists.");
            return;
        }
        entities.get(id).toRemove = true;
    }

    public void updateEntities(int keyId, PositionData data) {
        if (entities.containsKey(keyId) && Player.instance.id != keyId) {
            entities.get(keyId).setNewData(data);
        } else if (Player.instance.id != keyId) {
            addEntity(keyId, new EnemyPlayer(data));
        }
    }
}


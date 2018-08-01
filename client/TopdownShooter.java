// TopdownShooter.java

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class TopdownShooter {

    private JFrame frame;
    private JPanel panel;
    private static GraphicsConfinuration gc;

    private int width = 800;
    private int height = 600;

    private boolean running;
    
    public static void main(String[] agrs) {
        new TopdownShooter().init();
    }

    public void init() {
        running = true;

        frame = new JFrame(gc);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setSize(width, height);
        frame.setTitle("Multiplayer TopdownShooter")
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                draw(g);
            }
        };

        frame.add(panel);
        frame.addKeyListener(new AL());

        frame.setVisible(true);
    }

    private void gameLoop() {
        int targetFrameRate = 60;

        long timePerFrame = 1000000000 / targetFrameRate;

        long lastNanos = System.nanoTime();

        while (running) {
            long nanos = System.nanoTime();

            if (nanos - lastNanos < timePerFrame) {
                lastNanos = nanos;
                panel.repaint();
                update();
            }
        }
    }

    private void update() {

    }

    private void draw() {

    }

    private class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode()
            switch (keyCode) {
                case KeyEvent.VK_W:
                    break;
                case KeyEvent.VK_S:
                    break;
                case KeyEvent.VK_A:
                    break;
                case KeyEvent.VK_D:
                    break;
                default:
                    break;
            }
        }

        @override
        public void keyReleased(KeyEvent event) {

        }
    }
}

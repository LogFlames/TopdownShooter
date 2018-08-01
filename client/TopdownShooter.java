// TopdownShooter.java

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

public class TopdownShooter {

    public static TopdownShooter instance;

    private JFrame frame;
    private JPanel panel;
    private static GraphicsConfiguration gc;

    private int width = 800;
    private int height = 600;

    private boolean running;

    private EntityManager entityManager;

    public InputData inputData;
    
    public static void main(String[] agrs) {
        instance = new TopdownShooter();
        instance.init();
    }

    public void init() {
        running = true;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();

        frame = new JFrame(gc);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setSize(width, height);
        frame.setTitle("Multiplayer TopdownShooter");
        frame.setResizable(false);
        frame.setFocusable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                draw(g);
            }
        };

        panel.setFocusable(true);

        frame.add(panel);

        addKeyBindings(panel);

        frame.setVisible(true);

        entityManager = new EntityManager(new Player());

        inputData = new InputData();

        gameLoop();
    }

    private void gameLoop() {
        int targetFrameRate = 60;

        long nanosPerFrame = (long) 1e9 / targetFrameRate;

        long lastNanos = System.nanoTime();

        while (running) {
            long nanos = System.nanoTime();

            if (nanos - lastNanos >= nanosPerFrame) {
                float delta_time = (float) ((nanos - lastNanos) / 1e9);
                lastNanos = nanos;
                panel.repaint();
                update(delta_time);
            }
        }
    }

    private void update(float delta_time) {
        entityManager.update(delta_time);
    }

    private void draw(Graphics g) {
        entityManager.draw(g);
    }

    private void addKeyBindings(JComponent jc) {
        // D
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D pressed");

        jc.getActionMap().put("D pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.right = true;
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");

        jc.getActionMap().put("D released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.right = false;
                              }
                          });

        // A
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A pressed");

        jc.getActionMap().put("A pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.left = true;
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");

        jc.getActionMap().put("A released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.left = false;
                              }
                          });

        // W
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W pressed");

        jc.getActionMap().put("W pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.forward = true;
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");

        jc.getActionMap().put("W released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.forward = false;
                              }
                          });

        // S
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S pressed");

        jc.getActionMap().put("S pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.backwards = true;
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");

        jc.getActionMap().put("S released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.backwards = false;
                              }
                          });
    }

    public class InputData {
        public boolean forward;
        public boolean backwards;
        public boolean left;
        public boolean right;

    }
}

// TopdownShooter.java

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class TopdownShooter {

    public static TopdownShooter instance;

    private JFrame frame;
    private JPanel panel;
    private static GraphicsConfiguration gc;

    private int width;
    private int height;

    private boolean running;

    private EntityManager entityManager;

    public InputData inputData;

    private Client client;

    private int frameLoopIndex;
    
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
//        frame.setSize(600, 600);
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
        client = new Client();
        inputData = new InputData();

        frameLoopIndex = 0;

        gameLoop();
    }

    public void exit() {
        client.closeConnection();
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
        if (frameLoopIndex > 3) {
            frameLoopIndex = 0;
            client.updateLocations();
            client.sendProtocol();
        }
        entityManager.update(delta_time);
        frameLoopIndex++;
    }

    private void draw(Graphics g) {
        if (entityManager != null) {
            entityManager.draw(g);
        }
    }

    private void addKeyBindings(JComponent jc) {
        // D
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D pressed");

        jc.getActionMap().put("D pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.right = true;
                                  inputData.lastHori = "right";
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");

        jc.getActionMap().put("D released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.right = false;
                                  if (inputData.lastHori == "right") {
                                      inputData.lastHori = "";
                                  }
                              }
                          });

        // A
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A pressed");

        jc.getActionMap().put("A pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.left = true;
                                  inputData.lastHori = "left";
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");

        jc.getActionMap().put("A released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.left = false;
                                  if (inputData.lastHori == "left") {
                                      inputData.lastHori = "";
                                  }
                              }
                          });

        // W
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W pressed");

        jc.getActionMap().put("W pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.forward = true;
                                  inputData.lastVert = "forward";
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");

        jc.getActionMap().put("W released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.forward = false;
                                  if (inputData.lastVert == "forward") {
                                      inputData.lastVert = "";
                                  }
                              }
                          });

        // S
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S pressed");

        jc.getActionMap().put("S pressed", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.backwards = true;
                                  inputData.lastVert = "backwards";
                              }
                          });
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");

        jc.getActionMap().put("S released", new AbstractAction() {
                              @Override
                              public void actionPerformed(ActionEvent ae) {
                                  inputData.backwards = false;
                                  if (inputData.lastVert == "backwards") {
                                      inputData.lastVert = "";
                                  }
                              }
                          });
    }

    public class InputData {
        public boolean forward;
        public boolean backwards;
        public boolean left;
        public boolean right;

        public String lastVert = "";
        public String lastHori = "";
    }
}

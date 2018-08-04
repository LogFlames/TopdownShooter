// Client.java

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;

public class Client {

    private Socket socket;

    private BufferedReader input;
    private PrintStream output;

    private boolean gotId;

    public Client() {
        gotId = false;
        int port = 1023;
        while (socket == null) {
            port++;
            try {
                socket = new Socket("172.16.1.140", port);
                //socket = new Socket("94.245.5.48", 8192);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLocations() {
        try {
            if (input.ready()) {
                String line;
                while ((line = input.readLine()) != null) {
                    if (TopdownShooter.debug) {
                        System.out.print("From server -->: ");
                        System.out.println(line);
                    }
                    line = line.trim();
                    if (line.indexOf("#") <= -1) {
                        line += "#";
                    }
                    boolean parsed = false;
                    String[] parts = line.split("#");
                    for (String part : parts) {
                        part = part.trim();
                        if ("hb".equals(part)) {
                            SendData("hbb");
                            parsed = true;
                        }
                        if (part.startsWith("?") && !gotId) {
                            parsed = true;
                            part = part.replace("?", "");
                            try {
                                Player.instance.id = Integer.parseInt(part);
                                gotId = true;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        if (part.startsWith("GU")) {
                            parsed = true;
                            ParseGameUpdate(part.replace("GU", ""));
                        }
                        if (part.startsWith("PU")) {
                            parsed = true;
                            ParsePowerup(part.replace("PU", ""));
                        }
                        if (!parsed) {
                            System.out.print("(Not parsed) From server -->: ");
                            System.out.println(line);
                        }
                    }
                    if (!input.ready()) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ParseGameUpdate(String gameUpdate) {
        PositionData pos_data = new PositionData();
        boolean createdBullet = false;
        boolean pickedupPowerup = false;

        // Shouldn't run, ParseGameUpdate should get called multiple times instead
        String[] entities = gameUpdate.split("#");

        for (String entity : entities) {
            String[] dataParts = entity.split(";");
            for (String headers : dataParts) {
                String[] splited = headers.split(":");
                String attr = splited[0];
                String value = splited[1];
                value = value.replace(",", ".");

                switch (attr) {
                    case "identifier":
                        pos_data.id = PositionData.tryParseInt(value);
                        break;
                    case "pos_x":
                        pos_data.pos_x = PositionData.tryParseInt(value);
                        break;
                    case "pos_y":
                        pos_data.pos_y = PositionData.tryParseInt(value);
                        break;
                    case "vel_x":
                        pos_data.vel_x = PositionData.tryParseFloat(value);
                        break;
                    case "vel_y":
                        pos_data.vel_y = PositionData.tryParseFloat(value);
                        break;
                    case "health":
                        pos_data.health = PositionData.tryParseInt(value);
                        break;
                    case "rot":
                        pos_data.rotation = PositionData.tryParseFloat(value);
                        break;
                    case "shoot":
                        if (!"False".equals(value)) {
                            pos_data.bulletRotation = PositionData.tryParseFloat(value) % 360;
                            createdBullet = true;
                        }
                        break;
                    case "power":
                        if (!"False".equals(value)) {
                            pos_data.powerupId = PositionData.tryParseInt(value);
                            pickedupPowerup = true;
                        }
                        break;
                    default:
                        System.out.println(String.format("Can't handle gameUpdateKey: %s", attr)); 
                        break;
                }
            }
        }

        if (createdBullet) {
            ParseBullet(pos_data);
        }

        if (pickedupPowerup) {
        }

        EntityManager.instance.updateEntities(pos_data.id, pos_data);
    }

    public void ParseBullet(PositionData data) {
        int x = data.pos_x + 23;
        int y = data.pos_y + 23;
        float rot = data.bulletRotation;

        x += (int)Math.cos(Math.toRadians(rot)) * 36f;
        y += (int)Math.sin(Math.toRadians(rot)) * 36f;

        Color c;
        if (data.id == Player.instance.id) {
            c = Color.RED;
        } else {
            c = Color.BLACK;
        }

        BulletManager.instance.addBullet(new Bullet(x, y, rot, c, data.id));
    }

    public void ParsePowerup(String data) {
        String[] parts = data.split(";");
        int pos_x = 0;
        int pos_y = 0;
        int id = 0;
        for (String part : parts) {
            String[] attrs = part.split(":");
            String attribute = attrs[0];
            String value = attrs[1];
            switch (attribute) {
                case "identifier":
                    id = PositionData.tryParseInt(value);
                    break;
                case "pos_x":
                    pos_x = (int)(PositionData.tryParseFloat(value) * 1440);
                    break;
                case "pos_y":
                    pos_y = (int)(PositionData.tryParseFloat(value) * 900);
                    break;
                default:
                    System.out.println("No way to parse: " + part + " in powerup update from server.");
                    break;
            }
        }
        EntityManager.instance.addEntity(id, new Powerup(pos_x, pos_y));
    }

    public void pickupPowerup(PositionData data) {
        if (data.id == Player.instance.id) {
            // we already removed theat powerup locally
            return;
        }

        EntityManager.instance.removeEntity(data.powerupId);
    }

    public void sendProtocol() {
        String shootPart = "False";
        if (Player.instance.shootThisFrame) {
            shootPart = Float.toString((Player.instance.shootRotation + 90f) % 360f);
            Player.instance.shootThisFrame = false;
        }
        String powerupPart = "False";
        if (Player.instance.pickedupPowerup) {
            powerupPart = Integer.toString(Player.instance.powerupPickedupId);
            Player.instance.pickedupPowerup = false;
        }
        String message = String.format("GUpos_x:%d;pos_y:%d;shoot:%s;health:%d;vel_x:%f;vel_y:%f;rot:%f;power:%s;#",
                                       (int)Player.instance.x, (int)Player.instance.y,
                                       shootPart, Player.instance.health,
                                       Player.instance.vel_x, Player.instance.vel_y,
                                       Player.instance.rotation, powerupPart);
        SendData(message);
    }

    public void SendData(String data) {
        if (data != null && data != "") {
            output.println(data + "#");
            if (TopdownShooter.debug) {
                System.out.print("To server -->: ");
                System.out.println(data + "#");
            }
        }
    }

    public void closeConnection() {
        try {
            output.close();
            input.close();
            socket.close();
            System.out.println("Closed connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

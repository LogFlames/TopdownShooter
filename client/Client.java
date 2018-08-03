// Client.java

import java.util.*;
import java.io.*;
import java.net.*;

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
                    System.out.print("From server -->: ");
                    System.out.println(line);
                    line = line.trim();
                    if (line.indexOf("#") <= -1) {
                        line += "#";
                    }
                    String[] parts = line.split("#");
                    for (String part : parts) {
                        part = part.trim();
                        if ("hb".equals(part)) {
                            SendData("hbb");
                        }
                        if (part.startsWith("?") && !gotId) {
                            part = part.replace("?", "");
                            try {
                                Player.instance.id = Integer.parseInt(part);
                                gotId = true;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        if (part.startsWith("GU")) {
                            ParseGameUpdate(part.replace("GU", ""));
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

        // Shouldn't run, ParseGameUpdate should get called multiple times instead
        String[] entities = gameUpdate.split("#");

        for (String entity : entities) {
            String[] dataParts = entity.split(";");
            for (String headers : dataParts) {
                String[] splited = headers.split(":");
                String attr = splited[0];
                String value = splited[1];

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
                        // No shooting implemented yet
                        break;
                    default:
                        System.out.println(String.format("Can't handle gameUpdateKey: %s", attr)); 
                        break;
                }
            }
        }

        EntityManager.instance.updateEntities(pos_data.id, pos_data);
    }

    public void sendProtocol() {
        String message = String.format("GUpos_x:%d;pos_y:%d;shoot:False;health:%d;vel_x:%f;vel_y:%f;rot:%f;",
                                       (int)Player.instance.x, (int)Player.instance.y,
                                       Player.instance.health,
                                       Player.instance.vel_x, Player.instance.vel_y,
                                       Player.instance.rotation);
        SendData(message);
    }

    public void SendData(String data) {
        if (data != null && data != "") {
            output.println(data + "#");
            System.out.print("To server    -->:");
            System.out.println(data + "#");
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

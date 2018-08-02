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
                socket = new Socket("172.16.1.138", port);
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

    public void sendProtocol() {
        String message = String.format("GUpos_x:%f;pos_y:%f;shoot:False;health:%d;vel_x:%f;vel_y:%f;rot:%f;",
                                       Player.instance.x, Player.instance.y,
                                       Player.instance.health,
                                       Player.instance.vel_x, Player.instance.vel_y,
                                       Player.instance.rotation);
        SendData(message);
    }

    public void SendData(String data) {
        if (data != null && data != "") {
            output.println(data + "#\n");
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

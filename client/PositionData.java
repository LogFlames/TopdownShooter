import java.util.*;
import java.io.*;

public class PositionData {
    public int pos_x;
    public int pos_y;
    public float vel_x;
    public float vel_y;

    public float rotation;
    public int health;

    public float bulletRotation;

    public int id;

    public static int tryParseInt(String toPar) {
        try {
            return Integer.parseInt(toPar);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float tryParseFloat(String toPar) {
        try {
            return Float.parseFloat(toPar);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

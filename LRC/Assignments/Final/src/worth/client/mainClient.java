package worth.client;

import worth.client.gui.GUI;
import worth.client.utils.Utils;

public class mainClient {
    public static void main(String[] args) {
        Utils.parseArgs(args);
        GUI.show();
    }

}

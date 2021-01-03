package worth.client;

import worth.client.gui.GUI;

public class clientMain {
    public static void main(String[] args) {
        Utils.parseArgs(args);
        GUI gui = new GUI();
        gui.showLogin();
    }
}

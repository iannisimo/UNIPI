package worth.client;

import worth.client.gui.GUI;

public class Main {
    public static void main(String[] args) {
        Utils.parseArgs(args);
        GUI gui = new GUI();
        gui.showLogin();
    }
}

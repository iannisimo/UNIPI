package Worth.Client;

import Worth.Client.GUI.GUI;

public class Main {
    public static void main(String[] args) {
        Utils.parseArgs(args);
        GUI gui = new GUI();
        gui.showLogin();
    }
}

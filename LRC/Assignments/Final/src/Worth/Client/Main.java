package Worth.Client;

import java.rmi.registry.LocateRegistry;

import Worth.Client.GUI.GUI;
import Worth.Common.RegisterServiceInterface;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.showLogin();
    }
}

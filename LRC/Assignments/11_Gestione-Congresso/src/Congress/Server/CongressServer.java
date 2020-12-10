package Congress.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Congress.Common.CongressService;

public class CongressServer {
    public static void main(String[] args) {

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Usage: CongressServer <port>");
            return;
        }

        try {
            ServiceImpl service = new ServiceImpl();
            CongressService stub = (CongressService) UnicastRemoteObject.exportObject(service, 0);
            Registry r = LocateRegistry.createRegistry(port);
            r.rebind(Congress.Common.Const.REG_NAME, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

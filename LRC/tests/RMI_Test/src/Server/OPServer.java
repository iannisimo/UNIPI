package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Common.OPService;

public class OPServer {
    public static void main(String[] args) {
        try {
            OPServiceImpl service = new OPServiceImpl();
            OPService stub = (OPService) UnicastRemoteObject.exportObject(service, 0);
            Registry r = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            // Registry r = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            r.rebind("SERVER", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

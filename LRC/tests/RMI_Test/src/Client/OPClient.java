package Client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Common.OPService;

public class OPClient {
    public static void main(String[] args) {
        OPService service;

        try {
            Registry r = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            Remote ro = r.lookup("SERVER");
            service = (OPService) ro;

            System.out.printf("%d, %d\n", service.add(1, 2), service.mul(53, 12));
            // try {
            //     service.t();
            // } catch (RemoteException e) {
            //     System.out.println("Gotcha!");
            // }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}

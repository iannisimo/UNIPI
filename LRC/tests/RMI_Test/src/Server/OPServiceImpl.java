package Server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

import Common.OPService;

public class OPServiceImpl extends RemoteServer implements OPService {

    private static final long serialVersionUID = 1L;

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public int mul(int a, int b) throws RemoteException {
        return a * b;
    }

    public void t() throws RemoteException, IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException();
    }
}
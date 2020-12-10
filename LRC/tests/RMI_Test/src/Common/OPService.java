package Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OPService extends Remote {
    int add(int a, int b) throws RemoteException;
    int mul(int a, int b) throws RemoteException;
    void t() throws RemoteException, IndexOutOfBoundsException;
}

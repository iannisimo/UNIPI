package worth.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackServerInterface extends Remote {
    public static final String REG = "CALLBACK_SERVER_REG";
    public void registerCallback(String username, CallbackInterface clientInterface) throws RemoteException;
    public void unregisterCallback(String username) throws RemoteException;
}

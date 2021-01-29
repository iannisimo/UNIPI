package worth.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CallbackInterface extends Remote {
    public static final String REG = "CALLBACK_REG";
    public void setStatus(String username, boolean online) throws RemoteException;
    public void setStatus(List<String> usernames, boolean online) throws RemoteException;
}

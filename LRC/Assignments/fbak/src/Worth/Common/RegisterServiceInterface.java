package Worth.Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterServiceInterface extends Remote {

    /**
     * Register to the WORTH service using RMI
     * @param username
     * @param password
     * @return {true, false} -> {success, dup.user}
     * @throws RemoteException
     */
    public boolean register(String username, String password) throws RemoteException;
}
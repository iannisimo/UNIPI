package Worth.Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterServiceInterface extends Remote {

    /**
     * Register to the WORTH service using RMI
     * @param username
     * @param hash
     * @return {0, 1, -1} -> {success, dup. username, error}
     * @throws RemoteException
     */
    public short register(String username, String hash) throws RemoteException;
}
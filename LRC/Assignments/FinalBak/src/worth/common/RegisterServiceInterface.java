package worth.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterServiceInterface extends Remote {

    public static final String RMI_REG = "REG_SERVICE";

    /**
     * Register to the WORTH service using RMI
     * @param username
     * @param password
     * @return {true, false} -> {success, dup.user}
     * @throws RemoteException
     * @throws IllegalArgumentException if the request is unfulfillable
     */
    public void register(String username, String hash) throws RemoteException, IllegalArgumentException;
}
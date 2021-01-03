package worth.server.users;

import java.rmi.RemoteException;

import worth.common.RegisterServiceInterface;

public class RegisterService implements RegisterServiceInterface {

    /**
     * Implementation of the RMI register service, called from a client to register a new user.
     * @param username
     * @param hash
     * @throws RemoteException
     * @throws IllegalArgumentException The message in the exception will contain information about what went wrong (Username taken, empty, ...)
     */
    @Override
    public void register(String username, String hash) throws RemoteException, IllegalArgumentException {
        Users.addUser(username, hash);
    }
}

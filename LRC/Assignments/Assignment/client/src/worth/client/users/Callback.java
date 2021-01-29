package worth.client.users;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.List;

import worth.common.CallbackInterface;

/**
 * This methods are called by the server to update the lists of users
 */
public class Callback extends RemoteObject implements CallbackInterface {

    // Default serial version UID
    private static final long serialVersionUID = 1L;

    @Override
    public void setStatus(String username, boolean online) throws RemoteException {
        Users.setStatus(username, online);
    }

    @Override
    public void setStatus(List<String> usernames, boolean online) throws RemoteException {
        Users.setStatus(usernames, online);
    }
    
}

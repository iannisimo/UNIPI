package worth.server.users;

import java.rmi.RemoteException;

import worth.common.RegisterServiceInterface;

public class RegisterService implements RegisterServiceInterface {

    @Override
    public void register(String username, String hash) throws RemoteException, IllegalArgumentException {
        Users.addUser(username, hash);
    }
}

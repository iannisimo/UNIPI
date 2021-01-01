package Worth.Server.Users;

import java.rmi.RemoteException;

import Worth.Common.RegisterServiceInterface;

public class RegisterService implements RegisterServiceInterface {

    @Override
    public boolean register(String username, String password) throws RemoteException {
        if(Users.addUser(username, password)) {
            return true;
        }
        return false;
    }
}

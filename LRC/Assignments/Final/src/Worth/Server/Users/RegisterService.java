package Worth.Server.Users;

import java.rmi.RemoteException;

import Worth.Common.RegisterServiceInterface;

public class RegisterService implements RegisterServiceInterface {

    protected Users users;

    public RegisterService() {
        users = new Users();
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        System.out.println(username + password);
        if(users.addUser(username, password)) {
            return true;
        }
        return false;
    }
    
}

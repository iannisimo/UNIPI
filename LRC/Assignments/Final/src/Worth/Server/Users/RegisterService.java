package Worth.Server.Users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import Worth.Common.RegisterServiceInterface;
import Worth.Server.Const;

public class RegisterService implements RegisterServiceInterface {

    protected Users users;

    public RegisterService() {
        File usersFile = new File(Const.USERS_FILE);
        if (usersFile.isFile()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile));
                users = (Users) ois.readObject();
                ois.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            users = new Users();
        }
    }

    @Override
    public short register(String username, String hash) throws RemoteException {
        try {
            if(users.addUser(username, hash)) {
                return 0;
            }
            return 1;
        } catch (NullPointerException e) {
            return -1;
        }
    }
    
}

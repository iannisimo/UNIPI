package Worth.Server.Users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Worth.Server.Const;

public class Users {

    private Map<String, User> users;

    public Users() {
        users = new HashMap<>();
        File usersFile = new File(Const.USERS_FILE);
        if(usersFile.isFile()) {
            try (BufferedReader r = new BufferedReader(new FileReader(usersFile))){
                while(true) {
                    String user = r.readLine();
                    if(user == null) 
                        break;
                    String[] split = user.split(":");
                    users.put(split[0], new User(split[0], split[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a user to the "DB"
     * 
     * @param username
     * @param password
     * @return {true, false} -> {success, dup. user}
     * @throws NullPointerException
     */
    public Boolean addUser(String username, String password) throws NullPointerException {
        if (username == null)
            throw new NullPointerException();
        if (password == null)
            throw new NullPointerException();
        if (users.containsKey(username))
            return false;
        User user = new User(username, password);
        users.put(username, user);
        appendNew(user);
        return true;
    }

    private void appendNew(User u) {
        String newline = String.format("%s:%s\n", u.getUsername(), u.getPassword());
        File usersFile = new File(Const.USERS_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(usersFile, true);
            fos.write(newline.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

final class User {

    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public boolean passwordMatch(String password) {
        return this.password.equals(password);
    }
}
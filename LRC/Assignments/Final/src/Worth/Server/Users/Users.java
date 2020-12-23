package Worth.Server.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {

    // Default serialVersionUID
    private static final long serialVersionUID = 1L;

    private Map<String, User> users;

    public Users() {
        users = new HashMap<>();
    }
    
    /**
     * Add a user to the "DB"
     * @param username
     * @param hash
     * @return {true, false} -> {success, dup. user}
     * @throws NullPointerException
     */
    public Boolean addUser(String username, String hash) throws NullPointerException {
        if(username == null) throw new NullPointerException();
        if(hash == null) throw new NullPointerException();
        if(users.containsKey(username)) return false;
        users.put(username, new User(username, hash));
        return true;
    }
}

final class User {

    private final String username;
    private final String hash;

    public User(String username, String hash) {
        this.username = username;
        this.hash = hash;
    }

    public String getUsername() {
        return this.username;
    }
    public String getHash() {
        return this.hash;
    }
}
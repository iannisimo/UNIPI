package worth.server.users;

/**
 * Data structure containing a user's information.
 * Useless in the current state of the program but facilitates later revisions
 */
public class User {
    private String hash;

    public User(String hash) {
        this.hash = hash;
    }

    public boolean login(String hash) {
        return hash.equals(this.hash);
    }
}

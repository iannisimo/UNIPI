package worth.server.users;

public class User {
    private String hash;
    private boolean online;

    public User(String hash) {
        this.hash = hash;
        this.online = false;
    }

    public boolean login(String hash) {
        if(hash.equals(this.hash)) {
            this.online = true;
        }
        return this.online;
    }

    public void logout() {
        this.online = false;
    }

    public boolean isOnline() {
        return this.online;
    }
}

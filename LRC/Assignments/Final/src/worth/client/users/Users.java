package worth.client.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Users {
    private static Map<String, Boolean> users = new HashMap<>();
    
    public static List<String> listUsers() {
        return users.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    public static Map<String, Boolean> listOnlineUsers() {
        return users.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected static void setStatus(List<String> usernames, boolean online) {
        users.putAll(usernames.stream().collect(Collectors.toMap(k->k, k->online)));
    }

    protected static void setStatus(String username, boolean online) {
        users.put(username, online);
    }


}

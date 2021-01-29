package worth.client.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

/**
 * This structure keeps the users and their status
 */
public class Users {
    private static Map<String, Boolean> users = new HashMap<>();
    private static ObservableList<Text> list = FXCollections.observableArrayList();

    /**
     * @return A list containing all the users registered to the service
     */
    public static List<String> listUsers() {
        return users.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    /**
     * @return A map of all the users and their states
     */
    public static Map<String, Boolean> listOnlineUsers() {
        return users.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * @param usernames List of names to update
     * @param online New state
     */
    protected static void setStatus(List<String> usernames, boolean online) {
        users.putAll(usernames.stream().collect(Collectors.toMap(k->k, k->online)));
        refreshList();
    }

    /**
     * @param username Name of the user to update
     * @param online New state
     */
    protected static void setStatus(String username, boolean online) {
        users.put(username, online);
        refreshList();
    }

    /**
     * This method is used to link the GUI with the list of users
     * @return An ObservableList
     */
    public static ObservableList<Text> registerListView() {
        return list;
    }

    /**
     * Once called, this updates the list of users used by the GUI to show the users and their status.
     */
    public static void refreshList() {
        if(list == null) return;
        list.clear();
        users.entrySet().stream().forEach(u -> {
            Text t = new Text();
            t.setText(u.getKey());
            if(!u.getValue()) t.setOpacity(.5);
            list.add(t);
        });
    }
}

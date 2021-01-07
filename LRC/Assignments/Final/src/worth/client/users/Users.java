package worth.client.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class Users {
    private static Map<String, Boolean> users = new HashMap<>();
    private static ObservableList<Text> list = FXCollections.observableArrayList();
    
    public static List<String> listUsers() {
        return users.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    public static Map<String, Boolean> listOnlineUsers() {
        return users.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected static void setStatus(List<String> usernames, boolean online) {
        users.putAll(usernames.stream().collect(Collectors.toMap(k->k, k->online)));
        refreshList();
    }

    protected static void setStatus(String username, boolean online) {
        users.put(username, online);
        refreshList();
    }

    public static ObservableList<Text> registerListView() {
        return list;
    }

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

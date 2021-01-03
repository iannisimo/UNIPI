package worth.server.users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import worth.common.CallbackServerInterface;
import worth.server.Const;
import worth.server.projects.Projects;

public class Users {
    private static Map<String, User> users = new HashMap<>();
    private static Map<SelectionKey, String> onlineUsers = new HashMap<>();
    private static Callback callback;

    public static void initCallbackService() throws RemoteException {
        callback = new Callback();
        CallbackServerInterface stub = (CallbackServerInterface) UnicastRemoteObject.exportObject(callback, 0);
        Registry r = LocateRegistry.createRegistry(Const.RMI_PORT);
        r.rebind(CallbackServerInterface.REG, stub);
    }

    protected synchronized static void addUser(String username, String hash) {
        if (!username.matches(Const.UNAME_REGEX))
            throw new IllegalArgumentException("Wrong username format");
        if (users.containsKey(username))
            throw new IllegalArgumentException("Username taken");
        storeNew(username, hash);
        users.put(username, new User(hash));
        callback.setStatus(username, false);
    }

    public synchronized static boolean exists(String username) {
        return users.containsKey(username);
    }

    public synchronized static boolean login(String username, String hash, SelectionKey key) {
        if(!users.containsKey(username)) return false;
        if(!users.get(username).login(hash)) return false;
        if(onlineUsers.containsKey(key)) return false;
        if(onlineUsers.containsValue(username)) return false;
        onlineUsers.put(key, username);
        callback.setStatus(username, true);
        return true;
    }

    public synchronized static boolean logout(SelectionKey key) throws RemoteException {
        key.cancel();
        Projects.exitAllChats(onlineUsers.get(key));
        callback.unregisterCallback(onlineUsers.get(key));
        onlineUsers.remove(key);
        return true;
    }

    public synchronized static String keyToName(SelectionKey key) {
        return onlineUsers.get(key);
    }

    private synchronized static void storeNew(String username, String hash) {
        File usersFile = new File(Const.USERS_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(usersFile, true);
            fos.write(String.format("%s,%s\n", username, hash).getBytes());
            fos.close();
        } catch (IOException e) {
            if (Const.DEBUG)
                e.printStackTrace();
            System.err.println("Users file is not writable, exiting...");
            System.exit(-1);
        }
    }

    public static void restore() throws IOException {
        File usersFile = new File(Const.USERS_FILE);
        if(!usersFile.isFile()) return;
        BufferedReader reader = new BufferedReader(new FileReader(usersFile));
        while (true) {
            String line = reader.readLine();
            if(line == null) break;
            int separator = line.indexOf(",");
            String username = line.substring(0, separator);
            String hash = line.substring(separator+1);
            users.put(username, new User(hash));
        }
        reader.close();
    }

    public static List<String> listUsers() {
        return users.keySet().stream().collect(Collectors.toList());
    }

    public static List<String> listOnlineUsers() {
        return onlineUsers.values().stream().collect(Collectors.toList());
    }

    // // FIXME: optimize this garbage
    // public static Map<String, Boolean> listOnlineUsers() {

    //     // Map<String, List<SelectionKey>> m = onlineUsers.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

    //     Map<String, Boolean> onlineUsersMap = new HashMap<>();
    //     users.keySet().stream().forEach(user -> {
    //         if(onlineUsers.values().contains(user)) onlineUsersMap.put(user, true);
    //         else onlineUsersMap.put(user, false);
    //     });
    //     return onlineUsersMap;
    // }
}

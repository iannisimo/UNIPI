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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import worth.common.CallbackServerInterface;
import worth.common.RegisterServiceInterface;
import worth.server.Utils.Const;
import worth.server.projects.Projects;

/**
 * This class contains the information and functions needed for the users
 */
public class Users {
    private static Map<String, User> users = new ConcurrentHashMap<>();
    private static Map<SelectionKey, String> onlineUsers = new ConcurrentHashMap<>();
    private static Callback callback;

    /**
     * Registers the CallbackService and the RegisterService in the RMI's registry
     * @throws RemoteException
     */
    public static void initCallbackService() throws RemoteException {
        callback = new Callback();
        CallbackServerInterface stub = (CallbackServerInterface) UnicastRemoteObject.exportObject(callback, 0);
        Registry r = LocateRegistry.createRegistry(Const.RMI_PORT);
        r.rebind(CallbackServerInterface.REG, stub);
        RegisterService service = new RegisterService();
        RegisterServiceInterface stubR = (RegisterServiceInterface) UnicastRemoteObject.exportObject(service, 0);
        r.rebind(RegisterServiceInterface.RMI_REG, stubR);
    }

    /**
     * Add a new {@code (user -> hash)} pair in the data structure (registration)
     * @param username
     * @param hash
     */
    protected static void addUser(String username, String hash) {
        if (!username.matches(Const.UNAME_REGEX))
            throw new IllegalArgumentException("Wrong username format");
        if (users.containsKey(username))
            throw new IllegalArgumentException("Username taken");
        storeNew(username, hash);
        users.put(username, new User(hash));
        callback.setStatus(username, false);
    }

    /**
     * Check in a user is in the data structure
     * @param username username whose presence is to be tested
     * @return {@code true} if the username is is in the data structure
     */
    public static boolean exists(String username) {
        return users.containsKey(username);
    }

    /**
     * Try to log a new client
     * @param username identifier to be tested
     * @param hash hash of the user's password
     * @param key {@code SelectionKey} of the requesting client
     * @return {@code true} if the login procedure is succesful
     */
    public static boolean login(String username, String hash, SelectionKey key) {
        if(!users.containsKey(username)) return false;
        if(!users.get(username).login(hash)) return false;
        if(onlineUsers.containsKey(key)) return false;
        if(onlineUsers.containsValue(username)) return false;
        onlineUsers.put(key, username);
        callback.setStatus(username, true);
        return true;
    }

    /**
     * Logout the client associated to {@code key}
     * @param key key of the client
     * @throws IOException
     */
    public static void logout(SelectionKey key) throws IOException {
        Projects.exitAllChats(onlineUsers.get(key));
        callback.unregisterCallback(onlineUsers.get(key));
        onlineUsers.remove(key);
        key.cancel();
        key.channel().close();
    }

    /**
     * Convert a key to the correspondig username
     * @param key {@code SelectionKey} to be converted
     * @return The username associated to the given {@code key}
     */
    public static String keyToName(SelectionKey key) {
        return onlineUsers.get(key);
    }

    /**
     * Store a new {@code (username -> hash)} association to the save file
     * @param username
     * @param hash
     */
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

    /**
     * Restore all the {@code (username -> hash)} associations from the save file
     * @throws IOException
     */
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

    /**
     * @return A list containing all the users registered to the service
     */
    public static List<String> listUsers() {
        return users.keySet().stream().collect(Collectors.toList());
    }

    /**
     * @return A list containing all the users online
     */
    public static List<String> listOnlineUsers() {
        return onlineUsers.values().stream().collect(Collectors.toList());
    }
}

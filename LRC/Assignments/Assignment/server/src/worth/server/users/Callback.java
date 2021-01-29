package worth.server.users;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import worth.common.CallbackInterface;
import worth.common.CallbackServerInterface;
import worth.server.utils.Const;
/**
 * Implementation on the CallbackServerInterface used to notify clients about changes in the users list
 */
public class Callback extends RemoteObject implements CallbackServerInterface {
    
    // Default serial version UID
    private static final long serialVersionUID = 1L;

    private Map<String, CallbackInterface> clients;
    public Callback() {
        super();
        clients = new ConcurrentHashMap<>();
    }

    /**
     * This method gets called from a client to register to the callback functionality.
     * The server saves the association {@code (username -> clientInterface)} for later use and sends the curent Users status to the newly logged user
     * @param username The username of the newly logged user
     * @param clientInterface A reference to the client's callback interface
     * @throws RemoteException
     */
    @Override
    public synchronized void registerCallback(String username, CallbackInterface clientInterface) throws RemoteException {
        clients.put(username, clientInterface);
        clientInterface.setStatus(Users.listUsers(), false);
        clientInterface.setStatus(Users.listOnlineUsers(), true);
        
    }

    /**
     * This method needs to be called to unregister a client from the notifyiable list.
     * @param username Username of the logged-out client
     * @throws RemoteException
     */
    @Override
    public synchronized void unregisterCallback(String username) throws RemoteException {
        clients.remove(username);
        setStatus(username, false);
    }

    /**
     * Notify the status change of a single client
     * @param username User changing status
     * @param online New status
     */
    public synchronized void setStatus(String username, boolean online) {
        this.clients.entrySet().stream().forEach(m -> {
            try {
                m.getValue().setStatus(username, online);
            } catch (RemoteException e){
                if(Const.DEBUG) e.printStackTrace();
                try {
                    unregisterCallback(m.getKey());
                } catch (RemoteException ee) {
                    if(Const.DEBUG) ee.printStackTrace();
                }
            }
        });
    }


    /**
     * Notify the status change of a list of clients
     * @param usernames Users changing status
     * @param online New status
     */
    public synchronized void setStatus(List<String> usernames, boolean online) {
        this.clients.entrySet().stream().forEach(m -> {
            try {
                m.getValue().setStatus(usernames, online);
            } catch (RemoteException e){
                if(Const.DEBUG) e.printStackTrace();
                try {
                    unregisterCallback(m.getKey());
                } catch (RemoteException ee) {
                    if(Const.DEBUG) ee.printStackTrace();
                }
            }
        });
    }
}

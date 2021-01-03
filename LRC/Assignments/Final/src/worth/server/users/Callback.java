package worth.server.users;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import worth.common.CallbackInterface;
import worth.common.CallbackServerInterface;
import worth.server.Const;

public class Callback extends RemoteObject implements CallbackServerInterface {
    
    // Default serial version UID
    private static final long serialVersionUID = 1L;

    private Map<String, CallbackInterface> clients;
    public Callback() {
        super();
        clients = new HashMap<>();
    }

    @Override
    public synchronized void registerCallback(String username, CallbackInterface clientInterface) throws RemoteException {
        clients.put(username, clientInterface);
        clientInterface.setStatus(Users.listUsers(), false);
        clientInterface.setStatus(Users.listOnlineUsers(), true);
        
    }

    @Override
    public synchronized void unregisterCallback(String username) throws RemoteException {
        clients.remove(username);
        setStatus(username, false);
    }

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

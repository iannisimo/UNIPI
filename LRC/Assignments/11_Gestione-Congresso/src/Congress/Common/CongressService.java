package Congress.Common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CongressService extends Remote {

    /**
     * @param name: Should not be empty
     * @param day: Should be in range of the available days
     * @param session: Should be in range of the available sessions
     * @return Position; -1 If the params are not applicable; -2 If a session is full
     * @throws RemoteException 
     */
    public int addSpeaker(String name, int day, int session) throws RemoteException;

    /**
     * @param day
     * @param session
     * @return Speakers registered for the selection; null if not possible.
     * @throws RemoteException
     */
    public List<String> getSession(int day, int session) throws RemoteException;

    /**
     * @param day
     * @return Speakers registered for the selection; null if not possible.
     * @throws RemoteException
     */
    public List<List<String>> getSession(int day) throws RemoteException;

    /**
     * @return Speakers registered for the selection; null if not possible.
     * @throws RemoteException
     */
    public List<List<List<String>>> getSession() throws RemoteException;

    /**
     * @return Get the number of days of the congress.
     */
    public int getDays() throws RemoteException;

    /**
     * @return Get the number of sessions on each day.
     */
    public int getSessions() throws RemoteException;
}
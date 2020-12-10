package Congress.Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import Congress.Common.CongressService;

public class ServiceImpl implements CongressService {

    List<List<List<String>>> structure;

    public ServiceImpl() {
        structure = new ArrayList<>();
        for(int i = 0; i < Const.DAYS; i++) {
            List<List<String>> sessions = new ArrayList<>();
            for (int j = 0; j < Const.SESSIONS_PER_DAY; j++) {
                sessions.add(new ArrayList<>());
            }
            structure.add(sessions);
        }
    }

    @Override
    public int addSpeaker(String name, int day, int session) throws RemoteException {
        if(name.equals("")) return -1;
        if(day < 0 || day >= Const.DAYS) return -1;
        if(session < 0 || session >= Const.SESSIONS_PER_DAY) return -1;
        if(structure.get(day).get(session).size() >= Const.MAX_PER_SESSION) return -2;
        structure.get(day).get(session).add(name);
        return structure.get(day).get(session).size() - 1;
    }

    @Override
    public List<String> getSession(int day, int session) throws RemoteException {
        if(day < 0 || day >= Const.DAYS) return null;
        if(session < 0 || session >= Const.SESSIONS_PER_DAY) return null;
        return new ArrayList<>(structure.get(day).get(session));
    }

    @Override
    public List<List<String>> getSession(int day) throws RemoteException {
        if(day < 0 || day >= Const.DAYS) return null;
        return new ArrayList<>(structure.get(day));
    }

    @Override
    public List<List<List<String>>> getSession() throws RemoteException {
        return new ArrayList<>(structure);
    }

    @Override
    public int getDays() {
        return Const.DAYS;
    }

    @Override
    public int getSessions() {
        return Const.SESSIONS_PER_DAY;
    }
    
}

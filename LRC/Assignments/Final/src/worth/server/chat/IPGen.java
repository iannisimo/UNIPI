package worth.server.chat;

import java.util.ArrayList;
import java.util.List;

import worth.server.Const;

public class IPGen {
    private static int lastPort = Const.MIN_MCAST_PORT;
    private static List<Integer> freedPorts = new ArrayList<>();

    public static String getNewIPPort() {
        int port = 0;
        if(!freedPorts.isEmpty()) port = freedPorts.remove(0);
        else port = ++lastPort;
        if(port > Const.MAX_MCAST_PORT) return null;
        return Const.MCAST_IP + ":" + port;
    }

    public static void freeIP(String IP) {
        freedPorts.add(Integer.valueOf(IP.split(":")[1]));
    }
}

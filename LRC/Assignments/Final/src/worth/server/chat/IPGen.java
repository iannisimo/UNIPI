package worth.server.chat;

import java.util.ArrayList;
import java.util.List;

import worth.server.Const;

/**
 * This class is used to retreive an unused multicast address for usage in a project's chat
 * This implementation just changes the port number and has roughly 16000 possible combinations before it runs out of port but could easly be expanded to use multiple IPs 
 */
public class IPGen {
    private static int lastPort = Const.MIN_MCAST_PORT;
    private static List<Integer> freedPorts = new ArrayList<>();

    /**
     * @return A new IP for a project's chat
     */
    public static String getNewIPPort() {
        int port = 0;
        if(!freedPorts.isEmpty()) port = freedPorts.remove(0);
        else port = ++lastPort;
        if(port > Const.MAX_MCAST_PORT) return null;
        return Const.MCAST_IP + ":" + port;
    }

    /**
     * Free the IP for reuse
     * @param IP
     */
    public static void freeIP(String IP) {
        freedPorts.add(Integer.valueOf(IP.split(":")[1]));
    }
}

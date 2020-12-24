package Worth.Client;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import Worth.Common.RegisterServiceInterface;

public class Utils {
    public static void parseArgs(String[] args) {
        try {
            CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Error while trying to  parse arguments");
        }
    }

    public static boolean register(String username, char[] password) {
        RegisterServiceInterface rs;
        Registry r;
        try {
            r = LocateRegistry.getRegistry(Const.IP, Const.RMI_PORT);
            rs = (RegisterServiceInterface) r.lookup(Const.RMI_REG);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            return rs.register(username, new String(password));
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}

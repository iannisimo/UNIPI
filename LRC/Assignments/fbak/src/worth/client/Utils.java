package worth.client;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import worth.common.RegisterServiceInterface;

public class Utils {
    public static void parseArgs(String[] args) {
        try {
            CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Error while trying to parse arguments");
        }
    }

    public static boolean register(String username, char[] password) {
        RegisterServiceInterface rs;
        Registry r;
        try {
            r = LocateRegistry.getRegistry(Const.IP, Const.RMI_PORT);
            rs = (RegisterServiceInterface) r.lookup(Const.RMI_REG);
        } catch (RemoteException | NotBoundException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        try {
            return rs.register(username, hashPass(new String(password)));
        } catch (RemoteException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
    }

    public static String hashPass(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            if(Const.DEBUG) e.printStackTrace();
            return null;
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return Base64.getEncoder().encodeToString(digest);
    }
}

package worth.client;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import worth.client.tcp.Response;
import worth.client.users.Callback;
import worth.common.CallbackInterface;
import worth.common.CallbackServerInterface;
import worth.common.RegisterServiceInterface;

public class Utils {
    public static void parseArgs(String[] args) {
        try {
            CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Error while trying to parse arguments");
        }
    }

    public static Response register(String username, char[] password) {
        RegisterServiceInterface rs;
        Registry r;
        try {
            r = LocateRegistry.getRegistry(Const.IP, Const.RMI_PORT);
            rs = (RegisterServiceInterface) r.lookup(RegisterServiceInterface.RMI_REG);
        } catch (RemoteException | NotBoundException e) {
            if (Const.DEBUG) e.printStackTrace();
            return new Response(false, "Cannot reach the server");
        }
        try {
            rs.register(username, hashPass(new String(password)));
        } catch (RemoteException | IllegalArgumentException e) {
            if(Const.DEBUG) e.printStackTrace();
            return new Response(false, e.getMessage());
        }
        return new Response(true);
    }

    public static void registerCallbackService(String username) throws RemoteException, NotBoundException {
        Registry r = LocateRegistry.getRegistry(Const.RMI_PORT);
        CallbackServerInterface server = (CallbackServerInterface) r.lookup(CallbackServerInterface.REG);
        CallbackInterface callback = new Callback();
        CallbackInterface stub = (CallbackInterface) UnicastRemoteObject.exportObject(callback, 0);
        server.registerCallback(username, stub);
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

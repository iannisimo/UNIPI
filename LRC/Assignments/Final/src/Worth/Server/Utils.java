package Worth.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import Worth.Common.RegisterServiceInterface;
import Worth.Server.Users.RegisterService;

public class Utils {

    public static Boolean setProperty(String key, String val) {
        try {
            Properties p = new Properties();
            File cfgFile = new File(Const.CFG_FILE);
            if (!cfgFile.isFile()) {
                cfgFile.createNewFile();
            }
            FileInputStream fis = new FileInputStream(cfgFile);
            p.load(fis);
            p.setProperty(key, val);
            fis.close();
            FileOutputStream fos = new FileOutputStream(cfgFile);
            p.store(fos, "Cfg Update");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getProperty(String key) {
        try {
            Properties p = new Properties();
            FileInputStream fis = new FileInputStream(new File(Const.CFG_FILE));
            p.load(fis);
            return p.getProperty(key);
        } catch (IOException e) {
            return "";
        }
    }

    public static void parseArgs(String[] args) {
        try {
            CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Error while trying to  parse arguments");
        }
    }

    public static void setupEnviroment() {
        File resDir = new File(Const.RES_FOLDER);
        if (!resDir.exists())
            resDir.mkdir();
    }

    public static boolean registerRegisterService() {
        int rmiPort = Integer.parseInt(getProperty(Const.RMI_PORT_KEY));
        try {
            RegisterService service = new RegisterService();
            RegisterServiceInterface stub = (RegisterServiceInterface) UnicastRemoteObject.exportObject(service, 0);
            Registry r = LocateRegistry.createRegistry(rmiPort);
            r.rebind(Const.RMI_REG, stub);
        } catch (RemoteException e) {
            return false;
        }
        return true;
    }
}

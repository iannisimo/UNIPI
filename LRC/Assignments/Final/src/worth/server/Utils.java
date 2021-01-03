package worth.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import worth.common.RegisterServiceInterface;
import worth.server.users.RegisterService;


public class Utils {

    private Properties prop;
    public void setProp(String key, String val) {
        prop.setProperty(key, val);
    }
    public String getProp(String key) {
        return prop.getProperty(key);
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
        if(!resDir.exists())
            resDir.mkdir();
        File projDir = new File(Const.PROJECTS_FOLDER);
        if(!projDir.exists())
            projDir.mkdir();
        // Users.init();
    }

    public static boolean registerRegisterService() {
        try {
            RegisterService service = new RegisterService();
            RegisterServiceInterface stub = (RegisterServiceInterface) UnicastRemoteObject.exportObject(service, 0);
            Registry r = LocateRegistry.createRegistry(Const.RMI_PORT);
            r.rebind(RegisterServiceInterface.RMI_REG, stub);
        } catch (RemoteException e) {
            return false;
        }
        return true;
    }

    public static void clearBuf(ByteBuffer buf) {
        buf = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
    }

    public static String readFile(String fileName) throws IOException {
        File f = new File(fileName);
        if(!f.isFile()) throw new FileNotFoundException();
        FileInputStream fis = new FileInputStream(f);
        byte[] read = fis.readAllBytes();
        fis.close();
        return new String(read);
    }
}
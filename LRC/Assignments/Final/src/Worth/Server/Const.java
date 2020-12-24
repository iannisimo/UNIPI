package Worth.Server;

import java.io.File;

public class Const {
    public static int TCP_PORT = 9999;
    public static int RMI_PORT = 9998;

    public static final String RES_FOLDER = "res" + File.separator;
    public static final String USERS_FILE = RES_FOLDER + "users.worth";
    public static final String CFG_FILE = RES_FOLDER + "cfg.properties";
    public static final String RMI_REG = "REG_SERVICE";
    public static final String RMI_PORT_KEY = "rmi-port";
    public static final String TCP_PORT_KEY = "tcp-port";
}

package Worth.Server;

import java.io.File;

public class Const {
    public static int TCP_PORT = 9999;
    public static int RMI_PORT = 9998;
    public static boolean DEBUG = false;

    public static final String RES_FOLDER = "res" + File.separator;
    public static final String PROJECTS_FOLDER = RES_FOLDER + "projects" + File.separator;
    public static final String PROJECT_FOLDER(String name) {return PROJECTS_FOLDER + name + File.separator;} 
    public static final String PROJECT_INFO(String name) {return PROJECT_FOLDER(name) + "project";} 
    public static final String PROJECT_CARD(String name, String card) {return PROJECTS_FOLDER + name + File.separator + card + ".card";} 
    public static final String USERS_FILE = RES_FOLDER + "users.worth";
    public static final String CFG_FILE = RES_FOLDER + "cfg.properties";
    public static final String RMI_REG = "REG_SERVICE";
    public static final String RMI_PORT_KEY = "rmi-port";
    public static final String TCP_PORT_KEY = "tcp-port";

    public static final int BYTEBUF_SIZE = 1024;
}
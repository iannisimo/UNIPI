package worth.server;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Const {
    public static int TCP_PORT = 9999;
    public static int RMI_PORT = 9998;
    public static boolean DEBUG = false;
    public static int CORE_POOL_SIZE = 1;
    public static int MAX_POOL_SIZE = 4;
    
    public static final String RES_FOLDER = "files" + File.separator;
    public static final String PROJECTS_FOLDER = RES_FOLDER + "projects" + File.separator;
    public static final String PROJECT_FOLDER(String project) {return PROJECTS_FOLDER + project + File.separator;} 
    public static final String PROJECT_INFO(String project) {return PROJECT_FOLDER(project) + "project.info";} 
    public static final String PROJECT_CARD(String project, String card) {return PROJECTS_FOLDER + project + File.separator + card + ".card";} 
    public static final String USERS_FILE = RES_FOLDER + "users.verysecureformat";
    public static final String UNAME_REGEX = "^[a-zA-Z0-9._-]{1,}$";
    
    public static final int BYTEBUF_SIZE = 8192;
    public static final int POOL_TIMEOUT = 5;
    public static final TimeUnit POOL_TIMEUNIT = TimeUnit.SECONDS;

    public static final String MCAST_IP = "224.0.1.30";
    public static final int MIN_MCAST_PORT = 49151;
    public static final int MAX_MCAST_PORT = 65535;
}
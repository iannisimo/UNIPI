package worth.server.utils;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.SingleArgument;
import com.github.jankroken.commandline.annotations.Toggle;

public class Arguments {
    @Option
    @LongSwitch("help")
    @ShortSwitch("h")
    @Toggle(true)
    public void showHelp(boolean help) {
        if(help) {
            System.out.print("Accepted arguments:\n" +
                "\t-h:\tShow this help message\n" + 
                "\t-rp:\tSet the port for RMI connections\n" +
                "\t-tp:\tSet the port fot TCP connections\n" +
                "\t-d:\tSet the program in debug mode\n");
            System.exit(0);
        }
    }

    @Option
    @LongSwitch("rmi-port")
    @ShortSwitch("rp")
    @SingleArgument
    public void changeRmiPort(String rp) {
        Const.RMI_PORT = Integer.parseInt(rp);
    }
    
    @Option
    @LongSwitch("tcp-port")
    @ShortSwitch("tp")
    @SingleArgument
    public void changeTcpPort(String tp) {
        Const.TCP_PORT = Integer.parseInt(tp);
    }

    @Option
    @LongSwitch("debug")
    @ShortSwitch("d")
    @Toggle(true)
    public void setDebug(boolean debug) {
        Const.DEBUG = debug;
    }
}

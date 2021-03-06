package Worth.Server;

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
            // System.out.println("You can use the option --port <p> to change the server port");
        }
    }

    @Option
    @LongSwitch("rmi-port")
    @ShortSwitch("rp")
    @SingleArgument
    public void changeRmiPort(String rp) {
        Const.RMI_PORT = Integer.parseInt(rp);
        // Utils.setProperty(Const.RMI_PORT_KEY, p);
    }
    
    @Option
    @LongSwitch("tcp-port")
    @ShortSwitch("tp")
    @SingleArgument
    public void changeTcpPort(String tp) {
        Const.TCP_PORT = Integer.parseInt(tp);
        // Utils.setProperty(Const.TCP_PORT_KEY, tp);
    }

    @Option
    @LongSwitch("debug")
    @ShortSwitch("d")
    @Toggle(true)
    public void setDebug(boolean debug) {
        Const.DEBUG = debug;
    }
}

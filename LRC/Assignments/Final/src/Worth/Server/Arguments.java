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
            System.out.println("You can use the option --port <p> to change the server port");
        }
    }

    @Option
    @LongSwitch("port")
    @ShortSwitch("p")
    @SingleArgument
    public void changePort(String p) {
        Utils.setProperty("rmi-port", p);
    }
    
    @Option
    @LongSwitch("tcp-port")
    @ShortSwitch("tp")
    @SingleArgument
    public void changeTcpPort(String tp) {
        Utils.setProperty("tcp-port", tp);
    }
}

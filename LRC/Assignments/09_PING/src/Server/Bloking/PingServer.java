package Server.Bloking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import Server.NetErr;
import Server.ReqResp;

public class PingServer {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java PingServer port [seed]");
            System.exit(1);
        }

        final int PORT;
        final int SEED;

        try {
            PORT = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            System.out.println("ERR -arg 1");
            return;
        }
        try {
            SEED = (args.length >= 2) ? Integer.parseInt(args[1]) : 1337;
        } catch(NumberFormatException e) {
            System.out.println("ERR -arg 2");
            return;
        }

        final NetErr netErr = new NetErr(SEED);

        DatagramChannel channel;
        try {
            channel = DatagramChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(PORT);
            channel.socket().bind(socketAddress);
            channel.configureBlocking(true);
            System.out.println("*** PingServer Ready ***");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while(true) {
            try {
                ReqResp rr = new ReqResp();
                rr.socketAddress = channel.receive(rr.req);
                rr.req.flip();
                int wait = netErr.sendAfter();
                if(wait != -1) {
                    System.out.printf("%s> %s ACTION: delayed %d ms\n", rr.socketAddress.toString().substring(1), new String(rr.req.array()), wait);
                    rr.resp = rr.req;
                    new Thread(new ReplyTask(channel, rr, wait)).start();;
                } else {
                    System.out.printf("%s> %s ACTION: not sent\n", rr.socketAddress.toString().substring(1), new String(rr.req.array()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

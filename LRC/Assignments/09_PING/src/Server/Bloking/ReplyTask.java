package Server.Bloking;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

import Server.ReqResp;

public class ReplyTask implements Runnable {
    int wait;
    ReqResp rr;
    DatagramChannel channel;

    public ReplyTask(DatagramChannel channel, ReqResp rr, int wait) {
        this.channel = channel;
        this.rr = rr;
        this.wait = wait;
    }

    public void run() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.send(rr.resp, rr.socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Server.NonBlocking;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import Server.ReqResp;

public class KeyWait implements Runnable {
    private DatagramChannel channel;
    private Selector selector;
    private ReqResp rr;
    private int time;

    public KeyWait(DatagramChannel channel, Selector selector, ReqResp rr, int time) {
        this.channel = channel;
        this.selector = selector;
        this.rr = rr;
        this.time = time;
    }

    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.register(selector, SelectionKey.OP_WRITE, rr);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }
}

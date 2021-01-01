package Worth.Server.TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import Worth.Server.Const;
import Worth.Server.Utils;

public class Connection implements Runnable {

    private Selector selector;

    @Override
    public void run() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(Const.TCP_PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println("Failed to open ServerSocket, maybe the port is in use");
            return;
        }
        while(true) {
            try {
                selector.select();
            } catch (IOException e) {
                break;
            }
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                try {
                    if(key.isAcceptable()) {
                        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ, new Attachment());
                    } else if(key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        Attachment a = (Attachment) key.attachment();
                        Utils.clearBuf(a.request);
                        channel.read(a.request);
                        Commands.recognize(a, key);
                        channel.register(selector, SelectionKey.OP_WRITE, a);
                    } else if(key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        Attachment a = (Attachment) key.attachment();
                        channel.write(a.getResponse());
                        channel.register(selector, SelectionKey.OP_READ, a);
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ee) {

                    }
                }
            }
        }
    }
    
}

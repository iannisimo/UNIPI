package worth.server.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import worth.common.CMD;
import worth.common.Status;
import worth.server.Const;
import worth.server.projects.Projects;
import worth.server.users.Users;

public class Connection implements Runnable {

    protected Selector selector;

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
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    } else if(key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer a = (ByteBuffer) key.attachment();
                        channel.write(a);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if(key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
                        channel.read(buf);
                        ByteBuffer resp = execute(buf, key);
                        channel.register(selector, SelectionKey.OP_WRITE, resp);
                    }
                } catch (IOException | java.nio.BufferUnderflowException e) {
                    if(Const.DEBUG) e.printStackTrace();
                    try {
                        Users.logout(key);
                    } catch (IOException ee) {
                        if(Const.DEBUG) ee.printStackTrace();
                    }
                }
            }
        }
    }

    private ByteBuffer execute(ByteBuffer req, SelectionKey key) throws java.nio.BufferUnderflowException, IOException {
        req.flip();
        byte cmd = req.get();
        List<String> params = readResponse(req);

        switch (cmd) {
            case CMD.LOGIN: {
                if(params.size() < 2) break;
                String username = params.get(0);
                String hash = params.get(1);
                if(Users.login(username, hash, key)) return okBuf();
                break;
            }
            case CMD.LOGOUT: {
                if(!Users.logout(key)) break;
                return okBuf();
            }
            case CMD.LIST_PROJECTS: {
                String member = Users.keyToName(key);
                if(member == null) break;
                return generateResponse(Projects.findProjects(member));
            }
            case CMD.CREATE_PROJECT: {
                if(params.size() < 1) break;
                String project = params.get(0);
                String member = Users.keyToName(key);
                if(!Projects.addProject(project, member)) break;
                return okBuf();
            }
            case CMD.ADD_MEMBER: {
                if(params.size() < 2) break;
                String project = params.get(0);
                String member = params.get(1);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                if(!Projects.addMember(project, member)) break;
                return okBuf();
            }
            case CMD.SHOW_MEMBERS: {
                if(params.size() < 1) break;
                String project = params.get(0);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                return generateResponse(Projects.getMembers(project));
            }
            case CMD.SHOW_CARDS: {
                if(params.size() < 1) break;
                String project = params.get(0);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                return generateResponse(Projects.getCards(project));
            }
            case CMD.SHOW_CARD: {
                if(params.size() < 2) break;
                String project = params.get(0);
                String card = params.get(1);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                return generateResponse(Projects.getCardInfo(project, card));
            }
            case CMD.ADD_CARD: {
                if(params.size() < 3) break;
                String project = params.get(0);
                String card = params.get(1);
                String description = params.get(2);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                if(!Projects.addCard(project, card, description)) break;
                return okBuf();
            }
            case CMD.MOVE_CARD: {
                if(params.size() < 3) break;
                String project = params.get(0);
                String card = params.get(1);
                byte ft = params.get(2).getBytes()[0];
                Status from = Status.fromOrdinal(ft >> 4);
                Status to = Status.fromOrdinal(ft & 0x0F);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                if(!Projects.moveCard(project, card, from, to)) break;
                return okBuf();
            }
            case CMD.GET_CARD_HISTORY: {
                if(params.size() < 2) break;
                String project = params.get(0);
                String card = params.get(1);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                if(!Projects.exists(project, card)) break;
                List<String> history = new ArrayList<>();
                for(Status s : Projects.getCardHistory(project, card)) {
                    history.add(s.toString());
                }
                return generateResponse(history);
            }
            case CMD.DELETE_PROJECT: {
                if(params.size() < 1) break;
                String project = params.get(0);
                if(!Projects.isMember(project, Users.keyToName(key))) break;
                if(!Projects.deleteProject(project)) break;
                return okBuf();
            }
        }
        return errorBuf();
    }

    private ByteBuffer generateResponse(List<String> messages) {
        if(messages == null) return errorBuf();
        ByteBuffer response = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        response.put(CMD.OK);
        for(String message : messages) {
            response.put(message.getBytes());
            response.put(CMD.SPACER);
        }
        return response.flip();
    }

    private static List<String> readResponse(ByteBuffer bb) {
        List<String> response = new ArrayList<>();
        int len;
        while((len = findSeparator(bb)) != 0) {
            byte[] ba = new byte[len];
            bb.get(ba, 0, len);
            response.add(new String(ba));
            bb.get();
        }
        return response;
    }

    private static int findSeparator(ByteBuffer bb) {
        if(!bb.hasRemaining()) return 0;
        int i = 0;
        bb.mark();
        while(bb.get() != CMD.SPACER) i++;
        bb.reset();
        return i;
    }    

    private ByteBuffer errorBuf() {
        return ByteBuffer.allocate(2).put(CMD.ERROR).flip();
    }

    private ByteBuffer okBuf() {
        return ByteBuffer.allocate(2).put(CMD.OK).flip();
    }
}

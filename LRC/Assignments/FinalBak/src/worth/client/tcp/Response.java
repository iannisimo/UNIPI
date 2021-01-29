package worth.client.tcp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This structure contains a TCP response staus along with the optional messages received
 */
public class Response {
    private final boolean status;
    private final List<String> messages;

    public Response(boolean status) {
        this.status = status;
        this.messages = new ArrayList<>();
    }

    public Response(boolean status, List<String> messages) {
        this(status);
        this.messages.addAll(messages);
    }

    public Response(boolean status, String message) {
        this(status);
        this.messages.add(message);
    }

    public boolean getStatus() {
        return this.status;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public Iterator<String> getMessageIterator() {
        return this.messages.iterator();
    }

    public String getMessage() {
        return hasMessages() ? this.messages.get(0) : "";
    }

    public boolean hasMessages() {
        return !this.messages.isEmpty();
    }

    public int size() {
        return this.messages.size();
    }
    
}
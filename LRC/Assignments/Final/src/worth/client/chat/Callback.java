package worth.client.chat;

public interface Callback {
    public void newMessage(String message);
    public void chatUnreachable();
}

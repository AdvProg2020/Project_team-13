package Models;

public class ChatMessage {
    private String username;
    private String receiverUsername;
    private String content;

    public ChatMessage(String username, String receiverUsername, String content) {
        this.username = username;
        this.receiverUsername = receiverUsername;
        this.content = content;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

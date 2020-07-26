package Models;

public class ChatMessage {
    private String username;
    private String receiverUsername;
    private String content;
    private String objectId;

    public ChatMessage(String username, String receiverUsername, String content,String objectId) {
        this.username = username;
        this.objectId = objectId;
        this.receiverUsername = receiverUsername;
        this.content = content;
    }

    public String getObjectId() {
        return objectId;
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

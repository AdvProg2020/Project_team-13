package Models;

public class Message {
    private String content;
    private String userName,passWord;

    public Message(String content, String userName, String passWord) {
        this.content = content;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getContent() {
        return content;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }
}

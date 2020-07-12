package View;

import Controller.Client.ClientController;
import Controller.Client.UserController;
import Models.ChatMessage;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CustomerChatMenu extends Menu {
    GridPane userInfoGridPane;
    WebView chatMessages = new WebView();
    public CustomerChatMenu(Stage stage) {
        super(stage);
        this.stage = stage;
        userInfoGridPane = new GridPane();
        if (ClientController.getInstance().getMediaPlayer() != null)
            ClientController.getInstance().getMediaPlayer().stop();
        ClientController.getInstance().setMediaPlayer(new MediaPlayer(usersSong));
        ClientController.getInstance().getMediaPlayer().setVolume(0.02);
        ClientController.getInstance().getMediaPlayer().play();
        ClientController.getInstance().getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
    }

    private void setCenterGridPane() {
        setNewMessage();
        chatMessages.setMaxHeight(300);
        chatMessages.setMaxWidth(300);
        GridPane chatPane=new GridPane();
        TextField inputMessage=new TextField();
        inputMessage.setPromptText("Enter your message...");
        inputMessage.setMinWidth(260);
        Button sendMessageButton=new Button("Send");
        sendMessageButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(!inputMessage.getText().equals("")){
                    UserController.getInstance().sendChatMessage(inputMessage.getText());
                    inputMessage.setText("");
                    setNewMessage();
                }
            }
        });
        chatPane.add(chatMessages,0,0,10,1);
        chatPane.add(inputMessage,0,1,8,1);
        chatPane.add(sendMessageButton,9,1,2,1);
        centerGridPane.add(chatPane,1,0);

    }
    public void setNewMessage(){
        String messages="<html style=\"background-color: #ECD5DC;\">";
        ArrayList<ChatMessage> chats= UserController.getInstance().getChats().get(UserController.getInstance().getCurrentChatUser());
        if(chats!=null) {
            for (ChatMessage chatMessage : chats) {
                if (chatMessage.getUsername().equals(ClientController.getInstance().getCurrentUser().getUsername()))
                    messages += "<p style=\"background-color: rgb(0,150,200);text-align: right;font-size:12px;\">" + chatMessage.getUsername() + "<br>" + chatMessage.getContent() + "</p>";
                else
                    messages += "<p style=\"text-align: left;background-color: lightgreen;font-size:12px;\">" + chatMessage.getUsername() + "<br>" + chatMessage.getContent() + "</p>";

            }
        }
        messages+="</html>";
        refreshChatMessages(messages);
    }
    public void refreshChatMessages(String messages) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                chatMessages.getEngine().loadContent(messages);
            }
        });
    }

    protected void setPageGridPain() {
        pageGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.NEVER, VPos.CENTER, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkPasswordIsvalid(String word) {
        if (word.length() > 8 && word.length() < 18) {
            return true;
        }
        return false;
    }

    private boolean checkNameIsvalid(String name) {
        if (Pattern.matches("(([a-z]|[A-Z])+ )*(([a-z]|[A-Z])+)", name) && !name.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkEmailIsvalid(String email) {
        if (Pattern.matches("\\w+\\.?\\w*@\\w+\\.\\w+", email)) {
            return true;
        }
        return false;
    }
}

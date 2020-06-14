package View;

import Controller.Client.ClientController;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainMenu extends Menu {
    public MainMenu(Stage stage) {
        super(stage);
        if(ClientController.getInstance().getMediaPlayer()!=null)
            ClientController.getInstance().getMediaPlayer().stop();
        ClientController.getInstance().setMediaPlayer(new MediaPlayer(mainSong));
        ClientController.getInstance().getMediaPlayer().setVolume(0.04);
        ClientController.getInstance().getMediaPlayer().play();
        ClientController.getInstance().getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        setScene();
    }
}

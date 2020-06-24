package View;

import Controller.Client.ClientController;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class MainMenu extends Menu {
    public MainMenu(Stage stage) {
        super(stage);
        if (ClientController.getInstance().getMediaPlayer() != null)
            ClientController.getInstance().getMediaPlayer().stop();
        ClientController.getInstance().setMediaPlayer(new MediaPlayer(mainSong));
        ClientController.getInstance().getMediaPlayer().setVolume(0.04);
        ClientController.getInstance().getMediaPlayer().play();
        ClientController.getInstance().getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        setScene();
    }
    @Override
    public void setScene() {
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        centerGridPane.add(new ImageView(new Image("file:src/main_background.jpg")),0,0);
       // centerGridPane.setBackground(new Background(new BackgroundImage(new Image("file:src/main_background.jpg"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        ImageView storeAnim=new ImageView(new Image("file:src/cart_anim.gif"));
        storeAnim.setFitWidth(200);
        storeAnim.setFitHeight(200);
        GridPane mainGridPane=new GridPane();
        mainGridPane.add(storeAnim,10,0);
        mainGridPane.setHgap(30);
        Text welcomeText=new Text("Welcome to our Store ;)");
        welcomeText.setStyle("-fx-font-size: 20;-fx-font-weight: bold");
        welcomeText.setFill(Color.WHITE);
        mainGridPane.add(welcomeText,10,1);
        centerGridPane.add(mainGridPane,0,0);
        bottomGridPane.setStyle("-fx-background-color: rgb(45,156,240);");
        bottomGridPane.getRowConstraints().add(new RowConstraints(15, Control.USE_COMPUTED_SIZE, 15, Priority.NEVER, VPos.CENTER, false));
        scene.setRoot(pageGridPane);

    }

}

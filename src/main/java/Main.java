import Controller.Client.ClientController;
import Controller.Server.ServerController;
import Controller.Server.UserCenter;
import View.MainMenu;
import View.ManagerRegisterMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) {
        ClientController.getInstance().connectToServer();
        primaryStage.setTitle("Pms.com");
        if (UserCenter.getIncstance().getAllManager() != null
                && !UserCenter.getIncstance().getAllManager().isEmpty()) {
            new MainMenu(primaryStage).execute();
        }else {
            new ManagerRegisterMenu(primaryStage).execute();
        }
    }
}

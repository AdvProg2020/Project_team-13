import Controller.Client.ClientController;
import Controller.Client.UserController;
import Controller.Server.ServerController;
import Controller.Server.UserCenter;
import View.MainMenu;
import View.ManagerRegisterMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) {
        ClientController.getInstance().connectToServer();
        primaryStage.setTitle("SPM.com");
        UserController.getInstance().getCountOfManagerUsers();
//        System.out.println(UserController.getInstance().getAllManagers().size());
        if (UserController.getInstance().getManagerCount()>0) {
            new MainMenu(primaryStage).execute();
        } else {
            new ManagerRegisterMenu(primaryStage).execute();
        }
        System.out.println(UserController.getInstance().getManagerCount());
    }
}

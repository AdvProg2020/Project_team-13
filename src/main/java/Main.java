import Controller.Server.ServerController;
import View.EnterMenuScene;
import View.ManagerMenuScene;
import View.SellerMenuScene;
import View.UserMenuScene;
import View2.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
      launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        new ManagerMenuScene(primaryStage).execute();
    }
}

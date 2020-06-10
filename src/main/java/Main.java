import Controller.Server.ServerController;
import View.EnterMenuScene;
import View.LoginMenu;
import View.RegisterMenu;
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
        new RegisterMenu(primaryStage).execute();
    }
}

import Controller.Server.ServerController;
import View.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
      launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ServerController().runServer();
        primaryStage.setTitle("Hello World");
        new CreateCategoryMenu(primaryStage).execute();
    }
}

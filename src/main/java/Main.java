import View.CustomerRegisterMenu;
import View.LoginMenu;
import View.MainMenu;
import View.RegisterMenu;
import javafx.application.Application;
import javafx.stage.Stage;

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

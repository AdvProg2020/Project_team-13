import Controller.Server.ServerController;
import Controller.Server.UserCenter;
import Models.Product.Cart;
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
        primaryStage.setTitle("Pms.com");
        if (UserCenter.getIncstance().getAllManager() != null
                && !UserCenter.getIncstance().getAllManager().isEmpty()) {
            new MainMenu(primaryStage).execute();
        }else {
            new ManagerRegisterMenu(primaryStage).execute();
        }
    }
}

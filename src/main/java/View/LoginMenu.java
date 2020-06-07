package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginMenu extends EnterMenuScene{
    private TextField userName;
    private TextField passWord;
    private Button loginButton;
    private Hyperlink createNewAccount;


    public LoginMenu(Stage stage) {
        super(stage);
        setTheCenterOfThePage();
    }

    private void setTheCenterOfThePage() {

    }


    @Override
    public void execute() {

    }
}

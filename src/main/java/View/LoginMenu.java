package View;

import Controller.Client.LoginController;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginMenu extends Menu{
    private TextField userName;
    private PasswordField passWord;
    private Button loginButton;
    private Hyperlink createNewAccount;
    GridPane userInfoGridPane;

    public LoginMenu(Stage stage) {
        super(stage);
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        userInfoGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        setScene();
    }

    public void setScene() {
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        userInfoGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        scene.setRoot(pageGridPane);
    }


    private void setCenterGridPane() {
        Label userNameLabel = new Label("Username :");
        Label passWordLabel = new Label("Password :");
        userNameLabel.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        passWordLabel.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        Label login = new Label("Login");
        login.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 23));
        userName = new TextField();
        passWord = new PasswordField();
        Text alertText = new Text();
        userName.setPromptText("Enter Username...");
        passWord.setPromptText("Enter Password...");
        userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
        passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
        loginButton = new Button("Login");
        loginButton.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        loginButton.setStyle("-fx-background-color: #E85D9E");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setMaxSize(400, 50);
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!userName.getText().trim().isEmpty() && !passWord.getText().trim().isEmpty())
                    LoginController.getInstance().login(userName.getText().trim(), passWord.getText().trim());
                else {

                }
            }
        });
        login.setAlignment(Pos.TOP_CENTER);
        Text textOr = new Text("OR");
        textOr.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
        login.setAlignment(Pos.TOP_CENTER);
        userInfoGridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(login, HPos.CENTER);
        GridPane.setValignment(login, VPos.CENTER);
        GridPane.setHalignment(textOr, HPos.CENTER);
        userInfoGridPane.getColumnConstraints().add(new ColumnConstraints());
        createNewAccount = new Hyperlink();
        createNewAccount.setText("Create New Account");
        createNewAccount.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        createNewAccount.setTextFill(Color.BLACK);
        createNewAccount.setUnderline(true);
        userInfoGridPane.setVgap(20);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(500);
        userInfoGridPane.setMinHeight(450);
        userInfoGridPane.getColumnConstraints().add(new ColumnConstraints(300, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        GridPane.setHalignment(createNewAccount, HPos.CENTER);
        GridPane.setHalignment(alertText, HPos.CENTER);
        userInfoGridPane.add(login, 0, 0,2,1);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(500);
        userInfoGridPane.add(userNameLabel, 0, 1);
        userInfoGridPane.add(passWordLabel, 0, 2);
        userInfoGridPane.add(userName, 1, 1);
        userInfoGridPane.add(passWord, 1, 2 );
        userInfoGridPane.add(loginButton, 0, 5,2,1);
        userInfoGridPane.add(textOr, 0, 6, 2, 1);
        userInfoGridPane.add(createNewAccount, 0,7,2,1);
        userInfoGridPane.add(alertText, 0,8,2,1);
        GridPane.setValignment(textOr, VPos.CENTER);
        GridPane leftGridPane= new GridPane();
        GridPane upGridPane= new GridPane();
        upGridPane.setMinHeight(55);
        leftGridPane.setMinWidth(170);
        userInfoGridPane.setStyle("-fx-background-color: #ECA5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 1, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (passWord.getText().trim().isEmpty() || userName.getText().trim().isEmpty()) {
                    handleNullField(alertText);
                }else{
                    LoginController.getInstance().login(userName.getText().trim(), passWord.getText().trim());
                }
            }
        });
        createNewAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new RegisterMenu(LoginMenu.super.stage).execute();
            }
        });

    }

    protected void setPageGridPain() {
        pageGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.NEVER, VPos.CENTER, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
    }


    private void handleNullField(Text alertText){
        if (passWord.getText().isEmpty() && userName.getText().isEmpty()) {
            userName.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            passWord.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The UserName and PassWord");
            alertText.setVisible(true);
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        }else if(passWord.getText().isEmpty()){
            passWord.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The PassWord");
            alertText.setVisible(true);
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        }else if (userName.getText().isEmpty()){
            userName.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The UserName");
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        }else{
            // Nothing
        }
    }



    public void execute() {
        stage.setScene(scene);
        stage.show();
    }
}

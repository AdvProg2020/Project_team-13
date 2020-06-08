package View;

import Models.UserAccount.Customer;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static javax.swing.text.StyleConstants.Bold;

public class LoginMenu{
    private TextField userName;
    private TextField passWord;
    private Button loginButton;
    private Hyperlink createNewAccount;
    Scene scene;
    Stage stage;
    GridPane upGridPane, menuBarGridPane, centerGridPane, bottomGridPane, pageGridPane, userInfoGridPane;

    public LoginMenu(Stage stage) {
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        userInfoGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
//           centerGridPane.getRowConstraints().add(new RowConstraints(600, Control.USE_COMPUTED_SIZE, 600, Priority.NEVER, VPos.CENTER, false));
//               bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        setCenterGridPane();
//        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pageGridPane);
        scrollPane.fitToWidthProperty().set(true);
        scene.setRoot(scrollPane);
    }


    private void setCenterGridPane() {
        Label userNameLabel = new Label("Username :");
        Label passWordLabel = new Label("Password :");
        userNameLabel.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        passWordLabel.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        Label login = new Label("Login");
        login.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 23));
        userName = new TextField();
        passWord = new TextField();
        userName.setPromptText("Enter Username...");
        passWord.setPromptText("Enter Password...");
        userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
        passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
        Text alertUserName = new Text();
        Text alertPassword = new Text();
        loginButton = new Button("Login");
        loginButton.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        loginButton.setStyle("-fx-background-color: #E85D9E");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setMaxSize(400, 50);
        Text textOr = new Text("OR");
        textOr.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
        login.setAlignment(Pos.TOP_CENTER);
        userInfoGridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(login, HPos.CENTER);
        GridPane.setValignment(login, VPos.CENTER);
        GridPane.setHalignment(textOr, HPos.CENTER);
        GridPane.setValignment(textOr, VPos.CENTER);
        createNewAccount = new Hyperlink();
        createNewAccount.setText("Create New Account");
        createNewAccount.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        createNewAccount.setTextFill(Color.BLACK);
        createNewAccount.setUnderline(true);
        userInfoGridPane.setVgap(20);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(500);
        userInfoGridPane.add(login, 3, 1);
        userInfoGridPane.add(userNameLabel, 0, 4);
        userInfoGridPane.add(passWordLabel, 0, 5);
        userInfoGridPane.add(userName, 2, 4, 7, 1);
        userInfoGridPane.add(passWord, 2, 5, 7, 1);
        userInfoGridPane.add(loginButton, 3, 7);
        userInfoGridPane.add(textOr, 0, 9, 8, 1);
        userInfoGridPane.add(createNewAccount, 3, 10);
        GridPane leftGridPane= new GridPane();
        GridPane upGridPane= new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(130);
        userInfoGridPane.setStyle("-fx-background-color: #ECA5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 1, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);

    }


    private void setMenuBarGridPane() {
        menuBarGridPane.setStyle("-fx-background-color:rgba(76, 170, 240, 1)");
        GridPane leftGridPane = new GridPane();
        Label home = new Label("Home");
        Label products = new Label("Products");
        products.setStyle("-fx-background-color: rgba(45, 156, 240, 0.24);-fx-text-fill: White");
        home.setStyle("-fx-background-color:rgba(45, 156, 240, 0.31);-fx-text-fill: White;-fx-font-weight: bold;");
        ImageView image = new ImageView(new Image("file:src/back.png"));
        image.setFitWidth(40);
        image.setFitHeight(25);
        leftGridPane.add(image, 0, 0);
        leftGridPane.add(home, 1, 0);
        leftGridPane.add(products, 2, 0);
        leftGridPane.setHgap(5);
        GridPane rightGridPane = new GridPane();
        Label login = new Label("Login");
        Label register = new Label("Register");
        register.setStyle("-fx-background-color: rgba(45, 156, 240, 0.31);-fx-text-fill: White;-fx-font-weight: bold;");
        login.setStyle("-fx-background-color: rgba(45, 156, 240, 0.31);-fx-text-fill: White;-fx-font-weight: bold;");
        ImageView image1 = new ImageView(new Image("file:src/cart.png"));
        image1.setFitWidth(30);
        image1.setFitHeight(30);
        rightGridPane.add(image1, 2, 0);
        rightGridPane.add(login, 0, 0);
        rightGridPane.add(register, 1, 0);
        rightGridPane.setHgap(5);
        login.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        register.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        products.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        home.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.LEFT, true));
        menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, false));
        menuBarGridPane.add(leftGridPane, 0, 0);
        menuBarGridPane.add(rightGridPane, 1, 0);
        menuBarGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.NEVER, VPos.CENTER, false));
    }


    private void setUpGridPane() {
        Label label = new Label("        Pms.com");
        label.setStyle("-fx-font-weight: bold;");
        label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        upGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, VPos.CENTER, true));
        upGridPane.add(label, 0, 0);
    }


    private void setPageGridPain() {
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


    public void execute() {
        setScene();
        stage.setScene(scene);
        stage.show();
    }
}

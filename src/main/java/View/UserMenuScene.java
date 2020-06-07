package View;

import Controller.Client.ClientController;
import Models.UserAccount.Customer;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;


public class UserMenuScene {
    Scene scene;
    Stage stage;
    GridPane upGridPane, menuBarGridPane, centerGridPane, bottomGridPane, pageGridPane, userInfoGridPane;

    public UserMenuScene(Stage stage) {
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
        //   centerGridPane.getRowConstraints().add(new RowConstraints(600, Control.USE_COMPUTED_SIZE, 600, Priority.NEVER, VPos.CENTER, false));
        //       bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        setCenterGridPane();
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pageGridPane);
        scrollPane.fitToWidthProperty().set(true);
        scene.setRoot(scrollPane);

    }

    private void setCenterGridPane() {
        //  Customer customer=(Customer) ClientController.getInstance().getCurrentUser();
        Customer customer = new Customer("mamooti", "majidmajid", "Mahmood", "Ahmadi nejad", "Mamooti@yahoo.com", "09123456789", 10000);
        Text personalInfo = new Text(customer.viewPersonalInfo());
        Text pageTitle = new Text("User Menu");
        personalInfo.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        pageTitle.setStyle("-fx-font-weight: bold;");
        pageTitle.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        ImageView userIcon = new ImageView(new Image("file:src/user_icon.png"));
        userIcon.setFitHeight(100);
        userIcon.setFitWidth(100);
        ImageView editInfoPic = new ImageView(new Image("file:src/edit.png"));
        ImageView editPic = new ImageView(new Image("file:src/edit_user_photo.png"));
        editInfoPic.setFitWidth(25);
        editInfoPic.setFitHeight(25);
        editPic.setFitWidth(25);
        editPic.setFitHeight(25);
        Button editInfoButton = new Button("");
        editInfoButton.setGraphic(editInfoPic);
        Button editPhotoButton = new Button("");
        FileChooser fileChooser = new FileChooser();
        editPhotoButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            userIcon.setImage(new Image("file:"+selectedFile.getAbsolutePath()));
        });
        editPhotoButton.setGraphic(editPic);
        userInfoGridPane.setVgap(20);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(600);
        userInfoGridPane.setMaxHeight(300);
        userInfoGridPane.add(personalInfo, 2, 1, 1, 2);
        userInfoGridPane.add(userIcon, 1, 1, 1, 1);
        userInfoGridPane.add(editInfoButton, 9, 2, 1, 1);
        userInfoGridPane.add(editPhotoButton, 10, 2, 1, 1);
        GridPane leftMenuGridPane = new GridPane();
        leftMenuGridPane.setMinHeight(400);
        leftMenuGridPane.setStyle("-fx-background-color:rgba(45, 156, 240, 1);");
        Button discountCodesButton = new Button("Discount Codes");
        discountCodesButton.setStyle("-fx-font-size:  18;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        discountCodesButton.setMinHeight(50);
        discountCodesButton.setMinWidth(150);
        Button ordersButton = new Button("Orders");
        ordersButton.setTextAlignment(TextAlignment.CENTER);
        ordersButton.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        ordersButton.setMinHeight(50);
        ordersButton.setMinWidth(150);
        leftMenuGridPane.add(discountCodesButton, 0, 0, 2, 2);
        leftMenuGridPane.add(ordersButton, 0, 2, 2, 2);
        centerGridPane.add(leftMenuGridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 1, 1);
        centerGridPane.add(userInfoGridPane, 3, 1, 2, 2);
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

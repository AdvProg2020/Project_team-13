package View;

import Controller.Client.ClientController;
import Models.UserAccount.Customer;
import com.sun.javafx.scene.layout.region.LayeredBackgroundPositionConverter;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Popup;

import java.util.regex.Pattern;

public class Menu {
    protected Scene scene;
    protected Stage stage;
    protected GridPane upGridPane, menuBarGridPane, centerGridPane, bottomGridPane, pageGridPane;

    public Menu(Stage stage) {
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        ClientController.getInstance().addNewMenu(this);
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pageGridPane);
        scrollPane.fitToWidthProperty().set(true);
        scene.setRoot(scrollPane);
        Customer customer = new Customer("mamooti", "majidmajid", "Mahmood", "Ahmadi nejad", "Mamooti@yahoo.com", "09123456789", 10000);
        Text personalInfo = new Text(customer.viewPersonalInfo());
        System.out.println(personalInfo.getText());
        centerGridPane.add(personalInfo, 0, 0);
    }

    protected void setMenuBarGridPane() {
        if (ClientController.getInstance().getCurrentUser() == null) {
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
            login.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new LoginMenu(stage).execute();
                }
            });
            Label register = new Label("Register");
            register.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Stage popupwindow = new Stage();
                    GridPane gridPane = new GridPane();
                    scene.setFill(Color.GRAY);
                    popupwindow.setTitle("Edit information.");
                    gridPane.setStyle("-fx-background-color: Blue");
                    Button button = new Button("X");
                    button.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            scene.setFill(null);
                        }
                    });
                    gridPane.add(button, 0, 0);
                    gridPane.add(new Text(""),1,0);
                    ImageView seller = new ImageView(new Image("file:src/seller.png"));
                    ImageView customer = new ImageView(new Image("file:src/customer.png"));
                    seller.setFitWidth(100);
                    customer.setFitWidth(100);
                    seller.setFitHeight(110);
                    customer.setFitHeight(110);
                    gridPane.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
                    GridPane photoGridPane = new GridPane();
                    photoGridPane.setVgap(20);
                    photoGridPane.setHgap(20);
                    photoGridPane.add(seller, 0, 0);
                    photoGridPane.add(customer, 1, 0);
                    gridPane.add(photoGridPane, 1, 1);
                    photoGridPane.setAlignment(Pos.CENTER);
                    gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, VPos.TOP, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 400, Priority.NEVER, HPos.CENTER, true));
                    photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                    photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                    photoGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
                    photoGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    Label customer1=new Label("Customer");
                    Label seller1=new Label("Seller");
                    customer1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                    seller1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                    photoGridPane.add(seller1,0,1);
                    photoGridPane.add(customer1,1,1);
                    Scene scene1 = new Scene(gridPane, 320, 240);
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.initStyle(StageStyle.UNDECORATED);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            });
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
        } else {

        }
    }

    protected void setUpGridPane() {
        Label label = new Label("        Pms.com");
        label.setStyle("-fx-font-weight: bold;");
        label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        upGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, VPos.CENTER, true));
        upGridPane.add(label, 0, 0);
    }

    protected void setPageGridPain() {
        pageGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.NEVER, VPos.CENTER, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, VPos.TOP, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("This is a pop up window");
        Label label1 = new Label("Pop up window now displayed");
        Button button1 = new Button("Close this pop up window");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public void execute() {
        setScene();
        stage.setScene(scene);
        stage.show();
    }
}

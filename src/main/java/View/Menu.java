package View;

import Controller.Client.ClientController;
import Models.UserAccount.Customer;
import Models.UserAccount.Manager;
import Models.UserAccount.Seller;
import View2.UserMenu.Customer.CustomerMenu;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Popup;
import sun.font.FontFamily;
import sun.plugin2.message.Message;

import java.io.File;
import java.util.regex.Pattern;

public class Menu {
    protected Scene scene;
    protected Stage stage;
    protected GridPane upGridPane, menuBarGridPane, centerGridPane, bottomGridPane, pageGridPane;
    private AudioClip errorSound = new AudioClip(new File("src/error.mp3").toURI().toString());

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
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        scene.setRoot(pageGridPane);

    }

    protected void setMenuBarGridPane() {
        if (ClientController.getInstance().getCurrentUser() == null) {
            Menu menu = this;
            menuBarGridPane.setStyle("-fx-background-color:rgba(76, 170, 240, 1)");
            GridPane leftGridPane = new GridPane();
            Label home = new Label("Home");
            home.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!(menu instanceof MainMenu)) {
                        if (ClientController.getInstance().getMainMenu() != null) {
                            ClientController.getInstance().addNewMenu(ClientController.getInstance().getMainMenu());
                            ClientController.getInstance().getMainMenu().execute();
                        }
                    }
                }
            });
            Label products = new Label("Products");
            products.setStyle("-fx-background-color: rgba(45, 156, 240, 0.24);-fx-text-fill: White");
            home.setStyle("-fx-background-color:rgba(45, 156, 240, 0.31);-fx-text-fill: White;-fx-font-weight: bold;");
            ImageView back = new ImageView(new Image("file:src/back.png"));
            back.setFitWidth(40);
            back.setFitHeight(25);
            back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ClientController.getInstance().back();
                }
            });
            leftGridPane.add(back, 0, 0);
            leftGridPane.add(home, 1, 0);
            leftGridPane.add(products, 2, 0);
            leftGridPane.setHgap(5);
            GridPane rightGridPane = new GridPane();
            Label login = new Label("Login");
            login.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!(menu instanceof LoginMenu))
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
                    gridPane.add(new Text(""), 1, 0);
                    ImageView seller = new ImageView(new Image("file:src/seller.png"));
                    ImageView customer = new ImageView(new Image("file:src/customer.png"));
                    customer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            new RegisterMenu(stage).execute();
                        }
                    });
                    seller.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            new SellerRegisterMenu(stage).execute();
                        }
                    });
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
                    gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.CENTER, true));
                    photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                    photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                    photoGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
                    photoGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    Label customer1 = new Label("Customer");
                    customer1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            new RegisterMenu(stage).execute();
                        }
                    });

                    Label seller1 = new Label("Seller");
                    customer1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                    seller1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                    seller1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            new SellerRegisterMenu(stage).execute();
                        }
                    });
                    photoGridPane.add(seller1, 0, 1);
                    photoGridPane.add(customer1, 1, 1);
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
        Font font = Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28);
        label.setFont(font);
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
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }

    public void showMessage(String message, MessageKind messageKind) {
        Stage popupwindow = new Stage();
        GridPane gridPane = new GridPane();
        scene.setFill(Color.GRAY);
        popupwindow.setTitle("Edit information.");
        gridPane.add(new Text(""), 0, 0);
        ImageView right = new ImageView(new Image("file:src/Right.png"));
        ImageView wrong = new ImageView(new Image("file:src/Wrong.png"));
        right.setFitWidth(75);
        wrong.setFitWidth(75);
        right.setFitHeight(75);
        wrong.setFitHeight(75);
        ImageView content;
        GridPane photoGridPane = new GridPane();
        photoGridPane.setVgap(20);
        photoGridPane.setHgap(20);
        gridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.NEVER, VPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(230, Control.USE_COMPUTED_SIZE, 230, Priority.NEVER, HPos.CENTER, true));
        photoGridPane.getColumnConstraints().add(new ColumnConstraints(230, Control.USE_COMPUTED_SIZE, 230, Priority.ALWAYS, HPos.CENTER, true));
        photoGridPane.getRowConstraints().add(new RowConstraints(90, Control.USE_COMPUTED_SIZE, 90, Priority.NEVER, VPos.BOTTOM, true));
        photoGridPane.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, 50, Priority.NEVER, VPos.TOP, true));
        photoGridPane.getRowConstraints().add(new RowConstraints(70, Control.USE_COMPUTED_SIZE, 70, Priority.NEVER, VPos.CENTER, true));
        photoGridPane.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, 50, Priority.NEVER, VPos.TOP, true));
        if (messageKind.equals(MessageKind.ErrorWithBack)) {
            errorSound.play();
            photoGridPane.add(wrong, 0, 0);
            Text button = new Text("Go Back");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popupwindow.hide();
                    scene.setFill(null);
                    ClientController.getInstance().back();
                    if (message.startsWith("Register Successful")) {
                        new UserMenuScene(stage).execute();
                    } else if(message.startsWith("Login successful")) {
                        if(ClientController.getInstance().getCurrentUser() instanceof Customer) {
                            new UserMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Seller) {
                            new SellerMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Manager) {
                            new ManagerMenuScene(stage).execute();
                        }
                    }
                }
            });
            button.setFill(Color.WHITE);
            button.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            button.setTextAlignment(TextAlignment.CENTER);
            gridPane.add(button, 0, 0);
            gridPane.setStyle("-fx-background-color: Red");
            Label error = new Label("Error");
            Label message1 = new Label(message);
            message1.setFont(Font.loadFont("file:src/Bangers.ttf", 12));
            message1.setTextFill(Color.BLACK);
            error.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            error.setTextFill(Color.RED);
            photoGridPane.add(message1, 0, 2);
            photoGridPane.add(error, 0, 1);
            photoGridPane.add(gridPane, 0, 3);
        } else if (messageKind.equals(MessageKind.ErrorWithoutBack)) {
            errorSound.play();
            photoGridPane.add(wrong, 0, 0);
            Text button = new Text("Done");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popupwindow.hide();
                    scene.setFill(null);
                    if (message.startsWith("Register Successful")) {
                        new UserMenuScene(stage).execute();
                    } else if(message.startsWith("Login successful")) {
                        if(ClientController.getInstance().getCurrentUser() instanceof Customer) {
                            new UserMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Seller) {
                            new SellerMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Manager) {
                            new ManagerMenuScene(stage).execute();
                        }
                    }
                }
            });
            button.setFill(Color.WHITE);
            button.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            button.setTextAlignment(TextAlignment.CENTER);
            gridPane.add(button, 0, 0);
            gridPane.setStyle("-fx-background-color: Red");
            Label error = new Label("Error");
            Label message1 = new Label(message);
            message1.setFont(Font.loadFont("file:src/Bangers.ttf", 12));
            message1.setTextFill(Color.BLACK);
            error.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            error.setTextFill(Color.RED);
            photoGridPane.add(message1, 0, 2);
            photoGridPane.add(error, 0, 1);
            photoGridPane.add(gridPane, 0, 3);
        } else if (messageKind.equals(MessageKind.MessageWithoutBack)) {
            photoGridPane.add(right, 0, 0);
            Text button = new Text("Done");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popupwindow.hide();
                    scene.setFill(null);
                    if (message.startsWith("Register Successful")) {
                        new UserMenuScene(stage).execute();
                    }  else if(message.startsWith("Login successful")) {
                        if(ClientController.getInstance().getCurrentUser() instanceof Customer) {
                            new UserMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Seller) {
                            new SellerMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Manager) {
                            new ManagerMenuScene(stage).execute();
                        }
                    }
                }
            });
            button.setFill(Color.WHITE);
            button.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            button.setTextAlignment(TextAlignment.CENTER);
            gridPane.add(button, 0, 0);
            gridPane.setStyle("-fx-background-color: #02bf4f");
            Label error = new Label("Message");
            Label message1 = new Label(message);
            message1.setFont(Font.loadFont("file:src/Bangers.ttf", 12));
            message1.setTextFill(Color.BLACK);
            error.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            error.setTextFill(Color.web("#02bf4f"));
            photoGridPane.add(message1, 0, 2);
            photoGridPane.add(error, 0, 1);
            photoGridPane.add(gridPane, 0, 3);
        } else if (messageKind.equals(MessageKind.MessageWithBack)) {
            photoGridPane.add(right, 0, 0);
            Text button = new Text("Go Back");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popupwindow.hide();
                    ClientController.getInstance().back();
                    scene.setFill(null);
                    if (message.startsWith("Register Successful")) {
                        new UserMenuScene(stage).execute();
                    }  else if(message.startsWith("Login successful")) {
                        if(ClientController.getInstance().getCurrentUser() instanceof Customer) {
                            new UserMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Seller) {
                            new SellerMenuScene(stage).execute();
                        } else if(ClientController.getInstance().getCurrentUser() instanceof Manager) {
                            new ManagerMenuScene(stage).execute();
                        }
                    }
                }
            });
            button.setFill(Color.WHITE);
            button.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            button.setTextAlignment(TextAlignment.CENTER);
            gridPane.add(button, 0, 0);
            gridPane.setStyle("-fx-background-color: #02bf4f");
            Label error = new Label("Message");
            Label message1 = new Label(message);
            message1.setFont(Font.loadFont("file:src/Bangers.ttf", 12));
            message1.setTextFill(Color.BLACK);
            error.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
            error.setTextFill(Color.web("#02bf4f"));
            photoGridPane.add(message1, 0, 2);
            photoGridPane.add(error, 0, 1);
            photoGridPane.add(gridPane, 0, 3);
        }
        Scene scene1 = new Scene(photoGridPane, 230, 310);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.initStyle(StageStyle.UNDECORATED);
        popupwindow.setScene(scene1);
        System.out.println(popupwindow == null);
        System.out.println(stage == null);
        popupwindow.show();
    }
}

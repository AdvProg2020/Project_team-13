package View;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.LoginController;
import Controller.Client.ProductController;
import Models.Product.Category;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class LoginMenu extends Menu {
    private TextField userName;
    private PasswordField passWord;
    private Button loginButton;
    private Hyperlink createNewAccount;
    GridPane userInfoGridPane;

    public LoginMenu(Stage stage) {
        super(stage);
        setScene();
    }

    public void setScene() {
        userInfoGridPane = new GridPane();
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
        userInfoGridPane.getColumnConstraints().add(new ColumnConstraints(300, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        GridPane.setHalignment(createNewAccount, HPos.CENTER);
        GridPane.setHalignment(alertText, HPos.CENTER);
        userInfoGridPane.add(login, 0, 0, 2, 1);
        userInfoGridPane.setMinWidth(500);
        userInfoGridPane.setMinHeight(450);
        userInfoGridPane.add(userNameLabel, 0, 1);
        userInfoGridPane.add(passWordLabel, 0, 2);
        userInfoGridPane.add(userName, 1, 1);
        userInfoGridPane.add(passWord, 1, 2);
        userInfoGridPane.add(loginButton, 0, 5, 2, 1);
        userInfoGridPane.add(textOr, 0, 6, 2, 1);
        userInfoGridPane.add(createNewAccount, 0, 7, 2, 1);
        userInfoGridPane.add(alertText, 0, 8, 2, 1);
        GridPane.setValignment(textOr, VPos.CENTER);
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(40);
        leftGridPane.setMinWidth(165);
        leftGridPane.setGridLinesVisible(true);
        userInfoGridPane.setStyle("-fx-background-color: #ECA5DC;");
        centerGridPane.add(userInfoGridPane, 1, 1);
        centerGridPane.add(leftGridPane, 0, 0);
        centerGridPane.add(upGridPane, 1, 0);
        userInfoGridPane.setAlignment(Pos.CENTER);
        loginButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand

            }
        });
        loginButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (passWord.getText().trim().isEmpty() || userName.getText().trim().isEmpty()) {
                    handleNullField(alertText);
                } else {
                    LoginController.getInstance().login(userName.getText().trim(), passWord.getText().trim());
                }
            }
        });
        createNewAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                seller.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.HAND); //Change cursor to hand

                    }
                });
                seller.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                    }
                });
                customer.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.HAND); //Change cursor to hand

                    }
                });
                customer.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                    }
                });
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


    }

    protected void setPageGridPain() {
        pageGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.NEVER, VPos.CENTER, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
    }

    private void handleNullField(Text alertText) {
        if (passWord.getText().isEmpty() && userName.getText().isEmpty()) {
            userName.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            passWord.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The UserName and PassWord");
            alertText.setVisible(true);
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        } else if (passWord.getText().isEmpty()) {
            passWord.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The PassWord");
            alertText.setVisible(true);
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        } else if (userName.getText().isEmpty()) {
            userName.setStyle("-fx-background-color: rgb(250, 0, 0);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 55px;");
            alertText.setText("You Should Enter The UserName");
            alertText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 16));
        } else {
            // Nothing
        }
    }

    public void setMenuBarGridPane() {
        menuBarGridPane.getChildren().clear();
        menuBarGridPane.getColumnConstraints().clear();
        menuBarGridPane.getRowConstraints().clear();
        if (ClientController.getInstance().getCurrentUser() == null) {
            Menu menu = this;
            menuBarGridPane.setStyle("-fx-background-color:rgb(76,170,240)");
            GridPane leftGridPane = new GridPane();
            Label home = new Label("Home");
            home.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            home.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            home.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!(menu instanceof MainMenu)) {
                        ClientController.getInstance().getMainMenu().execute();

                    }
                }
            });
            ArrayList<MenuItem> menuItemArrayList = new ArrayList<>();
            for (String s : getCategoryName()) {
                MenuItem menuItem = new MenuItem("       " + s);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ProductController.getInstance().getAllProductsFromServer();
                        ProductController.getInstance().setCurrentCategory(CategoryController.getInstance().getCategoryWithName(s));
                        CategoryController.getInstance().setCurrentCategory(CategoryController.getInstance().getCategoryWithName(s));
                        new ProductsPageScene(stage).execute();
                    }
                });
                menuItemArrayList.add(menuItem);
            }
            MenuButton products = new MenuButton("Products");
            for (MenuItem menuItem : menuItemArrayList) {
                products.getItems().add(menuItem);
            }
            products.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            products.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            products.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
//                    ClientController.getInstance().back();
                }
            });
            products.setStyle("-fx-background-color: rgba(45, 156, 240, 0.24);");
            home.setStyle("-fx-background-color:rgba(45, 156, 240, 0.31);-fx-text-fill: White;-fx-font-weight: bold;");
            ImageView back = new ImageView(new Image("file:src/back.png"));
            back.setFitWidth(40);
            back.setFitHeight(25);
            back.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            back.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });

            back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ClientController.getInstance().back();
                }
            });
            leftGridPane.add(back, 0, 0);
            leftGridPane.add(home, 1, 0);
            //  products.setTextFill(Color.WHITE);
            leftGridPane.add(products, 2, 0);
            leftGridPane.setHgap(5);
            products.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
            home.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
            menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.LEFT, true));
            menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, false));
            menuBarGridPane.add(leftGridPane, 0, 0);
            menuBarGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.NEVER, VPos.CENTER, false));
        }
    }

    private ArrayList<String> getCategoryName() {
        CategoryController.getInstance().updateAllCategories();
        ArrayList<Category> categories = CategoryController.getInstance().getAllCategories();
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getName());
        }
        return categoriesNames;
    }


    public void execute() {
        stage.setScene(scene);
        stage.show();
    }
}

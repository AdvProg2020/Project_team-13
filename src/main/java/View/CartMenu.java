package View;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.DiscountCode;
import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class CartMenu extends Menu {
    GridPane productsPages;
    Text totalPriceAmount;
    DiscountCode discountCode1;
    Text errorText = new Text();

    public CartMenu(Stage stage) {
        super(stage);
        this.stage = stage;
        productsPages = new GridPane();
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        scene.setRoot(pageGridPane);
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
    }

    private void setCenterGridPane() {
        centerGridPane.getChildren().clear();
        String buttomStyle = "  -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    " +
                "    -fx-background-insets: 0,0,0,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 7 10 7 10;" +
                "     -fx-background-radius: 40px;" +
                "    -fx-border-radius: 20px;";
        //  Customer customer=(Customer) ClientController.getInstance().getCurrentUser();
        Text personalInfo = new Text("ali");
        Text pageTitle = new Text("Cart");
        personalInfo.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        pageTitle.setStyle("-fx-font-weight: bold;");
        pageTitle.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        Seller seller = new Seller("username", "password", "firstname",
                "lastName", "email", "phone number", 123,
                "ali", true);
        for (int kk = 0; kk < CartController.getInstance().getCurrentCart().getAllproduct().size(); kk++) {
            Product product = CartController.getInstance().getCurrentCart().getAllproduct().get(kk);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text("   " + product.getProductName() + "\n" + "   " + product.getCostAfterOff() + " $");
            Label label = new Label("   Total price:");
            Label totalPrice = new Label(Double.toString(CartController.getInstance().getTotalPriceOfProduct(product)));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            GridPane scoreGridPane = new GridPane();
            scoreGridPane.setHgap(2);
            scoreGridPane.add(label, 0, 0);
            scoreGridPane.add(totalPrice, 1, 0);
            Label countText = new Label("   Count: ");
            Label count = new Label(Double.toString(CartController.getInstance().getCurrentCart().getCountOfEachProduct().get(product.getProductId())));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            GridPane countPane = new GridPane();
            countPane.setHgap(2);
            countPane.add(countText, 0, 0);
            countPane.add(count, 1, 0);
            ImageView positive = new ImageView(new Image("file:src/positive.png"));
            ImageView negative = new ImageView(new Image("file:src/negative.png"));
            positive.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            positive.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            positive.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    CartController.getInstance().changeCountOfProduct(product, true);
                    setCenterGridPane();
                }
            });
            negative.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            negative.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            negative.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    CartController.getInstance().changeCountOfProduct(product, false);
                    setCenterGridPane();
                }
            });
            positive.setFitWidth(25);
            positive.setFitHeight(25);
            negative.setFitWidth(25);
            negative.setFitHeight(25);
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 2, 1);
            gridPane.add(positive, 0, 3);
            GridPane options = new GridPane();
            options.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 90, Priority.NEVER, HPos.LEFT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, false));
            options.setHgap(2);
            options.add(countPane, 0, 0);
            options.add(positive, 1, 0);
            options.add(negative, 2, 0);
            gridPane.add(options, 0, 3, 2, 1);
            gridPane.setMaxWidth(150);
            gridPanes.add(gridPane);
            gridPane.setStyle("-fx-background-color: #ECD5DC;-fx-background-radius: 20px;");
            countText.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            count.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            totalPrice.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            gridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            gridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
//                            ClientController.getInstance().setCurrentProduct(product);
//                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
        }
        ArrayList<GridPane> productsPages = new ArrayList<>();
        for (int j = 0; j < (gridPanes.size() / 4) + (gridPanes.size() % 4 == 0 ? 0 : 1); j++) {
            productsPages.add(new GridPane());
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).setVgap(10);
            productsPages.get(j).setMinWidth(600);
            productsPages.get(j).setMaxHeight(300);
            for (int i = j * 4; i < (j) * 4 + (j == ((gridPanes.size() / 4) + (gridPanes.size() % 4 == 0 ? 0 : 1) - 1) ? gridPanes.size() % 4 : 4); i++) {
                productsPages.get(j).add(gridPanes.get(i), 2 * ((i % 4) % 4) + 1, ((i % 4) / 4), 1, 1);
            }
        }
        ArrayList<Button> buttons = new ArrayList<>();
        Button purchase = new Button("Purchase");
        purchase.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        purchase.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        purchase.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (ClientController.getInstance().getCurrentUser() != null) {
                    Stage popupwindow = new Stage();
                    GridPane gridPane = new GridPane();
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
                    gridPane.setStyle("-fx-background-color: rgba(255,145,200,0.85);");
                    GridPane commentPane = new GridPane();
                    gridPane.add(commentPane, 1, 1);
                    Text titleText = new Text("Phone Number:");
                    Text contentText = new Text("Address:");
                    TextField getTitle = new TextField();
                    TextArea getContent = new TextArea();
                    getTitle.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    getContent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;");
                    getContent.setMinHeight(100);
                    getContent.setMaxWidth(200);
                    Text discountCodeText = new Text("Discount code");
                    TextField discountCode = new TextField();
                    Button checkDiscountCodeButton = new Button("Check it");
                    checkDiscountCodeButton.setStyle("-fx-background-color: #E85D9E;");
                    checkDiscountCodeButton.setMinWidth(100);
                    checkDiscountCodeButton.setTextFill(Color.WHITE);
                    Button addCommentButton = new Button("Purchase");
                    addCommentButton.setStyle("-fx-background-color: #E85D9E;");
                    addCommentButton.setMinWidth(100);
                    commentPane.setVgap(10);
                    commentPane.setHgap(10);
                    checkDiscountCodeButton.setVisible(false);
                    discountCode.setVisible(false);
                    discountCodeText.setVisible(false);
                    addCommentButton.setVisible(false);
                    getTitle.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (!getContent.getText().equals("")) {
                                checkDiscountCodeButton.setVisible(true);
                                discountCode.setVisible(true);
                                discountCodeText.setVisible(true);
                                addCommentButton.setVisible(true);
                            }
                        }
                    });
                    getContent.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (!getTitle.getText().equals("")) {
                                checkDiscountCodeButton.setVisible(true);
                                discountCode.setVisible(true);
                                discountCodeText.setVisible(true);
                                addCommentButton.setVisible(true);
                            }
                        }
                    });
                    errorText.setFill(Color.RED);
                    addCommentButton.setTextFill(Color.WHITE);
                    commentPane.add(titleText, 0, 0);
                    commentPane.add(contentText, 0, 1);
                    commentPane.add(getTitle, 1, 0);
                    commentPane.add(getContent, 1, 1, 1, 5);
                    commentPane.add(discountCodeText, 0, 6);
                    commentPane.add(discountCode, 1, 6);
                    commentPane.add(checkDiscountCodeButton, 0, 7);
                    commentPane.add(errorText, 1, 8);
                    commentPane.add(addCommentButton, 1, 9);
                    addCommentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (discountCode1 != null && discountCode1.getStartTime().before(new Date()) && discountCode1.getEndTime().after(new Date()))
                                CartController.getInstance().getCurrentCart().setDiscountCode(discountCode1);
                            if (!getContent.getText().equals("") && !getTitle.getText().equals("")) {
                                if (getTitle.getText().matches("\\d\\d\\d\\d\\d\\d\\d\\d+")) {
                                    CartController.getInstance().getCurrentCart().setReceivingInformation(getTitle.getText() + "\n" + getContent.getText());
                                    CartController.getInstance().getCurrentCart().setCustomerID(ClientController.getInstance().getCurrentUser().getUsername());


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
                                    ImageView seller = new ImageView(new Image("file:src/bank.png"));
                                    ImageView customer = new ImageView(new Image("file:src/wallet.png"));
                                    customer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            popupwindow.hide();
                                            CartController.getInstance().pay();
                                        }
                                    });
                                    seller.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            popupwindow.hide();
                                            //pay with bank account
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
                                    Label customer1 = new Label("Pay with Wallet");
                                    customer1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            popupwindow.hide();
                                            new RegisterMenu(stage).execute();
                                        }
                                    });
                                    Label seller1 = new Label("Pay with Bank");
                                    customer1.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
                                    seller1.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
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
                                    scene.setFill(null);
                                } else {
                                    getTitle.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                    errorText.setText("Phone Number is invalid");
                                }
                            } else if (getContent.getText().equals("")) {
                                getContent.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                errorText.setText("Address is empty");
                            } else if (getTitle.getText().equals("")) {
                                getTitle.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                errorText.setText("Phone Number is empty");
                            }
                        }
                    });
                    checkDiscountCodeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (!discountCode.getText().equals("")) {
                                discountCode1 = getDiscountCode(discountCode.getText());
                                if (discountCode1 != null) {
                                    if (discountCode1.getStartTime().before(new Date()) && discountCode1.getEndTime().after(new Date())) {
                                        errorText.setText("");
                                        discountCode.setStyle("-fx-background-color: #08ff00;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                    } else if (!discountCode1.getStartTime().before(new Date())) {
                                        discountCode.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                        errorText.setText("start time of discount code have not started.");
                                    } else if (!discountCode1.getEndTime().after(new Date())) {
                                        discountCode.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                        errorText.setText("discountCode has been expired.");
                                    }
                                } else if (discountCode1 == null) {
                                    discountCode.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                    errorText.setText("you haven't any discount code with this code");
                                }
                            } else {
                                discountCode.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-background-radius: 30;");
                                errorText.setText("Discount code is empty");

                            }
                        }
                    });
                    Scene scene1 = new Scene(gridPane, 400, 400);
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.initStyle(StageStyle.UNDECORATED);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                } else {
                    new LoginMenu(stage).execute();
                }
            }
        });
        Label totalPrice = new Label("Total Price: ");
//        totalPriceAmount = new Text(Double.toString(CartController.getInstance().getCurrentCart().getTotalPrice()));
        totalPriceAmount = new Text(Double.toString(CartController.getInstance().getCurrentCart().getTotalPrice()));
        for (int i = 0; i < productsPages.size(); i++) {
            buttons.add(new Button(Integer.toString(i + 1)));
            buttons.get(i).setStyle(buttomStyle);
            buttons.get(i).setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            buttons.get(i).setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            final int[] j = {i};
            buttons.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node child : centerGridPane.getChildren()) {
                        if (productsPages.contains(child)) {
                            centerGridPane.getChildren().remove(child);
                            break;
                        }
                    }
                    GridPane buttons1 = new GridPane();
                    buttons1.setHgap(3);
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons1.add(buttons.get(i), i + 1, 0);
                    }
                    GridPane gridPane11 = new GridPane();
                    gridPane11.add(totalPrice, 0, 0);
                    gridPane11.add(totalPriceAmount, 1, 0);
                    gridPane11.add(purchase, 2, 0);
                    gridPane11.setStyle("-fx-background-color: #E6E6E6");
                    gridPane11.setMinWidth(660);
                    gridPane11.setMinHeight(75);
                    buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
                    productsPages.get(j[0]).add(buttons1, 1, 5, 7, 1);
                    productsPages.get(j[0]).add(gridPane11, 1, 6, 7, 2);
                    centerGridPane.add(productsPages.get(j[0]), 1, 1, 2, 2);
                }
            });
        }
        GridPane buttons1 = new GridPane();
        buttons1.setHgap(3);
        for (int i = 0; i < buttons.size(); i++) {
            buttons1.add(buttons.get(i), i + 1, 0);
        }
        buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
        if (productsPages.size() > 0) {
            GridPane gridPane11 = new GridPane();
            gridPane11.add(totalPrice, 0, 0);
            gridPane11.add(totalPriceAmount, 1, 0);
            gridPane11.add(purchase, 2, 0);
            gridPane11.getColumnConstraints().add(new ColumnConstraints(75, Control.USE_COMPUTED_SIZE, 75, Priority.NEVER, HPos.LEFT, false));
            gridPane11.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.LEFT, false));
            gridPane11.getColumnConstraints().add(new ColumnConstraints(275, Control.USE_COMPUTED_SIZE, 275, Priority.NEVER, HPos.RIGHT, false));
            gridPane11.getRowConstraints().add(new RowConstraints(75, Control.USE_COMPUTED_SIZE, 75, Priority.NEVER, VPos.CENTER, false));
            totalPrice.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            totalPriceAmount.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            purchase.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
            String string = "  -fx-background-color: \n" +
                    "        #090a0c,\n" +
                    "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                    "        linear-gradient(#20262b, #191d22),\n" +
                    "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                    "    -fx-background-radius: 5,4,3,5;\n" +
                    "    -fx-background-insets: 0,1,2,0;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                    "    -fx-font-family: \"Arial\";\n" +
                    "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                    "    -fx-font-size: 12px;\n" +
                    "    -fx-padding: 10 20 10 20;";
            purchase.setStyle(string);
            gridPane11.setStyle("-fx-background-color: #E6E6E6");
            gridPane11.setMinWidth(660);
            gridPane11.setMinHeight(75);
            gridPane11.getColumnConstraints().add(new ColumnConstraints(75, Control.USE_COMPUTED_SIZE, 75, Priority.NEVER, HPos.LEFT, false));
            gridPane11.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.LEFT, false));
            gridPane11.getColumnConstraints().add(new ColumnConstraints(275, Control.USE_COMPUTED_SIZE, 275, Priority.NEVER, HPos.RIGHT, false));
            gridPane11.getRowConstraints().add(new RowConstraints(75, Control.USE_COMPUTED_SIZE, 75, Priority.NEVER, VPos.CENTER, false));
            productsPages.get(0).add(buttons1, 1, 5, 7, 1);
            productsPages.get(0).add(gridPane11, 1, 6, 7, 1);
        }
        GridPane leftMenuGridPane = new GridPane();
        leftMenuGridPane.setMinHeight(400);
        leftMenuGridPane.setStyle("-fx-background-color:rgba(45, 156, 240, 1);");
        Button addProduct = new Button("");
        addProduct.setTextAlignment(TextAlignment.CENTER);
        addProduct.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        addProduct.setMinHeight(50);
        addProduct.setMinWidth(150);
        leftMenuGridPane.add(addProduct, 0, 2, 2, 2);
        centerGridPane.add(leftMenuGridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 1, 1);
        if (productsPages.size() > 0) {
            centerGridPane.add(productsPages.get(0), 1, 1, 2, 2);
        } else {
            ImageView imageView = new ImageView(new Image("file:src/empty.png"));
            Text text = new Text("You'r cart is empty.                  ");
            GridPane gridPane = new GridPane();
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            gridPane.add(imageView, 0, 0);
            gridPane.add(text, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(400, Control.USE_COMPUTED_SIZE, 400, Priority.NEVER, HPos.RIGHT, false));
            gridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 200, Priority.NEVER, HPos.RIGHT, false));
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 32));
            centerGridPane.add(gridPane, 3, 1, 1, 1);
        }
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

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkPasswordIsvalid(String word) {
        return word.length() > 8 && word.length() < 18;
    }

    private boolean checkNameIsvalid(String name) {
        return Pattern.matches("(([a-z]|[A-Z])+ )*(([a-z]|[A-Z])+)", name) && !name.isEmpty();
    }

    private DiscountCode getDiscountCode(String discountCode) {
        DiscountCode discount = ((Customer) ClientController.getInstance().getCurrentUser()).findDiscountCodeWithCode(discountCode);
        return discount;
    }

    private boolean checkEmailIsvalid(String email) {
        return Pattern.matches("\\w+\\.?\\w*@\\w+\\.\\w+", email);
    }
}

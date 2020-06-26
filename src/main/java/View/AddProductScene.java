package View;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Seller;
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
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class AddProductScene extends Menu {
    private ArrayList<Category> categories;
    private String imagePath = "";
    private GridPane productsPages, userInfoGridPane;
    private TextField productName, availableNumbers, description, cost;
    private Button loginButton;
    private Hyperlink createNewAccount;

    public AddProductScene(Stage stage) {
        super(stage);
        this.stage = stage;
        productsPages = new GridPane();
        setScene();
    }

    public void setScene() {
        userInfoGridPane = new GridPane();
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        scene.setRoot(pageGridPane);
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
    }

    private void setCenterGridPane() {
        String menuBarStyle = "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 0 0 0 0;";
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        productName = new TextField();
        final TextField companyName1 = new TextField();
        availableNumbers = new TextField();
        description = new TextField();
        cost = new TextField();
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(50);
        Text title = new Text("\tAdd Product");
        ImageView userImage = new ImageView(new Image("file:src/product_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text productName1 = new Text("Product Name");
        Text companyName = new Text("Company Name");
        Text categoryText = new Text("Category");
        Text descriptionText = new Text("Description");
        Text costText = new Text("Products Cost");
        Text availableNumbersText = new Text("Available numbers");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button addProduct = new Button("Add Product");
        addProduct.setStyle("-fx-background-color: #E85D9E;");
        addProduct.setMinWidth(100);
        addProduct.setTextFill(Color.WHITE);
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        editPhotoButton.setStyle("-fx-background-color: #E85D9E");
        editPhotoButton.setMinWidth(100);
        editPhotoButton.setTextFill(Color.WHITE);
        FileChooser fileChooser = new FileChooser();
        Text selectedCategoryText = new Text("Selected Category");
        Text selectedCategory = new Text("");
        EventHandler<ActionEvent> eventChoosePhoto = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    userImage.setImage(new Image(selectedFile.toURI().toString()));
                    imagePath = selectedFile.toURI().toString();
                }
            }
        };
        HashMap<String, String> categoryFeaturesForProduct = new HashMap<>();
        ArrayList<MenuItem> menuItemArrayList = new ArrayList<>();
        for (String s : getCategoryName()) {
            MenuItem menuItem = new MenuItem("     1" + s);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectedCategory.setText(s);
                    categoryFeaturesForProduct.clear();

                    Category category1 = new Category(null, null);
                    for (Category category : CategoryController.getInstance().getAllCategories()) {
                        if (category.getName().equals(s)) {
                            category1 = category;
                        }
                    }


                    HashMap<Text, MenuButton> categoryFeatures = new HashMap<>();
                    ArrayList<MenuButton> menuButtons = new ArrayList<>();
                    for (String s1 : category1.getFeatures().keySet()) {
                        Text text = new Text(s1);
                        ImageView imageView = new ImageView(new Image("file:src/standings.png"));
                        imageView.setFitHeight(30);
                        imageView.setFitWidth(30);
                        MenuButton menuButton = new MenuButton(s1, imageView);
                        ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
                        for (String s2 : category1.getFeatures().get(s1)) {
                            MenuItem menuItem1 = new MenuItem(s2);
                            menuItemArrayList1.add(menuItem1);
                            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (categoryFeaturesForProduct.containsKey(s1)) {
                                        categoryFeaturesForProduct.replace(s1, s2);
                                    } else {
                                        categoryFeaturesForProduct.put(s1, s2);
                                    }
                                }
                            });
                        }
                        for (MenuItem item : menuItemArrayList1) {
                            menuButton.getItems().add(item);
                        }
                        categoryFeatures.put(text, menuButton);
                    }
                    Stage popupwindow = new Stage();
                    GridPane gridPane = new GridPane();
                    scene.setFill(Color.GRAY);
                    popupwindow.setTitle("Category Features");
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
                    gridPane.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
                    GridPane featuresGridPane = new GridPane();
                    gridPane.add(featuresGridPane, 1, 1, 1, 1);
                    featuresGridPane.setAlignment(Pos.CENTER);
                    int k1 = 0;
                    for (Text text : categoryFeatures.keySet()) {
                        categoryFeatures.get(text).setStyle(menuBarStyle);
                        featuresGridPane.add(text, 0, k1);
                        featuresGridPane.add(categoryFeatures.get(text), 1, k1);
                        Text text1 = new Text("");
                        featuresGridPane.add(text1, 2, k1);
                        for (MenuItem item : categoryFeatures.get(text).getItems()) {
                            item.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    text1.setText(item.getText());
                                    if (categoryFeaturesForProduct.containsKey(text.getText())) {
                                        categoryFeaturesForProduct.replace(text.getText(), item.getText());
                                    } else {
                                        categoryFeaturesForProduct.put(text.getText(), item.getText());
                                    }
                                }
                            });
                        }
                        k1++;
                    }
                    gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.CENTER, true));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, HPos.CENTER, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(120, Control.USE_COMPUTED_SIZE, 120, Priority.ALWAYS, HPos.LEFT, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 90, Priority.ALWAYS, HPos.LEFT, false));
                    featuresGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
                    featuresGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    featuresGridPane.setVgap(10);
                    Scene scene1 = new Scene(gridPane, 320, categoryFeatures.size() * 50 + 200);
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.initStyle(StageStyle.UNDECORATED);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            });
            menuItemArrayList.add(menuItem);
        }
        MediaView mediaView = new MediaView();
        Button chooseVideoButton = new Button("Choose video");
        chooseVideoButton.setStyle("-fx-background-color: #E85D9E;");
        chooseVideoButton.setMinWidth(100);
        chooseVideoButton.setTextFill(Color.WHITE);
        mediaView.setFitWidth(100);
        mediaView.setFitHeight(100);
        chooseVideoButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand

            }
        });
        chooseVideoButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        final String[] videoPath = new String[1];
        videoPath[0]="";
        chooseVideoButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    if(selectedFile.toURI().toString().endsWith("mp4")) {
                        mediaView.setMediaPlayer(new MediaPlayer(new Media(selectedFile.toURI().toString())));
                        videoPath[0] = selectedFile.toURI().toString();
                    }else {
                        errorText.setText("your file should be .mp4 format");
                    }
                }

            }
        });
        ImageView imageView = new ImageView(new Image("file:src/standings.png"));
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        MenuButton menuButton = new MenuButton("Categories", imageView);
        for (MenuItem menuItem : menuItemArrayList) {
            menuButton.getItems().add(menuItem);
        }
        menuButton.setStyle(menuBarStyle);
        editPhotoButton.setOnAction(eventChoosePhoto);
        // userInfoGridPane.add(title,0,0);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        //  userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        // upGridPane.setGridLinesVisible(true);
        upGridPane.add(title, 1, 0, 1, 1);
        this.productName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        companyName1.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        description.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        cost.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        availableNumbers.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");

        userInfoGridPane.add(userImage, 13, 1, 5, 5);
        userInfoGridPane.add(chooseVideoButton, 8, 5, 5, 5);
        userInfoGridPane.add(mediaView, 8, 1, 5, 5);
        userInfoGridPane.add(productName1, 5, 9, 3, 1);
        userInfoGridPane.add(companyName, 5, 10, 3, 1);
        userInfoGridPane.add(descriptionText, 5, 11, 3, 1);
        userInfoGridPane.add(costText, 5, 12, 3, 1);
        userInfoGridPane.add(availableNumbersText, 14, 9, 3, 1);
        userInfoGridPane.add(categoryText, 15, 11, 3, 1);
        userInfoGridPane.add(selectedCategoryText, 14, 12, 2, 1);
        userInfoGridPane.add(this.productName, 8, 9, 6, 1);
        userInfoGridPane.add(companyName1, 8, 10, 6, 1);
        userInfoGridPane.add(description, 8, 11, 6, 1);
        userInfoGridPane.add(menuButton, 18, 11, 3, 1);
        userInfoGridPane.add(selectedCategory, 19, 12);
        userInfoGridPane.add(cost, 8, 12, 6, 1);
        userInfoGridPane.add(availableNumbers, 19, 9, 1, 1);
        userInfoGridPane.add(editPhotoButton, 13, 6, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        userInfoGridPane.add(addProduct, 13, 15, 5, 3);
        userInfoGridPane.add(new Text(""), 18, 18);
        addProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AddProductScene.this.productName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                companyName1.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                description.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                cost.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                availableNumbers.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                errorText.setText("");
                if (checkNameIsvalid(companyName1.getText().trim())) {
                    if (Pattern.matches("\\d+", availableNumbers.getText().trim())) {
                        if (!(description.getText().trim()).isEmpty()) {
                            if (checkNameIsvalid(AddProductScene.this.productName.getText())) {
                                if (checkCreditIsvalid(cost.getText())) {
                                    if (categoryFeaturesForProduct.size() == CategoryController.getInstance().getCategoryWithName(selectedCategory.getText()).getFeatures().size()) {
                                        Product product = new Product(companyName1.getText().trim(), "", productName.getText().trim(),
                                                (Seller) ClientController.getInstance().getCurrentUser(), Double.parseDouble(cost.getText().trim()), selectedCategory.getText().trim(), description.getText().trim(), Integer.parseInt(availableNumbers.getText().trim()), categoryFeaturesForProduct);
                                        product.setVideoPath(videoPath[0]);
                                        product.setImagePath(imagePath);
                                        ProductController.getInstance().addProduct(product);
                                    } else {
                                        errorText.setText("You need to choose a mode for each feature of category.");
                                    }
                                } else {
                                    cost.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                    errorText.setText("csot is not a number.");
                                }
                            } else {
                                AddProductScene.this.productName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                errorText.setText("Product Name is invalid. use alphabetical characters and space.");
                            }
                        } else {
                            description.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                            errorText.setText("description is empty");
                        }
                    } else {
                        availableNumbers.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("available number of product is not a number.");
                    }
                } else {
                    companyName1.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Company Name Format is Invalid. use 0-9 alphabetical character.");
                }
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

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }


    private boolean checkNameIsvalid(String username) {
        if (Pattern.matches("(\\w+\\s)*\\w+", username)) {
            return true;
        }
        return false;
    }

    private boolean checkCreditIsvalid(String credit) {
        if (Pattern.matches("\\d+\\.?\\d*", credit)) {
            return true;
        }
        return false;
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
}

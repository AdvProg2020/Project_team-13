package View;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Models.Product.Category;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class EditCategoryMenu extends Menu {
    private TextField maxAmount, firstName, lastName, email, credit, phoneNumber;
    private TextField discountPercent;
    private DatePicker startDatePicker = new DatePicker();
    String imagePath = "";
    GridPane userInfoGridPane;
    HashMap<String, ArrayList<String>> featuresOfCategory = new HashMap();
    ArrayList<String> allModesOfFeature = new ArrayList<>();

    public EditCategoryMenu(Stage stage) {
        super(stage);
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
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        scene.setRoot(pageGridPane);
    }

    public void setMenuBarGridPane() {
        menuBarGridPane.getChildren().clear();
        menuBarGridPane.getColumnConstraints().clear();
        menuBarGridPane.getRowConstraints().clear();
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
        products.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        home.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.LEFT, true));
        menuBarGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, false));
        menuBarGridPane.add(leftGridPane, 0, 0);
        menuBarGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.NEVER, VPos.CENTER, false));
    }


    private void setCenterGridPane() {
        Category category = CategoryController.getInstance().getCurrentCategory();
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        maxAmount = new TextField();
        discountPercent = new TextField();
        firstName = new TextField();
        lastName = new TextField();
        phoneNumber = new TextField();
        email = new TextField();
        credit = new TextField();
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(25);
        Text title = new Text("\tEdit Category");
        ImageView userImage = new ImageView(new Image("file:src/user_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text maxAmountText = new Text("Catefory Neme");
        Text discountPercentText = new Text("Enter Feature Name");
        Text startDateText = new Text("Enter Feature Name");
        Text endDateText = new Text("End Date");
        Text firstNameText = new Text("Username");
        Text lastNameText = new Text("Numbers");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button adduser = new Button("Add Feature");
        adduser.setStyle("-fx-background-color: #E85D9E;");
        adduser.setMinWidth(100);
        adduser.setTextFill(Color.WHITE);
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        Button addmode = new Button("Add Mode");
        addmode.setStyle("-fx-background-color: #E85D9E;");
        addmode.setMinWidth(100);
        addmode.setTextFill(Color.WHITE);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        ComboBox featureNamesComboBox = new ComboBox();
        featureNamesComboBox.setEditable(true);
        for (String feature : category.getFeatures().keySet()) {
            featureNamesComboBox.getItems().add(feature);
        }
        comboBox.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (featureNamesComboBox.getValue() != null && !featureNamesComboBox.getValue().equals("")) {
                    comboBox.getItems().remove(0, comboBox.getItems().size());
                    allModesOfFeature.clear();
                    if (category.getFeatures().containsKey(featureNamesComboBox.getValue())) {
                        for (String mode : category.getFeatures().get(featureNamesComboBox.getValue())) {
                            comboBox.getItems().add(mode);
                            allModesOfFeature.add(mode);
                        }
                    }
                }
            }
        });
        upGridPane.add(hBox1, 0, 0, 1, 1);
        maxAmount.setText(category.getName());
        upGridPane.add(title, 1, 0, 1, 1);
        maxAmount.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        credit.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        firstName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        userInfoGridPane.add(discountPercentText, 5, 10, 3, 1);
        userInfoGridPane.add(featureNamesComboBox, 8, 10, 6, 1);
        userInfoGridPane.add(comboBox, 15, 10, 6, 1);
        userInfoGridPane.add(addmode, 25, 10, 6, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        addmode.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        addmode.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        addmode.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
                comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (!comboBox.getValue().equals("")) {
                    if (!allModesOfFeature.contains(comboBox.getValue())) {
                        if (category.getFeatures().containsKey(featureNamesComboBox.getValue())) {
                            comboBox.getItems().add(comboBox.getValue());
                            category.getFeatures().get(featureNamesComboBox.getValue()).add((String) comboBox.getValue());
                            CategoryController.getInstance().editCategoryFeatures(category, "adM");
                            comboBox.setValue("");
                        } else {
                            ArrayList<String> modes = new ArrayList<>();
                            modes.add((String) comboBox.getValue());
                            featureNamesComboBox.getItems().add(featureNamesComboBox.getValue());
                            category.getFeatures().put((String) featureNamesComboBox.getValue(), modes);
                            CategoryController.getInstance().editCategoryFeatures(category, "add");
                            comboBox.setValue("");
                        }
                    } else {
                        comboBox.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("you can't add same mode");
                    }
                } else {
                    comboBox.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("you should write a mode before you add it");

                }
            }
        });

        adduser.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);

            }
        });
        adduser.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        adduser.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
                comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (!(discountPercent.getText()).equals("")) {
                    if (allModesOfFeature.size() != 0) {
                        if (!featuresOfCategory.containsKey(discountPercent.getText())) {
                            featuresOfCategory.put(discountPercent.getText(), allModesOfFeature);
                            discountPercent.setText("");
                            for (String mode : allModesOfFeature) {
                                comboBox.getItems().remove(mode);
                            }
                            allModesOfFeature = new ArrayList<>();
                            CategoryController.getInstance().editCategoryFeatures(category, "add");
                        } else {
                            discountPercent.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                            errorText.setText("you can't add same feature");
                        }
                    } else {
                        comboBox.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("you should add one mode to the feature at least");
                    }
                } else {
                    discountPercent.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("you should write feature's name before you add it");

                }
            }
        });
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }


}

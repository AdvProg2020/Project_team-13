package View;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Controller.Client.ManagerController;
import Models.DiscountCode;
import Models.Product.Category;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CreateCategoryMenu extends Menu {
    private TextField maxAmount, firstName, lastName, email, credit, phoneNumber;
    private TextField discountPercent;
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private Button loginButton;
    private Hyperlink createNewAccount;
    private HashMap<String, Integer> maxUsingTime = new HashMap<>();
    private HashMap<String, Integer> remainingTimesForEachCustomer = new HashMap<>();
    private ArrayList<String> allUsers = new ArrayList<>();
    String imagePath = "";
    GridPane userInfoGridPane;
    HashMap<String, ArrayList<String>> featuresOfCategory = new HashMap();
    String categoryName;
    ArrayList<String> allModesOfFeature = new ArrayList<>();

    public CreateCategoryMenu(Stage stage) {
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
//           centerGridPane.getRowConstraints().add(new RowConstraints(600, Control.USE_COMPUTED_SIZE, 600, Priority.NEVER, VPos.CENTER, false));
//               bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        setCenterGridPane();
//        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        scene.setRoot(pageGridPane);
    }

    public void setMenuBarGridPane()  {
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
        Text title = new Text("\tCreate Category");
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
        Button signUp = new Button("Create Category");
        signUp.setStyle("-fx-background-color: #E85D9E;");
        signUp.setMinWidth(100);
        signUp.setTextFill(Color.WHITE);
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
        FileChooser fileChooser = new FileChooser();
        EventHandler<ActionEvent> eventChoosePhoto = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    userImage.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
                    imagePath = "file:" + selectedFile.getAbsolutePath();
                }
            }
        };
        editPhotoButton.setOnAction(eventChoosePhoto);
        // userInfoGridPane.add(title,0,0);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        //  userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        // upGridPane.setGridLinesVisible(true);
        upGridPane.add(title, 1, 0, 1, 1);
        maxAmount.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        credit.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        firstName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        userInfoGridPane.add(maxAmountText, 5, 9, 3, 1);
        userInfoGridPane.add(discountPercentText, 5, 10, 3, 1);
        userInfoGridPane.add(adduser, 5, 11, 3, 1);
        userInfoGridPane.add(maxAmount, 8, 9, 6, 1);
        userInfoGridPane.add(discountPercent, 8, 10, 6, 1);
        userInfoGridPane.add(comboBox, 15, 10, 6, 1);
        userInfoGridPane.add(addmode, 25, 10, 6, 1);
        userInfoGridPane.add(signUp, 13, 16, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        addmode.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand

            }
        });
        addmode.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        addmode.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
                comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (!((String) comboBox.getValue()).equals("")) {
                    if(!allModesOfFeature.contains((String) comboBox.getValue())) {
                        comboBox.getItems().add((String) comboBox.getValue());
                        allModesOfFeature.add((String) comboBox.getValue());
                        comboBox.setValue("");
                    }else{
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
                scene.setCursor(Cursor.HAND); //Change cursor to hand

            }
        });
        adduser.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
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
                        if(!featuresOfCategory.containsKey(discountPercent.getText())) {
                            featuresOfCategory.put(discountPercent.getText(), allModesOfFeature);
                            discountPercent.setText("");
                            for (String mode : allModesOfFeature) {
                                comboBox.getItems().remove(mode);
                            }
                            allModesOfFeature = new ArrayList<>();
                        }else{
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
        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maxAmount.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
                startDatePicker.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
                comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (!maxAmount.getText().equals("")) {
                    if (featuresOfCategory.size() > 0) {
                        CategoryController.getInstance().addNewCategory(new Category(maxAmount.getText(), featuresOfCategory));
                    } else
                        ClientController.getInstance().getCurrentMenu().showMessage("you should add one feature to category at least", MessageKind.ErrorWithoutBack);
                }else{
                    maxAmount.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Category name is empty");
                }
            }
        });
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }







}

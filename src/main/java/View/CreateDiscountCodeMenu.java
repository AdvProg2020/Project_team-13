package View;

import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Controller.Client.UserController;
import Models.DiscountCode;
import Models.UserAccount.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CreateDiscountCodeMenu extends Menu {
    private TextField maxAmount, firstName, lastName, email, credit, phoneNumber;
    private TextField discountPercent;
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private HashMap<String, Integer> maxUsingTime = new HashMap<>();
    private HashMap<String, Integer> remainingTimesForEachCustomer = new HashMap<>();
    private ArrayList<String> allUsers = new ArrayList<>();
    String imagePath = "";
    GridPane userInfoGridPane;

    public CreateDiscountCodeMenu(Stage stage) {
        super(stage);
        UserController.getInstance().getAllUserFromServer();
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
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        for (Customer customer : UserController.getInstance().getAllCustomers()) {
            comboBox.getItems().add(customer.getUsername());
        }
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
        Text title = new Text("\t  Create Discount Code");
        ImageView userImage = new ImageView(new Image("file:src/user_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text maxAmountText = new Text("Max Discount Amount");
        Text discountPercentText = new Text("Percent");
        Text startDateText = new Text("Start Date");
        Text endDateText = new Text("End Date");
        Text firstNameText = new Text("Username");
        Text lastNameText = new Text("Numbers");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button signUp = new Button("Create DiscountCode");
        signUp.setStyle("-fx-background-color: #E85D9E;");
        signUp.setMinWidth(100);
        signUp.setTextFill(Color.WHITE);
        Button adduser = new Button("Add User");
        adduser.setStyle("-fx-background-color: #E85D9E;");
        adduser.setMinWidth(100);
        adduser.setTextFill(Color.WHITE);
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        editPhotoButton.setStyle("-fx-background-color: #E85D9E");
        editPhotoButton.setMinWidth(100);
        editPhotoButton.setTextFill(Color.WHITE);
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
        //  userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        // upGridPane.setGridLinesVisible(true);
        upGridPane.add(title, 1, 0, 1, 1);
        maxAmount.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        credit.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        userInfoGridPane.add(maxAmountText, 5, 9, 3, 1);
        userInfoGridPane.add(discountPercentText, 5, 10, 3, 1);
        userInfoGridPane.add(startDateText, 5, 11, 3, 1);
        userInfoGridPane.add(endDateText, 5, 12, 3, 1);
        userInfoGridPane.add(firstNameText, 15, 9, 3, 1);
        userInfoGridPane.add(lastNameText, 15, 10, 3, 1);
        userInfoGridPane.add(adduser, 15, 11, 3, 1);
        userInfoGridPane.add(maxAmount, 8, 9, 6, 1);
        userInfoGridPane.add(discountPercent, 8, 10, 6, 1);
        userInfoGridPane.add(startDatePicker, 8, 11, 6, 1);
        userInfoGridPane.add(endDatePicker, 8, 12, 6, 1);
        userInfoGridPane.add(comboBox, 19, 9, 6, 1);
        userInfoGridPane.add(lastName, 19, 10, 6, 1);
        userInfoGridPane.add(signUp, 13, 16, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        adduser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!((String) comboBox.getValue()).equals("")) {
                    if (lastName.getText().matches("\\d+")) {
                        if (UserController.getInstance().isThereCustomerWithThisUsername(((String) comboBox.getValue()))) {
                            if (!allUsers.contains(((String) comboBox.getValue()))) {
                                allUsers.add(((String) comboBox.getValue()));
                                remainingTimesForEachCustomer.put(((String) comboBox.getValue()), Integer.parseInt(lastName.getText()));
                                maxUsingTime.put(((String) comboBox.getValue()), Integer.parseInt(lastName.getText()));
                                comboBox.setValue("");
                                lastName.setText("");
                            } else
                                ClientController.getInstance().getCurrentMenu().showMessage("this user already has this discount code", MessageKind.ErrorWithoutBack);
                        } else
                            ClientController.getInstance().getCurrentMenu().showMessage("there is no user with this username", MessageKind.ErrorWithoutBack);
                    } else {
                        lastName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("Number of discount is invalid.");
                    }
                } else {
                    comboBox.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("username is invalid.");
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
                endDatePicker.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                comboBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (checkdiscountPercentIsvalid(discountPercent.getText().trim())) {
                    if (checkmaxAmountIsvalid(maxAmount.getText())) {
                        Date startdate = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date enddate = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        if (checkStartTimeValid(startdate)) {
                            if (checkEndTimeValid(enddate, startdate)) {
                                if (allUsers.size() > 0) {
                                    DiscountCode discountCode = new DiscountCode(startdate, enddate, allUsers, Integer.parseInt(discountPercent.getText()), Double.parseDouble(maxAmount.getText()), maxUsingTime, remainingTimesForEachCustomer);
                                    DiscountController.getInstance().createDiscountCode(discountCode);
                                } else
                                    ClientController.getInstance().getCurrentMenu().showMessage("you should add discount code to some of users", MessageKind.ErrorWithoutBack);
                            } else {
                                endDatePicker.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                errorText.setText("End time is invalid.");
                            }
                        } else {
                            startDatePicker.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                            errorText.setText("Start date is invalid.");
                        }
                    } else {
                        maxAmount.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("Max Amount is invalid. correct format 0-9 A-z");
                    }
                } else {
                    discountPercent.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Discount Percent Format is Invalid. use 0-9 alphabetical character.");
                }
            }
        });
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkdiscountPercentIsvalid(String word) {
        if (word.matches("\\d+") && Double.parseDouble(word) <= 100) {
            return true;
        }
        return false;
    }

    private boolean checkNameIsvalid(String name) {
        if (Pattern.matches("\\w+", name) && !name.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkEmailIsvalid(String email) {
        if (Pattern.matches("\\w+\\.?\\w*@\\w+\\.\\w+", email)) {
            return true;
        }
        return false;
    }

    private boolean checkmaxAmountIsvalid(String maxAmount) {
        if (Pattern.matches("\\d+", maxAmount)) {
            return true;
        }
        return false;
    }

    private boolean checkEndTimeValid(Date endTime, Date startTime) {
        if (endTime.after(new Date()) && endTime.after(startTime)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkStartTimeValid(Date startTime) {
        if (startTime.after(new Date())) {
            return true;
        } else {
            return false;
        }
    }
}

package View;

import Controller.Client.ClientController;
import Controller.Client.UserController;
import Controller.Client.OffsController;
import Models.Offer;
import Models.Product.Product;
import Models.UserAccount.Seller;
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

public class EditOffsMenu extends Menu{
    private TextField maxAmount, firstName;
    Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
    private TextField discountPercent;
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private ChoiceBox choiceBox = new ChoiceBox();
    private ArrayList<String> allProducts = new ArrayList<>();
    String imagePath = "";
    GridPane userInfoGridPane;

    public EditOffsMenu(Stage stage) {
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
        Offer offer= OffsController.getInstance().getCurrentOffer();
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        maxAmount = new TextField();
        discountPercent = new TextField();
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(100);
        Text title = new Text("Create Offs");
        ImageView userImage = new ImageView(new Image("file:src/user_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text maxAmountText = new Text("Max Discount Amount");
        Text discountPercentText = new Text("Percent");
        Text startDateText = new Text("Start Date");
        Text endDateText = new Text("End Date");
        Text firstNameText = new Text("Choose Product");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button signUp = new Button("Create Offs");
        signUp.setStyle("-fx-background-color: #E85D9E;");
        signUp.setMinWidth(100);
        signUp.setTextFill(Color.WHITE);
        Button adduser = new Button("Add Product");
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
        int i = 1;
        for (Product product : seller.getAllProducts()) {
            if(offer.getProductByIdInOfferList(product.getProductId())==null) {
                choiceBox.getItems().add(i + ". " + product.getProductName());
                i++;
            }
        }
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        allProducts=offer.getProducts();
        startDatePicker.setValue(offer.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        endDatePicker.setValue(offer.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        discountPercent.setText(Double.valueOf(offer.getAmount()).toString());
        upGridPane.add(hBox1, 0, 0, 1, 1);
        upGridPane.add(title, 1, 0, 1, 1);
        maxAmount.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        discountPercent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
        choiceBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30;");
        userInfoGridPane.add(discountPercentText, 5, 9, 3, 1);
        userInfoGridPane.add(startDateText, 5, 10, 3, 1);
        userInfoGridPane.add(endDateText, 5, 11, 3, 1);
        userInfoGridPane.add(firstNameText, 15, 9, 5, 1);
        userInfoGridPane.add(adduser, 15, 11, 3, 1);
        userInfoGridPane.add(discountPercent, 8, 9, 6, 1);
        userInfoGridPane.add(startDatePicker, 8, 10, 6, 1);
        userInfoGridPane.add(endDatePicker, 8, 11, 6, 1);
        userInfoGridPane.add(choiceBox, 20, 9, 6, 1);
        userInfoGridPane.add(signUp, 13, 16, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        adduser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!((String) choiceBox.getValue()).equals("")) {
                    allProducts.add(seller.getAllProducts().get(Integer.parseInt(((String) choiceBox.getValue()).substring(0, 1)) - 1).getProductId());
                    choiceBox.getItems().remove((choiceBox.getValue()));
                } else {
                    choiceBox.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
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
                endDatePicker.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                choiceBox.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                if (checkdiscountPercentIsvalid(discountPercent.getText().trim())) {
                    Date startdate = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date enddate = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (checkStartTimeValid(startdate)) {
                        if (checkEndTimeValid(enddate, startdate)) {
                            if (allProducts.size() > 0) {
                                Offer offeredited=new Offer(Double.parseDouble(discountPercent.getText()), offer.getSeller(),allProducts,startdate,enddate);
                                offeredited.setOfferId(offer.getOfferId());
                                OffsController.getInstance().editOff(offeredited);
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

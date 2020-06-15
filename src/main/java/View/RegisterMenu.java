package View;

import Controller.Client.ClientController;
import Controller.Client.RegisterController;
import Models.UserAccount.Customer;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.regex.Pattern;

public class RegisterMenu  extends Menu {
    private TextField userName,firstName,lastName,email,credit,phoneNumber;
    private PasswordField passWord;
    String imagePath="";
    GridPane userInfoGridPane;

    public RegisterMenu(Stage stage) {
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
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        userName=new TextField();
        passWord=new PasswordField();
        firstName=new TextField();
        lastName=new TextField();
        phoneNumber=new TextField();
        email=new TextField();
        credit=new TextField();
        GridPane leftGridPane= new GridPane();
        GridPane upGridPane= new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(100);
        Text title=new Text("Customer Register");
        ImageView userImage=new ImageView(new Image("file:src/user_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text usernameText=new Text("Username");
        Text passwordText=new Text("Password");
        Text phonenumberText=new Text("Phone Number");
        Text emailText=new Text("Email");
        Text creditText=new Text("Credit");
        Text firstNameText=new Text("First Name");
        Text lastNameText=new Text("Last Name");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button signUp = new Button("Sign Up");
        signUp.setStyle("-fx-background-color: #E85D9E;");
        signUp.setMinWidth(100);
        signUp.setTextFill(Color.WHITE);
        Text errorText=new Text();
        errorText.setFill(Color.RED);
        editPhotoButton.setStyle("-fx-background-color: #E85D9E");
        editPhotoButton.setMinWidth(100);
        editPhotoButton.setTextFill(Color.WHITE);
        FileChooser fileChooser = new FileChooser();
        EventHandler<ActionEvent> eventChoosePhoto = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File selectedFile = fileChooser.showOpenDialog(stage);
                if(selectedFile!=null) {
                    userImage.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
                    imagePath="file:"+selectedFile.getAbsolutePath();
                }
            }
        };
        editPhotoButton.setOnAction(eventChoosePhoto);
       // userInfoGridPane.add(title,0,0);
        HBox hBox=new HBox();
        hBox.setMinWidth(230);
        HBox hBox1=new HBox();
        hBox1.setMinWidth(250);
      //  userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1,0,0,1,1);
       // upGridPane.setGridLinesVisible(true);
        upGridPane.add(title,1,0,1,1);
        userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        credit.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        firstName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        userInfoGridPane.add(userImage,13,1,5,5);
        userInfoGridPane.add(usernameText,5,9,3,1);
        userInfoGridPane.add(passwordText,5,10,3,1);
        userInfoGridPane.add(emailText,5,11,3,1);
        userInfoGridPane.add(creditText,5,12,3,1);
        userInfoGridPane.add(firstNameText,15,9,3,1);
        userInfoGridPane.add(lastNameText,15,10,3,1);
        userInfoGridPane.add(phonenumberText,15,11,3,1);
        userInfoGridPane.add(userName,8,9,6,1);
        userInfoGridPane.add(passWord,8,10,6,1);
        userInfoGridPane.add(email,8,11,6,1);
        userInfoGridPane.add(credit,8,12,6,1);
        userInfoGridPane.add(firstName,19,9,6,1);
        userInfoGridPane.add(lastName,19,10,6,1);
        userInfoGridPane.add(phoneNumber,19,11,6,1);
        userInfoGridPane.add(editPhotoButton,13,6,5,1);
        userInfoGridPane.add(signUp,13,16,5,1);
        userInfoGridPane.add(errorText,7,14,10,1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        centerGridPane.add(userInfoGridPane, 2, 2, 1, 1);
        centerGridPane.add(upGridPane, 1, 1, 2, 1);
        centerGridPane.add(leftGridPane, 0, 0, 1, 1);
        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                passWord.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                credit.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                firstName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                errorText.setText("");
                if (checkPasswordIsvalid(passWord.getText().trim())) {
                    if (checkNameIsvalid(firstName.getText().trim())) {
                        if (checkNameIsvalid(lastName.getText().trim())) {
                            if (checkEmailIsvalid(email.getText().trim())) {
                                if (Pattern.matches("\\d+", phoneNumber.getText().trim()) && phoneNumber.getText().trim().length() == 11&&phoneNumber.getText().charAt(0)=='0') {
                                    if(checkUsernameIsvalid(userName.getText())) {
                                        if(checkCreditIsvalid(credit.getText())) {
                                            Customer customer = new Customer(userName.getText(), passWord.getText(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), Double.parseDouble(credit.getText()));
                                            customer.setImagePath(imagePath);
                                            ClientController.getInstance().setCurrentUser(customer);
                                            RegisterController.getInstance().createNewUserAccount(customer);
                                        }else{
                                            credit.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                            errorText.setText("credit is not a number.");
                                        }
                                    }else{
                                        userName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                        errorText.setText("Username is invalid. correct format 0-9 A-z");
                                    }

                                } else {
                                    phoneNumber.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                    errorText.setText("Phone number is invalid. Correct format:09xxxxxxxxx"); }
                            } else {
                                email.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                errorText.setText("Email format is invalid. Correct Format: for@example.com");
                            }
                        } else {
                            lastName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                            errorText.setText("LastName format is invalid. use alphabetical characters");
                        }
                    } else {
                       firstName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("FirstName format is invalid. use alphabetical characters");
                    }
                } else {
                   passWord.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Password Format is Invalid. use 0-9 alphabetical character.");
                }
            }
        });
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }
    private boolean checkPasswordIsvalid(String word) {
        if (word.length() > 8 && word.length() < 18) {
            return true;
        }
        return false;
    }

    private boolean checkNameIsvalid(String name) {
        if (Pattern.matches("(([a-z]|[A-Z])+ )*(([a-z]|[A-Z])+)", name) && !name.isEmpty()) {
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
    private boolean checkUsernameIsvalid(String username) {
        if (Pattern.matches("\\w+", username)) {
            return true;
        }
        return false;
    }
    private boolean checkCreditIsvalid(String credit) {
        if (Pattern.matches("\\d+", credit)) {
            return true;
        }
        return false;
    }
}

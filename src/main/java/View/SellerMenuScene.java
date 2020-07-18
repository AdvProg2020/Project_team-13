package View;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Controller.Client.MessageController;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.regex.Pattern;

public class SellerMenuScene extends Menu {
    GridPane userInfoGridPane;

    public SellerMenuScene(Stage stage) {
        super(stage);
        this.stage = stage;
        userInfoGridPane = new GridPane();
        if (ClientController.getInstance().getMediaPlayer() != null)
            ClientController.getInstance().getMediaPlayer().stop();
        ClientController.getInstance().setMediaPlayer(new MediaPlayer(usersSong));
        ClientController.getInstance().getMediaPlayer().setVolume(0.02);
        ClientController.getInstance().getMediaPlayer().play();
        ClientController.getInstance().getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
    }

    private void setCenterGridPane() {
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        Text personalInfo = new Text(seller.viewPersonalInfo());
        Text pageTitle = new Text("User Menu");
        personalInfo.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        pageTitle.setStyle("-fx-font-weight: bold;");
        pageTitle.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        ImageView userIcon;
        if (!seller.getImagePath().equals("")) {
            userIcon = new ImageView(new Image(seller.getImagePath()));
            if (userIcon.getImage().getHeight() == 0) {
                userIcon.setImage(new Image("file:src/user_icon.png"));
            }
        } else {
            userIcon = new ImageView(new Image("file:src/user_icon.png"));
        }
        userIcon.setFitHeight(100);
        userIcon.setFitWidth(100);
        ImageView editInfoPic = new ImageView(new Image("file:src/edit.png"));
        ImageView editPic = new ImageView(new Image("file:src/edit_user_photo.png"));
        editInfoPic.setFitWidth(25);
        editInfoPic.setFitHeight(25);
        editPic.setFitWidth(25);
        editPic.setFitHeight(25);
        Button editInfoButton = new Button("");
        editInfoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Edit information.");
                TextField password, firstName, lastName, email, phoneNumber, companyName;
                Label firstName1, lastName1, email1, phoneNumber1, password1, companyName1;
                password = new PasswordField();
                password.setText(seller.getPassword());
                firstName = new TextField(seller.getFirstName());
                lastName = new TextField(seller.getLastName());
                email = new TextField(seller.getEmail());
                phoneNumber = new TextField(seller.getPhoneNumber());
                companyName = new TextField(seller.getCompanyName());
                password.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 120px;");
                firstName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width:120px;");
                lastName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 120px;");
                phoneNumber.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 120px;");
                email.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 120px;");
                companyName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 120px;");
                password.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                firstName.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                lastName.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                phoneNumber.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                email.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                companyName.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 14));
                password1 = new Label("Password");
                firstName1 = new Label("First Name");
                lastName1 = new Label("Last Name");
                email1 = new Label("Email");
                phoneNumber1 = new Label("Phone Number");
                companyName1 = new Label("Company Name");
                password1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                firstName1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                lastName1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                phoneNumber1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                email1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                companyName1.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                Text errors = new Text();
                errors.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 15));
                errors.setFill(Color.RED);
                GridPane gridPane = new GridPane();
                gridPane.add(password, 1, 0);
                gridPane.add(password1, 0, 0);
                gridPane.add(firstName, 1, 1);
                gridPane.add(firstName1, 0, 1);
                gridPane.add(lastName, 1, 2);
                gridPane.add(lastName1, 0, 2);
                gridPane.add(email, 1, 3);
                gridPane.add(email1, 0, 3);
                gridPane.add(phoneNumber, 1, 4);
                gridPane.add(phoneNumber1, 0, 4);
                gridPane.add(companyName, 1, 5);
                gridPane.add(companyName1, 0, 5);
                Button button2 = new Button("Edit");
                button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        errors.setText("");
                        if (checkPasswordIsvalid(password.getText().trim())) {
                            if (checkNameIsvalid(firstName.getText().trim())) {
                                if (checkNameIsvalid(lastName.getText().trim())) {
                                    if (checkEmailIsvalid(email.getText().trim())) {
                                        if (Pattern.matches("\\d+", phoneNumber.getText().trim()) && phoneNumber.getText().trim().length() == 11 && phoneNumber.getText().charAt(0) == '0') {
                                            if (checkNameIsvalid(companyName.getText())) {
                                                seller.setFirstName(firstName.getText().trim());
                                                seller.setLastName(lastName.getText().trim());
                                                seller.setEmail(email.getText().trim());
                                                seller.setPhoneNumber(phoneNumber.getText().trim());
                                                seller.setPassword(password.getText().trim());
                                                seller.setCompanyName(companyName.getText().trim());
                                                ClientController.getInstance().sendMessageToServer("@editSeller@" + new Gson().toJson(seller));
                                                personalInfo.setText(seller.viewPersonalInfo());
                                                popupwindow.close();
                                            } else {
                                                errors.setText("Company name format is invalid\nuse alphabetical characters");
                                            }
                                        } else {
                                            errors.setText("Phone number is invalid.\nCorrect format:09xxxxxxxxx");
                                        }
                                    } else {
                                        errors.setText("Email format is invalid.\nCorrect Format:ali@ali.com");
                                    }
                                } else {
                                    errors.setText("LastName format is invalid\nuse alphabetical characters");
                                }
                            } else {
                                errors.setText("FirstName format is invalid\nuse alphabetical characters");
                            }
                        } else {
                            errors.setText("Password Format is Invalid.\nuse 9-17 alphabetical character.");
                        }
                    }
                });
                button2.setStyle("-fx-text-fill: white;-fx-background-color:rgba(76, 170, 240, 1);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 28px;-fx-pref-width: 55px;");
                for (int i = 0; i < 6; i++) {
                    gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 20, Priority.NEVER, VPos.CENTER, true));
                }
                gridPane.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, 50, Priority.NEVER, VPos.TOP, true));
                gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 20, Priority.NEVER, VPos.CENTER, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(120, Control.USE_COMPUTED_SIZE, 120, Priority.NEVER, HPos.CENTER, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(135, Control.USE_COMPUTED_SIZE, 135, Priority.NEVER, HPos.CENTER, true));
                gridPane.add(errors, 0, 6, 2, 2);
                gridPane.add(button2, 0, 7, 2, 2);
                gridPane.setVgap(10);
                gridPane.setHgap(5);
                gridPane.setStyle("-fx-background-color: #ECD5DC;");
                gridPane.setAlignment(Pos.CENTER);
                Scene scene1 = new Scene(gridPane, 350, 300);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });


        Button increaseCredit = new Button("Change Credit");
        increaseCredit.setStyle("-fx-font-size:  18;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        increaseCredit.setMinHeight(50);
        increaseCredit.setMinWidth(150);
        increaseCredit.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);

            }
        });
        increaseCredit.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        increaseCredit.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
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
                Text titleText = new Text("Amount:");
                Text contentText = new Text("Account ID:");
                TextField getTitle = new TextField();
                TextField getContent = new TextField();
                getTitle.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                getContent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;");
                getContent.setMaxWidth(300);

                Button addCommentButton = new Button("Increase Credit");
                addCommentButton.setStyle("-fx-background-color: #E85D9E;");
                addCommentButton.setMinWidth(100);
                Button decreaseCerdit = new Button("Decrease Credit");
                decreaseCerdit.setStyle("-fx-background-color: #E85D9E;");
                decreaseCerdit.setTextFill(Color.WHITE);
                decreaseCerdit.setMinWidth(100);
                commentPane.setVgap(10);
                commentPane.setHgap(10);
                addCommentButton.setTextFill(Color.WHITE);
                commentPane.add(titleText, 0, 0);
                commentPane.add(contentText, 0, 1);
                commentPane.add(getTitle, 1, 0);
                commentPane.add(getContent, 1, 1, 1, 5);
                commentPane.add(addCommentButton, 1, 6);
                commentPane.add(decreaseCerdit, 1, 7);
                addCommentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(checkAccountIsValid(getContent.getText()) && checkAmountIsValid(getTitle.getText()))
                        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("increaseCredit",  ClientController.getInstance().getCurrentUser().getUsername() + "//" + ClientController.getInstance().getCurrentUser().getPassword() +
                                "//" + getContent.getText() + "//" + getTitle.getText()));
                        else{
                            ClientController.getInstance().getCurrentMenu().showMessage("your inout must be valid " + String.valueOf(CartController.getInstance().getAtLeastCredit()), MessageKind.ErrorWithoutBack);
                        }
                        popupwindow.hide();
                    }
                });
                decreaseCerdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(checkAccountIsValid(getContent.getText()) && checkAmountIsValid(getTitle.getText())) {
                            CartController.getInstance().getAtLeastCreditFromServer();
                            double net = ClientController.getInstance().getCurrentUser().getCredit() - Double.parseDouble(getTitle.getText());
                            if (CartController.getInstance().getAtLeastCredit() <= net) {
                                ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("decreaseCredit", ClientController.getInstance().getCurrentUser().getUsername() + "//" + ClientController.getInstance().getCurrentUser().getPassword() +
                                        "//" + getContent.getText() + "//" + getTitle.getText()));
                            } else {
                                ClientController.getInstance().getCurrentMenu().showMessage("your credit must be at least " + String.valueOf(CartController.getInstance().getAtLeastCredit()), MessageKind.ErrorWithoutBack);
                            }
                        }else {
                            ClientController.getInstance().getCurrentMenu().showMessage("your inout must be valid " + String.valueOf(CartController.getInstance().getAtLeastCredit()), MessageKind.ErrorWithoutBack);
                        }
                        popupwindow.hide();
                    }
                });
                Scene scene1 = new Scene(gridPane, 400, 300);
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.initStyle(StageStyle.UNDECORATED);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });

        editInfoButton.setGraphic(editInfoPic);
        Button editPhotoButton = new Button("");
        FileChooser fileChooser = new FileChooser();
        editPhotoButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                userIcon.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
                seller.setImagePath("file:" + selectedFile.getAbsolutePath());
                ClientController.getInstance().sendMessageToServer("@editSeller@" + new Gson().toJson(seller));
            }
        });
        editPhotoButton.setGraphic(editPic);
        userInfoGridPane.setVgap(20);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(600);
        userInfoGridPane.setMaxHeight(300);
        userInfoGridPane.add(personalInfo, 2, 1, 1, 2);
        userInfoGridPane.add(userIcon, 1, 1, 1, 1);
        userInfoGridPane.add(editInfoButton, 9, 2, 1, 1);
        userInfoGridPane.add(editPhotoButton, 10, 2, 1, 1);
        GridPane leftMenuGridPane = new GridPane();
        leftMenuGridPane.setMinHeight(400);
        leftMenuGridPane.setStyle("-fx-background-color:rgba(45, 156, 240, 1);");
        Button manageProducts = new Button("Manage Products");
        manageProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new ManageProducts(stage).execute();
            }
        });
        manageProducts.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        manageProducts.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        manageProducts.setStyle("-fx-font-size:  16;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        manageProducts.setMinHeight(50);
        manageProducts.setMinWidth(150);
        Button offsButton = new Button("Offs");
        offsButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        offsButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        offsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MangeOffsMenu(stage, 0).execute();
            }
        });
        offsButton.setTextAlignment(TextAlignment.CENTER);
        offsButton.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        offsButton.setMinHeight(50);
        offsButton.setMinWidth(150);
        Button salesHistoryButton = new Button("Sales History");
        salesHistoryButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);

            }
        });
        salesHistoryButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        salesHistoryButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                new SalesHistoryMenu(stage, 0).execute();
            }
        });
        salesHistoryButton.setTextAlignment(TextAlignment.CENTER);
        salesHistoryButton.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        salesHistoryButton.setMinHeight(50);
        salesHistoryButton.setMinWidth(150);
        salesHistoryButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        salesHistoryButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        leftMenuGridPane.add(manageProducts, 0, 0, 2, 2);
        leftMenuGridPane.add(offsButton, 0, 2, 2, 2);
        leftMenuGridPane.add(salesHistoryButton, 0, 6, 2, 2);
        leftMenuGridPane.add(increaseCredit, 0, 8, 2, 2);
        centerGridPane.add(leftMenuGridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 1, 1);
        centerGridPane.add(userInfoGridPane, 3, 1, 2, 2);

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

    private boolean checkAmountIsValid(String amount){
        return amount.matches("\\d+");
    }

    private boolean checkAccountIsValid(String account){
        return account.matches("@a\\d{5}");
    }
}

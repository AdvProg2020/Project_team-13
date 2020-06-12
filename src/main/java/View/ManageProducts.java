package View;

import Models.Product.Product;
import Models.UserAccount.Seller;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ManageProducts extends Menu {
    GridPane userInfoGridPane;

    public ManageProducts(Stage stage) {
        super(stage);
        this.stage = stage;
        userInfoGridPane = new GridPane();
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
        //  Customer customer=(Customer) ClientController.getInstance().getCurrentUser();
        Text personalInfo = new Text("ali");
        Text pageTitle = new Text("User Menu");
        personalInfo.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        pageTitle.setStyle("-fx-font-weight: bold;");
        pageTitle.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        ImageView editInfoPic = new ImageView(new Image("file:src/edit.png"));
        ImageView editPic = new ImageView(new Image("file:src/edit_user_photo.png"));
        editInfoPic.setFitWidth(25);
        editInfoPic.setFitHeight(25);
        editPic.setFitWidth(25);
        editPic.setFitHeight(25);
        Seller seller = new Seller("username", "password", "firstname",
                "lastName", "email", "phone number", 123,
                "ali", true);
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hashMap.put(Integer.toString(i), Integer.toString(i * i + i + 1));
        }
        for (int kk = 0; kk < 24; kk++) {
            Product product = new Product("aaaa", "aaaa", "aaaa", seller, 123, "lai", "svdfv", 123, hashMap);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text(product.getProductName() + "\n" + product.getCostAfterOff() + " $");
            Label label = new Label(Double.toString(product.getAverageScore()));
            ImageView star = new ImageView(new Image("file:src/star.png"));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            star.setFitWidth(15);
            star.setFitHeight(15);
            GridPane scoreGridPane = new GridPane();
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(15, Control.USE_COMPUTED_SIZE, 20, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.setHgap(2);
            scoreGridPane.add(label, 0, 0);
            scoreGridPane.add(star, 1, 0);
//            Button viewProduct=new Button(View)
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 1, 1);

            gridPane.setStyle("-fx-border-color: Black");
            gridPanes.add(gridPane);
        }
        Button editInfoButton = new Button("");
        editInfoButton.setGraphic(editInfoPic);
        Button editPhotoButton = new Button("");
        FileChooser fileChooser = new FileChooser();
        editPhotoButton.setGraphic(editPic);
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(10);
        userInfoGridPane.setMinWidth(600);
        userInfoGridPane.setMaxHeight(300);
        for (int i = 0; i < 5; i++) {
            userInfoGridPane.add(gridPanes.get(i), i, 1, 1, 2);
        }

        GridPane leftMenuGridPane = new GridPane();
        leftMenuGridPane.setMinHeight(400);
        leftMenuGridPane.setStyle("-fx-background-color:rgba(45, 156, 240, 1);");
        Button categoriesButton = new Button("Add product");
        categoriesButton.setTextAlignment(TextAlignment.CENTER);
        categoriesButton.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        categoriesButton.setMinHeight(50);
        categoriesButton.setMinWidth(150);
        leftMenuGridPane.add(categoriesButton, 0, 2, 2, 2);
        centerGridPane.add(leftMenuGridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 1, 1);
        centerGridPane.add(userInfoGridPane, 3, 1, 2, 2);

    }

    protected void setUpGridPane() {
        Label label = new Label("        Pms.com");
        label.setStyle("-fx-font-weight: bold;");
        label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        upGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, VPos.CENTER, true));
        upGridPane.add(label, 0, 0);
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
}


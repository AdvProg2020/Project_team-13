package View;

import Models.UserAccount.Customer;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EnterMenuScene extends Menu {

    public EnterMenuScene(Stage stage) {
        super(stage);
        this.stage = stage;
        upGridPane = new GridPane();
        menuBarGridPane = new GridPane();
        centerGridPane = new GridPane();
        bottomGridPane = new GridPane();
        pageGridPane = new GridPane();
        scene = new Scene(pageGridPane, 850, 600);
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        bottomGridPane.setStyle("-fx-background-color: rgba(45, 156, 240, 1);");
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
        scene.setRoot(pageGridPane);
        Customer customer = new Customer("mamooti", "majidmajid", "Mahmood", "Ahmadi nejad", "Mamooti@yahoo.com", "09123456789", 10000);
        Text personalInfo = new Text(customer.viewPersonalInfo());
        System.out.println(personalInfo.getText());
        centerGridPane.add(personalInfo, 0, 0);
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
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, VPos.TOP, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("This is a pop up window");
        Label label1 = new Label("Pop up window now displayed");
        Button button1 = new Button("Close this pop up window");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }
}

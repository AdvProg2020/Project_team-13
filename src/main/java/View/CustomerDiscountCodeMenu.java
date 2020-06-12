package View;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CustomerDiscountCodeMenu extends Menu {
    private BorderPane borderPane;
    private Button[] viewDetails;

    public CustomerDiscountCodeMenu(Stage stage) {
        super(stage);
        super.setScene();
        this.setScene();
    }

    @Override
    public void setScene() {
        borderPane = new BorderPane();
        viewDetails = new Button[7];
        pageGridPane.getChildren().remove(centerGridPane);
        pageGridPane.getChildren().remove(bottomGridPane);
        for (int i = 0; i < 7; i++) {
            viewDetails[i] = new Button("View Details");
            viewDetails[i].setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
            viewDetails[i].setStyle("-fx-background-color: rgba(128,128,128,0.82)");
        }
        this.setTheCenterInfo();
    }

    private void setTheCenterInfo() {
        Pagination pagination = new Pagination();
        pagination.setPageCount(4);
        Label discountCodes = new Label("Discount Codes");
        discountCodes.setTranslateX(70);
        discountCodes.setTranslateY(40);
        discountCodes.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getChildren().add(discountCodes);
        GridPane infoGridPane = new GridPane();
//        infoGridPane.getColumnConstraints().add(new ColumnConstraints());
//        infoGridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Label id = new Label("ID");
        Label discountCodePercent = new Label("Percent");
        Label maxDiscountCodePercent = new Label("MaxAmount");
        Label startTime = new Label(" StartTime");
        Label endTime = new Label(" EndTime");
        id.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        discountCodePercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        maxDiscountCodePercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        startTime.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        endTime.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        infoGridPane.setVgap(30);
        for (int i = 0; i < 5; i++) {
            infoGridPane.getColumnConstraints().add(new ColumnConstraints(120, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        }
        for (int i = 1; i <= 4; i++) {
            setTheRows(infoGridPane, i);
        }
        infoGridPane.setTranslateX(80);
        infoGridPane.setTranslateY(60);
        infoGridPane.add(id, 0, 0);
        infoGridPane.add(discountCodePercent, 1, 0);
        infoGridPane.add(maxDiscountCodePercent, 2, 0);
        infoGridPane.add(startTime, 3, 0);
        infoGridPane.add(endTime, 4, 0);
        Border border = new Border(new BorderStroke(Color.web("#808080"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL));
        pagination.setTranslateX(4);
        pagination.setTranslateY(70);
        borderPane.setBottom(pagination);
        borderPane.setBorder(border);
        borderPane.setTop(hBox);
        borderPane.setCenter(infoGridPane);
        pageGridPane.add(borderPane,  0, 2);
    }

    private void setTheRows(GridPane infoGridPane, int row) {
        Pane[] allPanes = new Pane[5];
        for (int i = 0; i < 5; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker("@d102333", "12%", "40%", "20/2/2020", "14/3/2020");
        for (int i = 0; i < 5; i++) {
           allLabels[i].setAlignment(Pos.CENTER);
           allPanes[i].getChildren().add(allLabels[i]);
           if(i==0){
               allLabels[i].setTranslateX(15);
           }
           if(i==1){
               allLabels[i].setTranslateX(35);
           }
           if(i==2){
               allLabels[i].setTranslateX(35);
           }
           if(i==3){
               allLabels[i].setTranslateX(20);
           }
           if(i==4){
               allLabels[i].setTranslateX(20);
           }
           allLabels[i].setTranslateY(8);
           GridPane.setHalignment(allPanes[i], HPos.CENTER);
           infoGridPane.add(allPanes[i], i, row);
        }
        Group pane = new Group();
        viewDetails[row].setMaxSize(200, 50);
        pane.getChildren().add(viewDetails[row]);
        pane.setStyle("-fx-background-color: #808080");
        infoGridPane.add(pane, 5, row);
    }

    private Label[] labelMaker(String id, String discountCodePercent, String maxDiscountCodePercent, String StartDate, String EndDate) {
        Label[] allLabels = new Label[5];
        allLabels[0] = new Label(id);
        allLabels[1] = new Label(discountCodePercent);
        allLabels[2] = new Label(maxDiscountCodePercent);
        allLabels[3] = new Label(StartDate);
        allLabels[4] = new Label(EndDate);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }


}

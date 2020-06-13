package View;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomerDiscountCodeMenu extends Menu{
    private BorderPane borderPane;
    private Button[] viewDetails;
    private Pagination pagination;
    private GridPane infoGridPane;
    private HBox hBox;

    public CustomerDiscountCodeMenu(Stage stage) {
        super(stage);
        super.setScene();
        this.setScene();
    }

    @Override
    public void setScene() {
        pageGridPane.getChildren().remove(centerGridPane);
        pageGridPane.getChildren().remove(bottomGridPane);
        pagination = new Pagination();
        pagination.setPageCount(4);
        pagination.setTranslateX(50);
        pagination.setTranslateY(50);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(param);
            }
        });
        setTheBeginning();
        Label discountCodes = new Label("Discount Codes");
        discountCodes.setTranslateX(20);
        discountCodes.setTranslateY(20);
        discountCodes.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
        hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getChildren().add(discountCodes);
        borderPane.setTop(hBox);
        borderPane.setCenter(pagination);
        pageGridPane.add(borderPane, 0, 2);
    }

    private Node createPage(Integer param) {
        setTheBeginning();
        if(param.equals(0)){
            return setTheCenterInfo( "@d12421",  "22%",  "21%",  "2/3/2020",  "5/3/2020");
        }
        return setTheCenterInfo( "@d134431",  "2",  "12",  "234",  "236");
    }

    private void setTheBeginning() {
        borderPane = new BorderPane();
        viewDetails = new Button[7];
        for (int i = 0; i < 7; i++) {
            viewDetails[i] = new Button("View Details");
            viewDetails[i].setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
            viewDetails[i].setStyle("-fx-background-color: rgba(128,128,128,0.82)");
        }
    }

    private GridPane setTheCenterInfo(String ids, String discountCodePercents, String maxDiscountCodePercents, String StartDate, String EndDate) {
        infoGridPane = new GridPane();
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
            setTheRows(i, ids, discountCodePercents,maxDiscountCodePercents, StartDate, EndDate);
        }
        infoGridPane.setTranslateX(20);
        infoGridPane.setTranslateY(1);
        infoGridPane.add(id, 0, 0);
        infoGridPane.add(discountCodePercent, 1, 0);
        infoGridPane.add(maxDiscountCodePercent, 2, 0);
        infoGridPane.add(startTime, 3, 0);
        infoGridPane.add(endTime, 4, 0);
        return infoGridPane;
    }

    private void setTheRows(int row, String id, String discountCodePercent, String maxDiscountCodePercent, String StartDate, String EndDate) {
        Pane[] allPanes = new Pane[5];
        for (int i = 0; i < 5; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(id, discountCodePercent, maxDiscountCodePercent, StartDate, EndDate);
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

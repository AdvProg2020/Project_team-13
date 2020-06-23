package View;
import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Models.DiscountCode;
import Models.UserAccount.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class CustomerDiscountCodeMenu extends Menu{
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int discountCodeCounter;
    private List<GridPane> allGridPanes;
    private Pagination pagination;
    private List<DiscountCode> allDiscountCodes;

    public CustomerDiscountCodeMenu(Stage stage) {
        super(stage);
        super.setScene();
        viewDetails = new HashMap<>();
        this.setScene();
    }


    public void setPages(int pages) {
        this.pages = pages;
    }


    public void setDiscountCodeCounter(int discountCodeCounter) {
        this.discountCodeCounter = discountCodeCounter;
    }



    public void setScene() {
        DiscountController.getInstance().getAllDiscountCodesFromServer();
        Customer customer = (Customer)ClientController.getInstance().getCurrentUser();
        allDiscountCodes = customer.getAllDiscountCodes();
        if (!allDiscountCodes.isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(allDiscountCodes.size()%4 == 0 ?
                    allDiscountCodes.size()/4 : (allDiscountCodes.size()/4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                allGridPanes.add(null);
            }
            pagination = new Pagination(pages, 0);
            pagination.setTranslateX(30);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Manage Discount Codes");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        }else{
            HBox hBox = new HBox();
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No DiscountCode!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }




    private GridPane createPage(Integer param) {
        if(param.equals(pages-1)){
            setTheCenterInfo(allDiscountCodes.size() - (param * 4), param);
        }else{
            setTheCenterInfo(4, param);
        }
        return allGridPanes.get(param);
    }


    private void setTheBeginning() {
        borderPane = new BorderPane();
        for (int i = 0; i < allDiscountCodes.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 18));
            button.setStyle("-fx-background-color: #808080");
            button.setTranslateX(40);
            int finalI = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = allDiscountCodes.get(finalI).view().split("\n");
                    Label[] label = new Label[details.length];
                    Label label1 = new Label("Details :\n\n");
                    label1.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 30));
                    vBox.getChildren().add(label1);
                    for (int j = 0; j < label.length; j++) {
                        label[j] = new Label(details[j]);
                        label[j].setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
                        vBox.getChildren().add(label[j]);
                    }
                    Scene scene = new Scene(vBox, 300, 500);
                    Stage stage = new Stage();
                    stage.setWidth(400);
                    stage.setHeight(600);
                    stage.setScene(scene);
                    stage.setTitle("Details");
                    stage.show();
                }
            });
            viewDetails.put(allDiscountCodes.get(i).getDiscountCodeID(), button);
        }
    }

    private void setTheCenterInfo(int counter, Integer pages) {
        gridPane = new GridPane();
        Label id = new Label("ID");
        Label discountPercent = new Label("Percent  ");
        Label maxDiscountPercent = new Label("MaxAmount");
        Label endDate = new Label("End Date");
        id.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        discountPercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        maxDiscountPercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        endDate.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        for (int i = 0; i < 5; i++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(120,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        }
        setDiscountCodeCounter(4*pages);
        for (int i = 1; i <= counter; discountCodeCounter++, i++) {
            setTheRows(i, allDiscountCodes.get(discountCodeCounter).getDiscountCodeID(),
                    String.valueOf(allDiscountCodes.get(discountCodeCounter).getDiscountPercent()),
                    String.valueOf(allDiscountCodes.get(discountCodeCounter).getMaxDiscountAmount()),
                    String.valueOf(allDiscountCodes.get(discountCodeCounter).getEndTime()));
        }
        gridPane.setTranslateX(10);
        gridPane.setTranslateY(1);
        gridPane.add(id, 0, 0);
        gridPane.add(discountPercent, 1, 0);
        gridPane.add(maxDiscountPercent, 2, 0);
        gridPane.add(endDate, 3, 0);
        allGridPanes.add(pages, gridPane);
    }



    private void setTheRows(int row, String id, String discountPercent, String maxDiscountAmount, String endTime) {
        Pane[] allPanes = new Pane[4];
        for (int i = 0; i < 4; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(id, discountPercent, maxDiscountAmount, endTime);
        for (int i = 0; i < 4; i++) {
            if(i == 0){
                allLabels[i].setTranslateX(15);
            }
            if(i == 1){
                allLabels[i].setTranslateX(40);
            }
            if(i == 2){
                allLabels[i].setTranslateX(25);
            }
            if(i == 3){
                allLabels[i].setTranslateX(1);
            }
            allPanes[i].getChildren().add(allLabels[i]);
            allLabels[i].setTranslateY(1);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        Pane pane2 = new Pane();
        pane2.setStyle("-fx-background-color: #e6e6e6");
        pane2.getChildren().add(viewDetails.get(id));
        gridPane.add(pane2, 4, row);
    }

    private Label[] labelMaker(String id, String discountPercent, String maxDiscountAmount, String endTime) {
        Label[] allLabels = new Label[4];
        allLabels[0] = new Label(id);
        allLabels[1] = new Label(discountPercent);
        allLabels[2] = new Label(maxDiscountAmount);
        allLabels[3] = new Label(endTime);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }


}

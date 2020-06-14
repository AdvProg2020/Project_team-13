package View;
import Controller.Client.DiscountController;
import Models.DiscountCode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.*;

public class ManageDiscountCodesMenu extends Menu{
    private Map<String, ImageView> edits;
    private Map<String, ImageView> deletes;
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int discountCodeCounter;
    private List<GridPane> allGridPanes;

    public ManageDiscountCodesMenu(Stage stage) {
        super(stage);
        super.setScene();
        edits = new HashMap<>();
        deletes = new HashMap<>();
        viewDetails = new HashMap<>();
        this.setScene();
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public void setDiscountCodeCounter(int discountCodeCounter) {
        this.discountCodeCounter = discountCodeCounter;
    }

    @Override
    public void setScene() {
        DiscountController.getInstance().getAllDiscountCodesFromServer();
        if (!DiscountController.getInstance().getAllDiscountCodes().isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(DiscountController.getInstance().getAllDiscountCodes().size()%4 == 0 ?
                    DiscountController.getInstance().getAllDiscountCodes().size()/4 : (DiscountController.getInstance().getAllDiscountCodes().size()/4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                allGridPanes.add(null);
            }
            Pagination pagination = new Pagination(pages, 0);
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
            setTheCenterInfo(DiscountController.getInstance().getAllDiscountCodes().size() - (param * 4), param);
        }else{
            setTheCenterInfo(4, param);
        }
        return getAllGridPanes().get(param);
    }

    public List<GridPane> getAllGridPanes() {
        return allGridPanes;
    }

    private void setTheBeginning() {
        borderPane = new BorderPane();
        ArrayList<DiscountCode> allDiscountCodes = DiscountController.getInstance().getAllDiscountCodes();
        for (int i = 0; i < allDiscountCodes.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 18));
            button.setTranslateX(0);
            int finalI = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = DiscountController.getInstance().viewDiscountCode(allDiscountCodes.get(finalI).getDiscountCodeID()).split("\n");
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
        for (int i = 0; i < allDiscountCodes.size(); i++) {
            ImageView imageView = new ImageView(new Image("file:src/edit.png"));
            imageView.setFitHeight(45);
            imageView.setFitWidth(45);
            int finalI = i;
            imageView.setOnMouseClicked(event -> {
                DiscountController.getInstance().editDiscountCode(allDiscountCodes.get(finalI));
                //
            });
            imageView.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            imageView.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            edits.put(allDiscountCodes.get(i).getDiscountCodeID(), imageView);
        }
        for (int i = 0; i < allDiscountCodes.size(); i++) {
            ImageView imageView = new ImageView(new Image("file:src/trash1.png"));
            imageView.setFitHeight(45);
            imageView.setFitWidth(45);
            imageView.setTranslateX(15);
            int finalI = i;
            imageView.setOnMouseClicked(event -> {
                DiscountController.getInstance().deleteDiscountCode(allDiscountCodes.get(finalI).getDiscountCodeID());
                //
            });
            imageView.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            imageView.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            deletes.put(allDiscountCodes.get(i).getDiscountCodeID(), imageView);
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
        for (int i = 0; i < 7; i++) {
            if (i==3) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(200, Control.USE_COMPUTED_SIZE,
                        Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }else if (i>3){
                if(i==6){
                    gridPane.getColumnConstraints().add(new ColumnConstraints(10, Control.USE_COMPUTED_SIZE,
                            Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
                }else {
                    gridPane.getColumnConstraints().add(new ColumnConstraints(70, Control.USE_COMPUTED_SIZE,
                            Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
                }
            }else{
                gridPane.getColumnConstraints().add(new ColumnConstraints(100,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
        }
        setDiscountCodeCounter(4*pages);
        for (int i = 1; i <= counter; discountCodeCounter++, i++) {
            setTheRows(i, DiscountController.getInstance().getAllDiscountCodes().get(discountCodeCounter).getDiscountCodeID(),
                    String.valueOf(DiscountController.getInstance().getAllDiscountCodes().get(discountCodeCounter).getDiscountPercent()),
                    String.valueOf(DiscountController.getInstance().getAllDiscountCodes().get(discountCodeCounter).getMaxDiscountAmount()),
                    String.valueOf(DiscountController.getInstance().getAllDiscountCodes().get(discountCodeCounter).getEndTime()));
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
                allLabels[i].setTranslateX(10);
            }
            if(i == 3){
                allLabels[i].setTranslateX(1);
            }
            allPanes[i].getChildren().add(allLabels[i]);
            allLabels[i].setTranslateY(1);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        Pane pane = new Pane();
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        pane.setStyle("-fx-background-color: #e6e6e6");
        pane1.setStyle("-fx-background-color: #e6e6e6");
        pane2.setStyle("-fx-background-color: #e6e6e6");
        pane.getChildren().add(deletes.get(id));
        pane1.getChildren().add(edits.get(id));
        pane2.getChildren().add(viewDetails.get(id));
        gridPane.add(pane, 4, row);
        gridPane.add(pane1, 5, row);
        gridPane.add(pane2, 6, row);
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

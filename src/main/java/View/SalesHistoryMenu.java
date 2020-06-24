package View;
import Controller.Client.ClientController;
import Models.Log;
import Models.UserAccount.Seller;
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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesHistoryMenu extends Menu {
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int page;
    private int logCounter;
    private List<GridPane> allGridPanes;
    private Seller seller;
    private Pagination pagination;

    public SalesHistoryMenu(Stage stage, int pages) {
        super(stage);
        super.setScene();
        viewDetails = new HashMap<>();
        this.setScene(pages);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setPages(int pages) {
        this.page = pages;
    }


    public void setLogCounter(int logCounter) {
        this.logCounter = logCounter;
    }


    public void setScene(int pages) {
        setSeller((Seller)ClientController.getInstance().getCurrentUser());
        if (!seller.getHistoryOfTransaction().isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            setPages(seller.getHistoryOfTransaction().size()%4 == 0 ?
                    seller.getHistoryOfTransaction().size()/4 : (seller.getHistoryOfTransaction().size()/4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < page; i++) {
                allGridPanes.add(null);
            }
            pagination = new Pagination(page, pages);
            pagination.setTranslateX(50);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Sales History");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            hBox.setTranslateX(60);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        } else {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Sell History!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }

    private GridPane createPage(Integer param) {
        if (param.equals(page - 1)) {
            setTheCenterInfo(seller.getHistoryOfTransaction().size() - (param * 4), param);
        } else {
            setTheCenterInfo(4, param);
        }
        return getAllGridPanes().get(param);
    }

    public List<GridPane> getAllGridPanes() {
        return allGridPanes;
    }

    private void setTheBeginning() {
        borderPane = new BorderPane();
        ArrayList<Log> allSellLog = seller.getHistoryOfTransaction();
        for (int i = 0; i < allSellLog.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
            button.setTranslateX(40);
            int finalI = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = seller.findOrderWithId(allSellLog.get(finalI).getId()).viewOrders().split("\n");
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
            viewDetails.put(allSellLog.get(i).getId(), button);
        }
    }

    private void setTheCenterInfo(int counter, Integer pages) {
        gridPane = new GridPane();
        Label id = new Label("ID");
        Label price = new Label("Price");
        Label date = new Label("Date");
        Label status = new Label("Status");
        id.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        price.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        date.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        status.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        for (int i = 0; i < 6; i++) {
            if(i==2){
                gridPane.getColumnConstraints().add(new ColumnConstraints(300,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }else if(i==3){
                gridPane.getColumnConstraints().add(new ColumnConstraints(180,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }else if(i==5) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(100,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }else{
                gridPane.getColumnConstraints().add(new ColumnConstraints(110,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
        }
        setLogCounter(4 * pages);
        ArrayList<Log> allSellLog = seller.getHistoryOfTransaction();
        for (int i = 1; i <= counter; logCounter++, i++) {
            setTheRows(i, allSellLog.get(logCounter).getId(), String.valueOf(allSellLog.get(logCounter).getPrice()),
                    String.valueOf(allSellLog.get(logCounter).getDate()), String.valueOf(allSellLog.get(logCounter).getReceivingStatus()));
        }
        gridPane.setTranslateX(20);
        gridPane.setTranslateY(1);
        gridPane.add(id, 0, 0);
        gridPane.add(price, 1, 0);
        gridPane.add(date, 2, 0);
        gridPane.add(status, 3, 0);
        allGridPanes.add(pages, gridPane);
    }


    private void setTheRows(int row, String id, String price, String date, String status) {
        Pane[] allPanes = new Pane[4];
        for (int i = 0; i < 4; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(id, price, date, status);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                allLabels[i].setTranslateX(10);
            }
            if (i == 1) {
                allLabels[i].setTranslateX(30);
            }
            if (i == 2) {
                allLabels[i].setTranslateX(15);
            }
            if (i == 3) {
                allLabels[i].setTranslateX(-3);
            }
            allLabels[i].setAlignment(Pos.CENTER);
            allPanes[i].getChildren().add(allLabels[i]);
            allLabels[i].setTranslateY(8);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        Pane pane = new Pane();
        pane.getChildren().add(viewDetails.get(id));
        pane.setStyle("-fx-background-color: #e6e6e6");
        gridPane.add(pane, 4, row);
    }

    private Label[] labelMaker(String id, String price, String date, String status) {
        Label[] allLabels = new Label[4];
        allLabels[0] = new Label(id);
        allLabels[1] = new Label(price);
        allLabels[2] = new Label(date);
        allLabels[3] = new Label(status);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }
}
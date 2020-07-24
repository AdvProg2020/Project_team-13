package View;

import Controller.Client.ClientController;
import Controller.Client.UserController;
import Models.Log;
import Models.ReceivingStatus;
import Models.UserAccount.Customer;
import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static View.MangeOffsMenu.getLabels;

public class ManageOrders extends Menu {
    private Map<String, ArrayList<Button>> viewDetails = new HashMap<>();
    private BorderPane borderPane = new BorderPane();
    private GridPane gridPane = new GridPane();
    private int page;
    private int logCounter;
    private List<GridPane> allGridPanes;
    private ArrayList<Log> orders = new ArrayList<>();

    public ManageOrders(Stage stage, int pages) {
        super(stage);
        super.setScene();
        viewDetails = new HashMap<>();
        this.setScene(pages);
    }


    public void setPages(int pages) {
        this.page = pages;
    }


    public void setLogCounter(int logCounter) {
        this.logCounter = logCounter;
    }


    public void setScene(int pages) {
        viewDetails.clear();
        borderPane=new BorderPane();
        gridPane.getChildren().clear();
        ClientController.getInstance().getAllOrdersFromServer();
        if (!UserController.getInstance().getOrders().isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            setPages(UserController.getInstance().getOrders().size() % 4 == 0 ?
                    UserController.getInstance().getOrders().size() / 4 : (UserController.getInstance().getOrders().size() / 4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < page; i++) {
                allGridPanes.add(null);
            }
            Pagination pagination = new Pagination(page, pages);
            pagination.setTranslateX(50);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Orders Menu");
            ManageRequests.setTranslateX(150);
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            hBox.setTranslateX(50);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        } else {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Orders!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }

    private GridPane createPage(Integer param) {
        if (param.equals(page - 1)) {
            setTheCenterInfo(UserController.getInstance().getOrders().size() - (param * 4), param);
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
        ArrayList<Log> allBuyLog = UserController.getInstance().getOrders();
        for (int i = 0; i < allBuyLog.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
            button.setTranslateX(40);
            Button button1 = new Button("Send");
            button1.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
            button1.setTranslateX(40);
            if (allBuyLog.get(i).getReceivingStatus().equals(ReceivingStatus.Received)) {
                button1.setText("Received");
                button1.setDisable(true);
                button1.setMaxHeight(20);
            } else {
                button1.setText("Receive");                button1.setMaxHeight(20);

                int finalI1 = i;
                button1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        allBuyLog.get(finalI1).setReceivingStatus(ReceivingStatus.Received);
                        ClientController.getInstance().sendMessageToServer("@editLog@" + new Gson().toJson(allBuyLog.get(finalI1)));
                    }
                });
            }
            int finalI = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = allBuyLog.get(finalI).viewOrders().split("\n");
                    Label[] label = new Label[details.length];
                    Label label1 = new Label("Details :\n\n");
                    label1.setMaxHeight(20);
                    label1.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 10));
                    vBox.getChildren().add(label1);
                    for (int j = 0; j < label.length; j++) {
                        label[j] = new Label(details[j]);
                        label[j].setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 10));
                        vBox.getChildren().add(label[j]);
                    }
                    setTheSettings(vBox);
                }
            });
            ArrayList<Button> buttons = new ArrayList<>();
            button.setMaxHeight(10);
            button1.setMaxHeight(10);
            buttons.add(button);
            buttons.add(button1);
            viewDetails.put(allBuyLog.get(i).getId(),buttons );
        }
    }

    static void setTheSettings(VBox vBox) {
        Scene scene = new Scene(vBox, 300, 500);
        Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.setTitle("Details");
        stage.show();
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
        date.setTranslateX(0);
        gridPane.setVgap(30);
        for (int i = 0; i < 6; i++) {
            if (i == 2) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(300,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else if (i == 3) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(180,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else if (i == 5) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(100,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else {
                gridPane.getColumnConstraints().add(new ColumnConstraints(110,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
        }
        setLogCounter(4 * pages);
        ArrayList<Log> allBuyLog = UserController.getInstance().getOrders();
        for (int i = 1; i <= counter; logCounter++, i++) {
            setTheRows(i, allBuyLog.get(logCounter).getId(), String.valueOf(allBuyLog.get(logCounter).getPrice()),
                    String.valueOf(allBuyLog.get(logCounter).getDate()), String.valueOf(allBuyLog.get(logCounter).getReceivingStatus()));
        }
        gridPane.setTranslateX(20);
        MangeOffsMenu.processSeTheBeginning(pages, id, price, date, status, gridPane, allGridPanes);
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
                allLabels[i].setTranslateX(20);
            }
            if (i == 3) {
                allLabels[i].setTranslateX(-5);
            }
            allLabels[i].setAlignment(Pos.CENTER);
            allPanes[i].getChildren().add(allLabels[i]);
            allLabels[i].setTranslateY(8);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        HBox vBox = new HBox();
        vBox.setStyle("-fx-background-color: #e6e6e6");
        vBox.getChildren().add(viewDetails.get(id).get(0));
        vBox.getChildren().add(viewDetails.get(id).get(1));
        gridPane.add(vBox, 4, row);
    }

    private Label[] labelMaker(String id, String price, String date, String status) {
        return getLabels(id, price, date, status);
    }
}

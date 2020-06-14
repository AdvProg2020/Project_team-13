package View;
import Controller.Client.RequestController;
import Models.Request;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersMenu extends Menu {
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int logCounter;
    private List<GridPane> allGridPanes;

    public OrdersMenu(Stage stage) {
        super(stage);
        super.setScene();
        viewDetails = new HashMap<>();
        this.setScene();
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public void setLogCounter(int logCounter) {
        this.logCounter = logCounter;
    }

    @Override
    public void setScene() {
        RequestController.getInstance().getAllRequestsFromServer();
        if (RequestController.getInstance().getAllRequests().size() != 0) {
            allGridPanes = new ArrayList<>();
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
//            this.setPages();
            Pagination pagination = new Pagination(pages, 0);
            pagination.setTranslateX(50);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Buys History");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        } else {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Buy History!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }

    private GridPane createPage(Integer param) {
        if (param.equals(pages - 1)) {
            setTheCenterInfo(RequestController.getInstance().getAllRequests().size() - (param * 4), param);
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
        ArrayList<Request> allRequests = RequestController.getInstance().getAllRequests();
        for (int i = 0; i < RequestController.getInstance().getAllRequests().size(); i++) {
            Button button = new Button("View Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = RequestController.getInstance().viewRequestDetail(getTheIdForDetail(viewDetails, button)).split("\n");
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
            viewDetails.put(allRequests.get(i).getRequestId(), button);
        }
    }

    private String getString(ImageView acceptImage, Map<String, ImageView> accepts) {
        String requestIdForThis = null;
        for (String requestId : accepts.keySet()) {
            if (accepts.get(requestId).equals(acceptImage)) {
                requestIdForThis = requestId;
                break;
            }
        }
        return requestIdForThis;
    }


    private String getTheIdForDetail(Map<String, Button> viewDetails, Button button) {
        String requestIdForThis = null;
        for (String requestId : viewDetails.keySet()) {
            if (viewDetails.get(requestId).equals(button)) {
                requestIdForThis = requestId;
                break;
            }
        }
        return requestIdForThis;
    }

    private void setTheCenterInfo(int counter, Integer pages) {
        gridPane = new GridPane();
        Label id = new Label("ID");
        Label type = new Label("Type");
        id.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        type.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        for (int i = 0; i < 5; i++) {
            if (i == 1) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
            gridPane.getColumnConstraints().add(new ColumnConstraints(120,
                    Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        }
        setLogCounter(4 * pages);
        for (int i = 1; i <= counter; logCounter++, i++) {
            setTheRows(i, RequestController.getInstance().getAllRequests().get(logCounter).getRequestId(), String.valueOf(RequestController.getInstance().getAllRequests().get(logCounter).getType()));
        }
        gridPane.setTranslateX(20);
        gridPane.setTranslateY(1);
        gridPane.add(id, 0, 0);
        gridPane.add(type, 1, 0);
        allGridPanes.add(pages, gridPane);
    }


    private void setTheRows(int row, String ids, String types) {
        Pane[] allPanes = new Pane[2];
        for (int i = 0; i < 2; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(ids, types);
        for (int i = 0; i < 2; i++) {
            allLabels[i].setAlignment(Pos.CENTER);
            allPanes[i].getChildren().add(allLabels[i]);
            if (i == 0) {
                allLabels[i].setTranslateX(15);
            }
            if (i == 1) {
                allLabels[i].setTranslateX(18);
            }
            allLabels[i].setTranslateY(8);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        Pane pane = new Pane();
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        pane3.setStyle("-fx-background-color: #e6e6e6");
        pane.setStyle("-fx-background-color: #e6e6e6");
        pane1.setStyle("-fx-background-color: #e6e6e6");
        pane2.setStyle("-fx-background-color: #e6e6e6");
//        pane.getChildren().add(accepts.get(ids));
//        pane1.getChildren().add(declines.get(ids));
        pane2.getChildren().add(viewDetails.get(ids));
        gridPane.add(pane3, 2, row);
        gridPane.add(pane, 3, row);
        gridPane.add(pane1, 4, row);
        gridPane.add(pane2, 5, row);
    }

    private Label[] labelMaker(String id, String type) {
        Label[] allLabels = new Label[2];
        allLabels[0] = new Label(id);
        allLabels[1] = new Label(type);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }
}
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

public class ManageRequestMenu extends Menu{
    private Map<String, ImageView> accepts;
    private Map<String, ImageView> declines;
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int page;
    private int requestCounter;
    private List<GridPane> allGridPanes;
    private Pagination pagination;

    public ManageRequestMenu(Stage stage, int pages) {
        super(stage);
        super.setScene();
        accepts = new HashMap<>();
        declines = new HashMap<>();
        viewDetails = new HashMap<>();
        this.setScene(pages);
    }

    public void setPages(int pages) {
        this.page = pages;
    }


    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }

    public void setScene(int pages) {
        RequestController.getInstance().getAllRequestsFromServer();
        if (RequestController.getInstance().getAllRequests().size() != 0) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(RequestController.getInstance().getAllRequests().size()%4 == 0 ?
                    RequestController.getInstance().getAllRequests().size()/4 : (RequestController.getInstance().getAllRequests().size()/4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                allGridPanes.add(null);
            }
            pagination = new Pagination(page, pages);
            pagination.setTranslateX(50);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Manager Requests");
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
            Label noRequest = new Label("There is No Request!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }

    private GridPane createPage(Integer param) {
        if(param.equals(page-1)){
            setTheCenterInfo(RequestController.getInstance().getAllRequests().size() - (param * 4), param);
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
        ArrayList<Request> allRequests = RequestController.getInstance().getAllRequests();
        for (int i = 0; i < RequestController.getInstance().getAllRequests().size(); i++) {
            Button button = new Button("View Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 19));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
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
        for (int i = 0; i < RequestController.getInstance().getAllRequests().size(); i++) {
            ImageView acceptImage= new ImageView(new Image("file:src/like1.png"));
            acceptImage.setFitHeight(50);
            acceptImage.setFitWidth(50);
            acceptImage.setOnMouseClicked(event -> {
                String requestIdForThis = getString(acceptImage, accepts);
                RequestController.getInstance().acceptRequest(requestIdForThis);
                new ManageRequestMenu(this.getStage(), this.pagination.getCurrentPageIndex()).execute();
            });
            acceptImage.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            acceptImage.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            accepts.put(allRequests.get(i).getRequestId(), acceptImage);
        }
        for (int i = 0; i < RequestController.getInstance().getAllRequests().size(); i++) {
            ImageView declineImage= new ImageView(new Image("file:src/dislike1.png"));
            declineImage.setFitWidth(50);
            declineImage.setFitHeight(50);
            declines.put(allRequests.get(i).getRequestId(), declineImage);
            declineImage.setOnMouseClicked(event -> {
                String requestIdForThis = getString(declineImage, declines);
                RequestController.getInstance().declineRequest(requestIdForThis);
                new ManageRequestMenu(this.getStage(), this.pagination.getCurrentPageIndex()).execute();
            });
            declineImage.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            declineImage.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
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


    private String getTheIdForDetail(Map<String, Button> viewDetails, Button button){
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
            if (i==1){
                gridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
            gridPane.getColumnConstraints().add(new ColumnConstraints(120,
                    Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        }
        setRequestCounter(4*pages);
        for (int i = 1; i <= counter; requestCounter++, i++) {
            setTheRows(i, RequestController.getInstance().getAllRequests().get(requestCounter).getRequestId(), String.valueOf(RequestController.getInstance().getAllRequests().get(requestCounter).getType()));
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
            if(i==0){
                allLabels[i].setTranslateX(20);
            }
            if(i==1){
                allLabels[i].setTranslateX(30);
            }
            allPanes[i].getChildren().add(allLabels[i]);
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
        pane.getChildren().add(accepts.get(ids));
        pane1.getChildren().add(declines.get(ids));
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

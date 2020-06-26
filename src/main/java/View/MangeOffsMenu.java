package View;

import Controller.Client.ClientController;
import Models.Offer;
import Models.UserAccount.Seller;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MangeOffsMenu extends Menu implements EventHandler<ActionEvent> {
    private Map<String, ImageView> edits;
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int offerCounter;
    private List<GridPane> allGridPanes;
    private Pagination pagination;
    private List<Offer> allOffers;


    public MangeOffsMenu(Stage stage, int pages) {
        super(stage);
        super.setScene();
        edits = new HashMap<>();
        viewDetails = new HashMap<>();
        this.setScene(pages);
    }


    public void setOfferCounter(int offerCounter) {
        this.offerCounter = offerCounter;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private void setScene(int pageNumber) {
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        allOffers = seller.getAllOffer();
        Button addOffer = new Button("Add Offer");
        addOffer.setStyle("-fx-background-color: #808080");
        addOffer.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 15));
        addOffer.setMaxHeight(20);
        addOffer.setOnAction(this);
        if (allOffers != null && !allOffers.isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(allOffers.size() % 4 == 0 ?
                    allOffers.size() / 4 : (allOffers.size() / 4) + 1);
            allGridPanes = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                allGridPanes.add(null);
            }
            pagination = new Pagination(pages, pageNumber);
            pagination.setTranslateX(30);
            pagination.setTranslateY(50);
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            pagination.setPageFactory(this::createPage);
            setTheBeginning();
            Label ManageRequests = new Label("Manage Offers");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            hBox.getChildren().add(addOffer);
            addOffer.setTranslateX(45);
            addOffer.setTranslateY(18);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        } else {
            HBox hBox = new HBox();
            hBox.getChildren().add(addOffer);
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Offer!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setTop(addOffer);
            addOffer.setTranslateY(250);
            addOffer.setTranslateX(375);
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }

    private GridPane createPage(Integer integer) {
        if (integer.equals(pages - 1)) {
            setTheCenterInfo(allOffers.size() - (integer * 4), integer);
        } else {
            setTheCenterInfo(4, integer);
        }
        return getAllGridPanes().get(integer);
    }

    private List<GridPane> getAllGridPanes() {
        return this.allGridPanes;
    }

    private void setTheCenterInfo(int counter, Integer integer) {
        gridPane = new GridPane();
        Label name = new Label("ID");
        Label percent = new Label("Percent");
        Label startDate = new Label("Start Date");
        Label endDate = new Label("End Date");
        name.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        percent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        startDate.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        endDate.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        for (int i = 0; i < 6; i++) {
            if (i == 2 || i == 3) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(250,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else if (i == 4 || i == 5) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(110,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else if (i == 1) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(100,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            } else {
                gridPane.getColumnConstraints().add(new ColumnConstraints(100,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
            }
        }
        setOfferCounter(4 * integer);
        for (int i = 1; i <= counter; offerCounter++, i++) {
            setTheRows(i, allOffers.get(offerCounter).getOfferId(), String.valueOf(allOffers.get(offerCounter).getAmount()),
                    String.valueOf(allOffers.get(offerCounter)
                            .getStartTime()), String.valueOf(allOffers.get(offerCounter).getEndTime()));
        }
        gridPane.setTranslateX(10);
        gridPane.setTranslateY(1);
        gridPane.add(name, 0, 0);
        gridPane.add(percent, 1, 0);
        gridPane.add(startDate, 2, 0);
        gridPane.add(endDate, 3, 0);
        allGridPanes.set(integer, gridPane);
    }

    private void setTheBeginning() {
        borderPane = new BorderPane();
        for (int i = 0; i < allOffers.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 18));
            button.setTranslateX(38);
            int finalI1 = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = allOffers.get(finalI1).toString().split("\n");
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
            viewDetails.put(allOffers.get(i).getOfferId(), button);
        }
        for (int i = 0; i < allOffers.size(); i++) {
            ImageView imageView = new ImageView(new Image("file:src/edit.png"));
            imageView.setFitHeight(45);
            imageView.setFitWidth(45);
            imageView.setTranslateX(60);
            imageView.setOnMouseClicked(event -> {
                new EditOffsMenu(this.stage).execute();
            });
            imageView.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            imageView.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            edits.put(allOffers.get(i).getOfferId(), imageView);
        }
    }


    private void setTheRows(int row, String offerId, String amount, String startTime, String endTime) {
        Pane[] allPanes = new Pane[4];
        for (int i = 0; i < 4; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(offerId, amount, startTime, endTime);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                allLabels[i].setTranslateX(15);
            }
            if (i == 1) {
                allLabels[i].setTranslateX(20);
            }
            if (i == 2) {
                allLabels[i].setTranslateX(0);
            }
            if (i == 3) {
                allLabels[i].setTranslateX(0);
            }
            allPanes[i].getChildren().add(allLabels[i]);
            allLabels[i].setTranslateY(1);
            GridPane.setHalignment(allPanes[i], HPos.CENTER);
            gridPane.add(allPanes[i], i, row);
        }
        Pane pane = new Pane();
        Pane pane1 = new Pane();
        pane.setStyle("-fx-background-color: #e6e6e6");
        pane1.setStyle("-fx-background-color: #e6e6e6");
        pane.getChildren().add(edits.get(offerId));
        pane1.getChildren().add(viewDetails.get(offerId));
        gridPane.add(pane, 4, row);
        gridPane.add(pane1, 5, row);

    }

    private Label[] labelMaker(String offerId, String offerPercent, String startDate, String endDate) {
        Label[] allLabels = new Label[4];
        allLabels[0] = new Label(offerId);
        allLabels[1] = new Label(offerPercent);
        allLabels[2] = new Label(startDate);
        allLabels[3] = new Label(endDate);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }

    @Override
    public void handle(ActionEvent event) {
        new CreateOffsMenu(this.stage).execute();
    }
}

package View;

import Controller.Client.AuctionController;
import Models.Auction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class AuctionsMenu extends Menu {
    private Map<String, ImageView> edits;
    private Map<String, ImageView> deletes;
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int discountCodeCounter;
    private List<GridPane> allGridPanes;
    private Pagination pagination;

    public AuctionsMenu(Stage stage, int pages) {
        super(stage);
        super.setScene();
        edits = new HashMap<>();
        deletes = new HashMap<>();
        viewDetails = new HashMap<>();
        this.setScene(pages);
    }


    public void setPages(int pages) {
        this.pages = pages;
    }


    public void setDiscountCodeCounter(int discountCodeCounter) {
        this.discountCodeCounter = discountCodeCounter;
    }


    public void setScene(int pageNumber) {
        AuctionController.getInstance().updateAllAuctions();
        if (!AuctionController.getInstance().getAllAuctions().isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(AuctionController.getInstance().getAllAuctions().size() % 4 == 0 ?
                    AuctionController.getInstance().getAllAuctions().size() / 4 : (AuctionController.getInstance().getAllAuctions().size() / 4) + 1);
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
            Label ManageRequests = new Label("Auctions Menu");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        } else {
            HBox hBox = new HBox();
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Active Auction!!");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }


    private GridPane createPage(Integer param) {
        if (param.equals(pages - 1)) {
            setTheCenterInfo(AuctionController.getInstance().getAllAuctions().size() - (param * 4), param);
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
        AuctionController.getInstance().updateAllAuctions();
        ArrayList<Auction> allAuctions = AuctionController.getInstance().getAllAuctions();
        for (int i = 0; i < allAuctions.size(); i++) {
            Button button = new Button("Enter");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 18));
            button.setTranslateX(0);
            int finalI = i;
            if(new Date().before(allAuctions.get(finalI).getStartTime())) {
                button.setDisable(true);
                button.setText("Not active");
            }
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    AuctionController.getInstance().setCurrentAuction(allAuctions.get(finalI));
                    new AuctionPage(stage).execute();
                }
            });
            viewDetails.put(allAuctions.get(i).getAuctionId(), button);
        }
    }

    private void setTheCenterInfo(int counter, Integer pages) {
        gridPane = new GridPane();
        Label id = new Label("ID");
        Label discountPercent = new Label("ProductId");
        Label maxDiscountPercent = new Label("LastPrice  ");
        Label endDate = new Label("End Date");
        id.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        discountPercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        maxDiscountPercent.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        endDate.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        gridPane.getColumnConstraints().add(new ColumnConstraints(110, Control.USE_COMPUTED_SIZE,
                110, Priority.NEVER, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE,
                150, Priority.NEVER, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(110, Control.USE_COMPUTED_SIZE,
                110, Priority.NEVER, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(70, Control.USE_COMPUTED_SIZE,
                Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(70, Control.USE_COMPUTED_SIZE,
                Double.POSITIVE_INFINITY, Priority.NEVER, HPos.LEFT, true));
        setDiscountCodeCounter(4 * pages);
        for (int i = 1; i <= counter; discountCodeCounter++, i++) {
            setTheRows(i, AuctionController.getInstance().getAllAuctions().get(discountCodeCounter).getAuctionId(),
                    String.valueOf(AuctionController.getInstance().getAllAuctions().get(discountCodeCounter).getProduct().getProductId()),
                    String.valueOf(AuctionController.getInstance().getAllAuctions().get(discountCodeCounter).getBestOffer()),
                    String.valueOf(AuctionController.getInstance().getAllAuctions().get(discountCodeCounter).getEndTime()));
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
            if (i == 0) {
                allLabels[i].setTranslateX(15);
            }
            if (i == 1) {
                allLabels[i].setTranslateX(40);
            }
            if (i == 2) {
                allLabels[i].setTranslateX(10);
            }
            if (i == 3) {
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

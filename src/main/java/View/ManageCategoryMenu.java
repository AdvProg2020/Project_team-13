package View;
import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Models.Product.Category;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManageCategoryMenu extends Menu implements EventHandler<ActionEvent>{
    private Map<String, ImageView> edits;
    private Map<String, ImageView> deletes;
    private Map<String, Button> viewDetails;
    private BorderPane borderPane;
    private GridPane gridPane;
    private int pages;
    private int categoryCounter;
    private List<GridPane> allGridPanes;
    private Pagination pagination;
    private Button addCategory;
    private ArrayList<Category> allCategories;

    public ManageCategoryMenu(Stage stage, int pages) {
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


    public void setCategoryCounter(int categoryCounter) {
        this.categoryCounter = categoryCounter;
    }



    public void setScene(int pageNumber) {
        CategoryController.getInstance().updateAllCategories();
        allCategories = CategoryController.getInstance().getAllCategories();
        addCategory = new Button("Add Category");
        addCategory.setStyle("-fx-background-color: #808080");
        addCategory.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 15));
        addCategory.setMaxHeight(20);
        addCategory.setOnAction(this);
        if (!allCategories.isEmpty()) {
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            this.setPages(allCategories.size()%4 == 0 ?
                    allCategories.size()/4 : (allCategories.size()/4) + 1);
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
            Label ManageRequests = new Label("Manage Categories");
            ManageRequests.setTranslateX(20);
            ManageRequests.setTranslateY(20);
            ManageRequests.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.getChildren().add(ManageRequests);
            hBox.getChildren().add(addCategory);
            addCategory.setTranslateX(45);
            addCategory.setTranslateY(18);
            borderPane.setTop(hBox);
            borderPane.setCenter(pagination);
        }else{
            HBox hBox = new HBox();
            hBox.getChildren().add(addCategory);
            pageGridPane.getChildren().remove(centerGridPane);
            pageGridPane.getChildren().remove(bottomGridPane);
            borderPane = new BorderPane();
            Label noRequest = new Label("There is No Category");
            noRequest.setTranslateY(150);
            noRequest.setFont(Font.loadFont("file:src/Bangers.ttf", 50));
            borderPane.setTop(addCategory);
            addCategory.setTranslateY(250);
            addCategory.setTranslateX(350);
            borderPane.setCenter(noRequest);
        }
        pageGridPane.add(borderPane, 0, 2);
    }




    private GridPane createPage(Integer param) {
        if(param.equals(pages-1)){
            setTheCenterInfo(allCategories.size() - (param * 4), param);
        }else{
            setTheCenterInfo(4, param);
        }
        return allGridPanes.get(param);
    }


    private void setTheBeginning() {
        borderPane = new BorderPane();
        for (int i = 0; i < allCategories.size(); i++) {
            Button button = new Button("Details");
            button.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 18));
            button.setTranslateX(38);
            int finalI1 = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-background-color: #afafaf");
                    String[] details = allCategories.get(finalI1).toString().split("\n");
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
            viewDetails.put(allCategories.get(i).getName(), button);
        }
        for (int i = 0; i < allCategories.size(); i++) {
            ImageView imageView = new ImageView(new Image("file:src/edit.png"));
            imageView.setFitHeight(45);
            imageView.setFitWidth(45);
            imageView.setTranslateX(60);
            int finalI1 = i;
            imageView.setOnMouseClicked(event -> {
                CategoryController.getInstance().setCurrentCategory(allCategories.get(finalI1));
                new EditCategoryMenu(this.stage).execute();
            });
            imageView.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            imageView.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            edits.put(allCategories.get(i).getName(), imageView);
        }
        for (int i = 0; i < allCategories.size(); i++) {
            ImageView imageView = new ImageView(new Image("file:src/trash1.png"));
            imageView.setFitHeight(45);
            imageView.setFitWidth(45);
            imageView.setTranslateX(80);
            int finalI1 = i;
            imageView.setOnMouseClicked(event -> {
                CategoryController.getInstance().removeCategory(allCategories.get(finalI1).getName());
                ClientController.getInstance().getMenus().remove(this);
                new ManageCategoryMenu(this.getStage(), this.pagination.getCurrentPageIndex()).execute();
            });
            imageView.setOnMouseEntered((EventHandler<Event>) event -> scene.setCursor(Cursor.HAND));
            imageView.setOnMouseExited((EventHandler<Event>) event -> scene.setCursor(Cursor.DEFAULT));
            deletes.put(allCategories.get(i).getName(), imageView);
        }
    }

    private void setTheCenterInfo(int counter, Integer pages) {
        gridPane = new GridPane();
        Label name = new Label("Name");
        Label numberOfProducts = new Label("NumberOfProducts");
        name.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        numberOfProducts.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        gridPane.setVgap(30);
        for (int i = 0; i < 5; i++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(120,
                        Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, HPos.CENTER, true));
        }
        setCategoryCounter(4*pages);
        for (int i = 1; i <= counter; categoryCounter++, i++) {
            setTheRows(i, allCategories.get(categoryCounter).getName(), String.valueOf(allCategories.get(categoryCounter).getAllProducts().size()));
        }
        gridPane.setTranslateX(10);
        gridPane.setTranslateY(1);
        gridPane.add(name, 0, 0);
        gridPane.add(numberOfProducts, 1, 0);
        allGridPanes.set(pages, gridPane);
    }




    private void setTheRows(int row, String name, String numberOfProducts) {
        Pane[] allPanes = new Pane[2];
        for (int i = 0; i < 2; i++) {
            allPanes[i] = new Pane();
            allPanes[i].setStyle("-fx-background-color: #e6e6e6");
        }
        Label[] allLabels = labelMaker(name, numberOfProducts);
        for (int i = 0; i < 2; i++) {
            if(i == 0){
                allLabels[i].setTranslateX(30);
            }
            if(i == 1){
                allLabels[i].setTranslateX(75);
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
        pane.getChildren().add(deletes.get(name));
        pane1.getChildren().add(edits.get(name));
        pane2.getChildren().add(viewDetails.get(name));
        gridPane.add(pane, 2, row);
        gridPane.add(pane1, 3, row);
        gridPane.add(pane2, 4, row);
    }

    private Label[] labelMaker(String categoryName, String numOfProducts) {
        Label[] allLabels = new Label[2];
        allLabels[0] = new Label(categoryName);
        allLabels[1] = new Label(numOfProducts);
        for (Label label : allLabels) {
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        }
        return allLabels;
    }


    @Override
    public void handle(ActionEvent event) {
        new CreateCategoryMenu(this.stage).execute();
    }
}

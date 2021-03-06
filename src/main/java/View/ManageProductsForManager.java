package View;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Product.Product;
import com.google.gson.Gson;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageProductsForManager extends Menu {
    GridPane productsPages;

    public ManageProductsForManager(Stage stage) {
        super(stage);
        this.stage = stage;
        productsPages = new GridPane();
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pageGridPane);
        scene.setRoot(scrollPane);
        scrollPane.setFitToWidth(true);
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, false));
    }

    private void setCenterGridPane() {
        centerGridPane.getChildren().clear();
        String buttomStyle = "  -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    " +
                "    -fx-background-insets: 0,0,0,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 7 10 7 10;" +
                "     -fx-background-radius: 40px;" +
                "    -fx-border-radius: 20px;";
        Text pageTitle = new Text("Manage Products");
        pageTitle.setStyle("-fx-font-size: 24;-fx-font-weight: bold ");
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        ProductController.getInstance().getAllProductsFromServer();
        for (int kk = 0; kk < ProductController.getInstance().getAllProducts().size(); kk++) {
            Product product = ProductController.getInstance().getAllProducts().get(kk);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text("   " + product.getProductName() + "\n" + "   " + product.getCostAfterOff() + " $");
            Label label = new Label("   " + Double.toString(product.getAverageScore()));
            ImageView star = new ImageView(new Image("file:src/star.png"));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            star.setFitWidth(20);
            star.setFitHeight(20);
            GridPane scoreGridPane = new GridPane();
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(15, Control.USE_COMPUTED_SIZE, 20, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.setHgap(2);
            scoreGridPane.add(label, 0, 0);
            scoreGridPane.add(star, 1, 0);
            ImageView deleteProduct = new ImageView(new Image("file:src/trash1.png"));
            deleteProduct.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);

                }
            });
            deleteProduct.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            deleteProduct.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    ProductController.getInstance().removeProductForManager(product.getProductId());
                    setCenterGridPane();
                }
            });
            deleteProduct.setFitWidth(25);
            deleteProduct.setFitHeight(25);
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 1, 1);
            GridPane options = new GridPane();
            options.getColumnConstraints().add(new ColumnConstraints(117, Control.USE_COMPUTED_SIZE, 117, Priority.NEVER, HPos.RIGHT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, false));
            options.setHgap(2);
            options.add(deleteProduct, 0, 0);
            gridPane.add(options, 0, 3, 2, 1);
            gridPanes.add(gridPane);
            gridPane.setStyle("-fx-background-color: #ECD5DC;-fx-background-radius: 20px;");
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            scoreGridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);

                }
            });
            scoreGridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            scoreGridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(ProductController.getInstance().getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
            text.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);

                }
            });
            text.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(ProductController.getInstance().getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
            imageView.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);

                }
            });
            imageView.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
        public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(ProductController.getInstance().getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
        }
        ArrayList<GridPane> productsPages = new ArrayList<>();
        for (int j = 0; j < (gridPanes.size() / 12) + (gridPanes.size() % 12 == 0 ? 0 : 1); j++) {
            productsPages.add(new GridPane());
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, 5, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.NEVER, HPos.LEFT, false));
            productsPages.get(j).setVgap(10);
            productsPages.get(j).setMinWidth(600);
            productsPages.get(j).setMaxHeight(300);
            for (int i = j * 12; i < (j) * 12 + (j == ((gridPanes.size() / 12) + (gridPanes.size() % 12 == 0 ? 0 : 1) - 1) ? gridPanes.size() % 12 : 12); i++) {
                productsPages.get(j).add(gridPanes.get(i), 2 * ((i % 12) % 4) + 1, ((i % 12) / 4), 1, 1);
            }
        }
        ArrayList<Button> buttons = new ArrayList<>();
        for (int i = 0; i < productsPages.size(); i++) {
            buttons.add(new Button(Integer.toString(i + 1)));
            buttons.get(i).setStyle(buttomStyle);
            buttons.get(i).setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);
                }
            });
            buttons.get(i).setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            final int[] j = {i};
            buttons.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node child : centerGridPane.getChildren()) {
                        if (productsPages.contains(child)) {
                            centerGridPane.getChildren().remove(child);
                            break;
                        }
                    }
                    GridPane buttons1 = new GridPane();
                    buttons1.setHgap(3);
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons1.add(buttons.get(i), i + 1, 0);
                    }
                    buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
                    productsPages.get(j[0]).add(buttons1, 1, 5, 7, 1);
                    centerGridPane.add(productsPages.get(j[0]), 1, 1, 2, 2);
                }
            });
        }
        GridPane buttons1 = new GridPane();
        buttons1.setHgap(3);
        for (int i = 0; i < buttons.size(); i++) {
            buttons1.add(buttons.get(i), i + 1, 0);
        }
        buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
        if (productsPages.size() > 0) {
            productsPages.get(0).add(buttons1, 1, 5, 7, 1);
        }
        GridPane leftMenuGridPane = new GridPane();
        leftMenuGridPane.setMinHeight(400);
        leftMenuGridPane.setStyle("-fx-background-color:rgba(45, 156, 240, 1);");
        centerGridPane.add(leftMenuGridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 1, 1);
        if (productsPages.size() > 0) {
            centerGridPane.add(productsPages.get(0), 1, 1, 2, 2);
        } else {
            ImageView imageView = new ImageView(new Image("file:src/empty.png"));
            Text text = new Text("You don't have any product to sell.");
            GridPane gridPane = new GridPane();
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            gridPane.add(imageView, 0, 0);
            gridPane.add(text, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(400, Control.USE_COMPUTED_SIZE, 400, Priority.NEVER, HPos.RIGHT, false));
            gridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 200, Priority.NEVER, HPos.RIGHT, false));
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 32));
            centerGridPane.add(gridPane, 3, 1, 1, 1);
        }
    }

    protected void setPageGridPain() {
        pageGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.NEVER, VPos.CENTER, false));
        pageGridPane.getRowConstraints().add(new RowConstraints(40, Control.USE_COMPUTED_SIZE, 40, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(80, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.TOP, true));
        pageGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.BOTTOM, false));
        pageGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_PREF_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.RIGHT, true));
        pageGridPane.add(upGridPane, 0, 0);
        pageGridPane.add(menuBarGridPane, 0, 1);
        pageGridPane.add(centerGridPane, 0, 2);
        pageGridPane.add(bottomGridPane, 0, 3);
    }

    public void execute() {
        stage.setScene(scene);
        stage.show();
    }
}

package View;

import Controller.Client.*;
import Controller.Server.AuctionCenter;
import Models.Auction;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ManageProducts extends Menu {
    GridPane productsPages;
    Product currentProduct;

    public ManageProducts(Stage stage) {
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
        Seller seller = (Seller) ClientController.getInstance().getCurrentUser();
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        for (int kk = 0; kk < seller.getAllProducts().size(); kk++) {
            Product product = seller.getAllProducts().get(kk);
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
            ImageView editInfoPic = new ImageView(new Image("file:src/edit3.png"));
            ImageView deleteProduct = new ImageView(new Image("file:src/trash1.png"));
            ImageView addToAdds = new ImageView(new Image("file:src/add.png"));
            ImageView auction = new ImageView(new Image("file:src/auction.png"));
            GridPane auctionGridPane = new GridPane();
            auctionGridPane.add(auction, 0, 0);
            auctionGridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            auctionGridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            auctionGridPane.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Stage popupwindow = new Stage();
                    currentProduct = product;
                    Button button1 = new Button("X");
                    button1.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
                    button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            scene.setFill(null);
                        }
                    });
                    GridPane gridPane1 = new GridPane();
                    gridPane1.getRowConstraints().add(new RowConstraints(25, Control.USE_COMPUTED_SIZE, 25, Priority.NEVER, VPos.CENTER, true));
                    gridPane1.add(button1, 0, 0);
                    gridPane1.add(createAuctionPopup(popupwindow), 1, 1);
                    gridPane1.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
                    Scene scene1 = new Scene(gridPane1, 500, 500);
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.initStyle(StageStyle.UNDECORATED);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            });
            editInfoPic.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            editInfoPic.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            editInfoPic.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    currentProduct = product;
                    handle1();
                }
            });
            GridPane addsGridPane = new GridPane();
            addsGridPane.add(addToAdds, 0, 0);
            addsGridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            addsGridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            addsGridPane.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    if (seller.getCredit() >= 50) {
                        ProductController.getInstance().setCommercializedProduct(product.getProductId());
                        seller.setCredit(seller.getCredit() - 50);
                    } else {
                        showMessage("You dont have enough money to create new add.cost of each add is 50 boxes.", MessageKind.ErrorWithoutBack);
                    }
                }
            });
            GridPane deleteProductPane = new GridPane();
            deleteProductPane.add(deleteProduct, 0, 0);
            deleteProductPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            deleteProductPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            deleteProductPane.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Gson gson = new Gson();
                    ProductController.getInstance().createDeleteProductRequest(product);
                    setCenterGridPane();
                }
            });
            editInfoPic.setFitWidth(25);
            editInfoPic.setFitHeight(25);
            deleteProduct.setFitWidth(25);
            deleteProduct.setFitHeight(25);
            addToAdds.setFitWidth(25);
            addToAdds.setFitHeight(25);
            auction.setFitWidth(25);
            auction.setFitHeight(25);
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 1, 1);
            GridPane options = new GridPane();
            options.getColumnConstraints().add(new ColumnConstraints(57, Control.USE_COMPUTED_SIZE, 57, Priority.NEVER, HPos.RIGHT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, false));
            options.setHgap(2);
            options.add(auctionGridPane, 0, 0);
            options.add(addsGridPane, 1, 0);
            options.add(editInfoPic, 2, 0);
            options.add(deleteProductPane, 3, 0);
            gridPane.add(options, 0, 3, 2, 1);
            gridPanes.add(gridPane);
            gridPane.setStyle("-fx-background-color: #ECD5DC;-fx-background-radius: 20px;");
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            scoreGridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            scoreGridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            scoreGridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(seller.getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
            text.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            text.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(seller.getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
            imageView.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND); //Change cursor to hand

                }
            });
            imageView.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
                }
            });
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(seller.getAllProducts().get(i));
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
                    scene.setCursor(Cursor.HAND); //Change cursor to hand
                }
            });
            buttons.get(i).setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
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
        Button addProduct = new Button("Add product");
        addProduct.setTextAlignment(TextAlignment.CENTER);
        addProduct.setStyle("-fx-font-size: 20 ;-fx-background-color:rgba(45, 156, 240, 0);-fx-text-alignment: center;-fx-text-fill: White;-fx-font-weight: bold;");
        addProduct.setMinHeight(50);
        addProduct.setMinWidth(150);
        addProduct.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        addProduct.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        addProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage popupwindow = new Stage();
                GridPane gridPane = new GridPane();
                scene.setFill(Color.GRAY);
                popupwindow.setTitle("Edit information.");
                gridPane.setStyle("-fx-background-color: Blue");
                Button button = new Button("X");
                button.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        scene.setFill(null);
                    }
                });
                gridPane.add(button, 0, 0);
                gridPane.add(new Text(""), 1, 0);
                ImageView seller = new ImageView(new Image("file:src/product_icon.png"));
                ImageView customer = new ImageView(new Image("file:src/other_file.png"));
                customer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new AddFileScene(stage).execute();
                    }
                });
                seller.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new AddProductScene(stage).execute();
                    }
                });
                seller.setFitWidth(100);
                customer.setFitWidth(100);
                seller.setFitHeight(110);
                customer.setFitHeight(110);
                gridPane.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
                GridPane photoGridPane = new GridPane();
                photoGridPane.setVgap(20);
                photoGridPane.setHgap(20);
                photoGridPane.add(seller, 0, 0);
                photoGridPane.add(customer, 1, 0);
                gridPane.add(photoGridPane, 1, 1);
                photoGridPane.setAlignment(Pos.CENTER);
                gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.CENTER, true));
                photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                photoGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 200, Priority.ALWAYS, HPos.CENTER, false));
                photoGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
                photoGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                Label customer1 = new Label("File");
                customer1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new RegisterMenu(stage).execute();
                    }
                });
                Label seller1 = new Label("Product");
                customer1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                seller1.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                seller1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new SellerRegisterMenu(stage).execute();
                    }
                });
                photoGridPane.add(seller1, 0, 1);
                photoGridPane.add(customer1, 1, 1);
                Scene scene1 = new Scene(gridPane, 320, 240);
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.initStyle(StageStyle.UNDECORATED);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });
        leftMenuGridPane.add(addProduct, 0, 2, 2, 2);
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

    private boolean checkNameIsValid(String name) {
        if (Pattern.matches("(\\w+)( \\w+)*", name) && !name.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkCreditIsValid(String credit) {
        if (Pattern.matches("\\d+\\.?\\d*", credit)) {
            return true;
        }
        return false;
    }

    public void handle1() {
        CategoryController.getInstance().updateAllCategories();
        Stage popupwindow2 = new Stage();
        Button button = new Button("X");
        button.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popupwindow2.hide();
                scene.setFill(null);
            }
        });

        String imagePath[] = {""};
        GridPane userInfoGridPane;
        userInfoGridPane = new GridPane();
        TextField productName, availableNumbers, description, cost, companyName;
        String menuBarStyle = "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 0 0 0 0;";
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        productName = new TextField();
        companyName = new TextField();
        availableNumbers = new TextField();
        description = new TextField();
        cost = new TextField();
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(100);
        Text title = new Text("Edit Product");
        ImageView userImage = new ImageView(new Image("file:src/product_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text productName1 = new Text("Product Name");
        Text companyNameText = new Text("Company Name");
        Text categoryText = new Text("Category");
        Text descriptionText = new Text("Description");
        Text costText = new Text("Products Cost");
        Text availableNumbersText = new Text("Available numbers");
        title.setStyle("-fx-font-size: 24;-fx-font-weight: bold ");

        Button editPhotoButton = new Button("Choose Photo");
        Button editProduct = new Button("Edit Product");
        productName.setText(currentProduct.getProductName());
        availableNumbers.setText(Integer.toString(currentProduct.getNumberOfAvailableProducts()));
        description.setText(currentProduct.getDescription());
        cost.setText(Double.toString(currentProduct.getProductCost()));
        companyName.setText(currentProduct.getProductCompany());
        editProduct.setStyle("-fx-background-color: #E85D9E;");
        editProduct.setMinWidth(100);
        editProduct.setTextFill(Color.WHITE);
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        editPhotoButton.setStyle("-fx-background-color: #E85D9E");
        editPhotoButton.setMinWidth(100);
        editPhotoButton.setTextFill(Color.WHITE);
        FileChooser fileChooser = new FileChooser();
        Text selectedCategoryText = new Text("Selected Category");
        Text selectedCategory = new Text("");
        selectedCategory.setText(currentProduct.getProductsCategory());
        EventHandler<ActionEvent> eventChoosePhoto = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File selectedFile = fileChooser.showOpenDialog(popupwindow2);
                if (selectedFile != null) {
                    userImage.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
                    imagePath[0] = "file:" + selectedFile.getAbsolutePath();
                }
            }
        };
        HashMap<String, String> categoryFeaturesForProduct = currentProduct.getFeaturesOfCategoryThatHas();
        ArrayList<MenuItem> menuItemArrayList = new ArrayList<>();
        String s = currentProduct.getProductsCategory();
        {
            MenuItem menuItem = new MenuItem("     1" + s);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GridPane gridPane = new GridPane();
                    selectedCategory.setText(s);
                    Category category1 = new Category(null, null);
                    CategoryController.getInstance().updateAllCategories();
                    for (Category category : CategoryController.getInstance().getAllCategories()) {
                        if (category.getName().equals(s)) {
                            category1 = category;
                        }
                    }
                    HashMap<Text, MenuButton> categoryFeatures = new HashMap<>();
                    ArrayList<MenuButton> menuButtons = new ArrayList<>();
                    for (String s1 : category1.getFeatures().keySet()) {
                        Text text = new Text(s1);
                        ImageView imageView = new ImageView(new Image("file:src/standings.png"));
                        imageView.setFitHeight(30);
                        imageView.setFitWidth(30);
                        MenuButton menuButton = new MenuButton(s1, imageView);
                        ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
                        for (String s2 : category1.getFeatures().get(s1)) {
                            MenuItem menuItem1 = new MenuItem(s2);
                            menuItemArrayList1.add(menuItem1);
                            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (categoryFeaturesForProduct.containsKey(s1)) {
                                        categoryFeaturesForProduct.replace(s1, s2);
                                    } else {
                                        categoryFeaturesForProduct.put(s1, s2);
                                    }
                                }
                            });
                        }
                        for (MenuItem item : menuItemArrayList1) {
                            menuButton.getItems().add(item);
                        }
                        categoryFeatures.put(text, menuButton);
                    }
                    Stage popupwindow = new Stage();
                    scene.setFill(Color.GRAY);
                    popupwindow.setTitle("Category Features");
                    gridPane.setStyle("-fx-background-color: Blue");
                    Button button = new Button("X");
                    button.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            popupwindow.hide();
                            scene.setFill(null);
                        }
                    });
                    gridPane.add(button, 0, 0);
                    gridPane.add(new Text(""), 1, 0);
                    gridPane.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
                    GridPane featuresGridPane = new GridPane();
                    gridPane.add(featuresGridPane, 1, 1, 1, 1);
                    featuresGridPane.setHgap(5);
                    featuresGridPane.setVgap(5);
                    featuresGridPane.setAlignment(Pos.CENTER);
                    int k1 = 0;
                    for (Text text : categoryFeatures.keySet()) {
                        categoryFeatures.get(text).setStyle(menuBarStyle);
                        featuresGridPane.add(text, 0, k1);
                        featuresGridPane.add(categoryFeatures.get(text), 1, k1);
                        Text text1 = new Text("");
                        featuresGridPane.add(text1, 2, k1);
                        for (String s1 : currentProduct.getFeaturesOfCategoryThatHas().keySet()) {
                            if (s1.equals(text.getText())) {
                                text1.setText(currentProduct.getFeaturesOfCategoryThatHas().get(text.getText()));
                            }
                        }
                        for (MenuItem item : categoryFeatures.get(text).getItems()) {
                            item.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    text1.setText(item.getText());
                                    if (categoryFeaturesForProduct.containsKey(text.getText())) {
                                        categoryFeaturesForProduct.replace(text.getText(), item.getText());
                                    } else {
                                        categoryFeaturesForProduct.put(text.getText(), item.getText());
                                    }
                                }
                            });
                        }
                        k1++;
                    }
                    gridPane.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                    gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.NEVER, VPos.TOP, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.CENTER, true));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, HPos.CENTER, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(120, Control.USE_COMPUTED_SIZE, 120, Priority.ALWAYS, HPos.LEFT, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 90, Priority.ALWAYS, HPos.LEFT, false));
                    Scene scene1 = new Scene(gridPane, 320, categoryFeatures.size() * 50 + 200);
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.initStyle(StageStyle.UNDECORATED);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            });
            menuItemArrayList.add(menuItem);
        }
        ImageView imageView = new ImageView(new Image("file:src/standings.png"));
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        MenuButton menuButton = new MenuButton("Categories", imageView);
        for (MenuItem menuItem : menuItemArrayList) {
            menuButton.getItems().add(menuItem);
        }
        menuButton.setStyle(menuBarStyle);
        editPhotoButton.setOnAction(eventChoosePhoto);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        upGridPane.add(title, 1, 0, 1, 1);
        productName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        companyName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        description.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        cost.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        availableNumbers.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
        userInfoGridPane.add(userImage, 13, 1, 5, 5);
        userInfoGridPane.add(productName1, 5, 9, 3, 1);
        userInfoGridPane.add(companyNameText, 5, 10, 3, 1);
        userInfoGridPane.add(descriptionText, 5, 11, 3, 1);
        userInfoGridPane.add(costText, 5, 12, 3, 1);
        userInfoGridPane.add(availableNumbersText, 14, 9, 3, 1);
        userInfoGridPane.add(categoryText, 15, 11, 3, 1);
        userInfoGridPane.add(selectedCategoryText, 14, 12, 2, 1);
        userInfoGridPane.add(productName, 8, 9, 6, 1);
        userInfoGridPane.add(companyName, 8, 10, 6, 1);
        userInfoGridPane.add(description, 8, 11, 6, 1);
        userInfoGridPane.add(menuButton, 18, 11, 3, 1);
        userInfoGridPane.add(selectedCategory, 19, 12);
        userInfoGridPane.add(cost, 8, 12, 6, 1);
        userInfoGridPane.add(availableNumbers, 19, 9, 1, 1);
        userInfoGridPane.add(editPhotoButton, 13, 6, 5, 1);
        userInfoGridPane.add(editProduct, 13, 16, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        editProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                productName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                companyName.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                description.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                cost.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                availableNumbers.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                errorText.setText("");
                if (checkNameIsValid(companyName.getText().trim())) {
                    if (Pattern.matches("\\d+", availableNumbers.getText().trim())) {
                        if (!(description.getText().trim()).isEmpty()) {
                            if (checkNameIsValid(productName.getText())) {
                                if (checkCreditIsValid(cost.getText())) {
                                    if (categoryFeaturesForProduct.size() == CategoryController.getInstance().getCategoryWithName(selectedCategory.getText().trim()).getFeatures().size()) {
                                        Product product = new Product(companyNameText.getText().trim(), currentProduct.getProductId(), productName.getText().trim(),
                                                (Seller) ClientController.getInstance().getCurrentUser(), Double.parseDouble(cost.getText().trim()), selectedCategory.getText().trim(), description.getText().trim(), Integer.parseInt(availableNumbers.getText().trim()), categoryFeaturesForProduct);
                                        product.setImagePath(imagePath[0]);
                                        ProductController.getInstance().editProduct(product);
                                    } else {
                                        errorText.setText("You need to choose a mode for each feature of category.");
                                    }
                                } else {
                                    cost.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                    errorText.setText("csot is not a number.");
                                }
                            } else {
                                productName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                                errorText.setText("Product Name is invalid. use alphabetical characters and space.");
                            }
                        } else {
                            description.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                            errorText.setText("description is empty");
                        }
                    } else {
                        availableNumbers.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("available number of product is not a number.");
                    }
                } else {
                    companyName.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Company Name Format is Invalid. use 0-9 alphabetical character.");
                }
            }
        });
        scene.setFill(Color.GRAY);
        popupwindow2.setTitle("Edit information.");
        GridPane gridPane1 = new GridPane();
        gridPane1.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
        gridPane1.getRowConstraints().add(new RowConstraints(500, Control.USE_COMPUTED_SIZE, 500, Priority.NEVER, VPos.TOP, true));
        gridPane1.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
        gridPane1.getColumnConstraints().add(new ColumnConstraints(600, Control.USE_COMPUTED_SIZE, 600, Priority.NEVER, HPos.CENTER, true));
        gridPane1.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);");
        gridPane1.add(button, 0, 0);
        gridPane1.add(new Text(""), 1, 0);
        gridPane1.add(userInfoGridPane, 1, 1);
        Scene scene1 = new Scene(gridPane1, 650, 500);
        popupwindow2.initModality(Modality.APPLICATION_MODAL);
        popupwindow2.initStyle(StageStyle.UNDECORATED);
        popupwindow2.setScene(scene1);
        popupwindow2.show();

    }

    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private ArrayList<String> allUsers = new ArrayList<>();
    GridPane userInfoGridPane;

    private GridPane createAuctionPopup(Stage stage) {
        userInfoGridPane = new GridPane();
        userInfoGridPane.setVgap(10);
        userInfoGridPane.setHgap(20);
        userInfoGridPane.setMinWidth(650);
        userInfoGridPane.setMinHeight(400);
        GridPane leftGridPane = new GridPane();
        GridPane upGridPane = new GridPane();
        upGridPane.setMinHeight(50);
        leftGridPane.setMinWidth(25);
        Text title = new Text("\t  Create Discount Code");
        ImageView userImage = new ImageView(new Image("file:src/user_icon.png"));
        userImage.setFitHeight(100);
        userImage.setFitWidth(100);
        Text startDateText = new Text("Start Date");
        Text endDateText = new Text("End Date");
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
        Button editPhotoButton = new Button("Choose Photo");
        Button createAuction = new Button("Create Auction");
        createAuction.setStyle("-fx-background-color: #E85D9E;");
        createAuction.setMinWidth(100);
        createAuction.setTextFill(Color.WHITE);
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        editPhotoButton.setStyle("-fx-background-color: #E85D9E");
        editPhotoButton.setMinWidth(100);
        editPhotoButton.setTextFill(Color.WHITE);
        FileChooser fileChooser = new FileChooser();
        EventHandler<ActionEvent> eventChoosePhoto = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    userImage.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
                }
            }
        };
        editPhotoButton.setOnAction(eventChoosePhoto);
        // userInfoGridPane.add(title,0,0);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        // userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        // upGridPane.setGridLinesVisible(true);
        upGridPane.add(title, 1, 0, 1, 1);
        userInfoGridPane.add(startDateText, 5, 11, 3, 1);
        userInfoGridPane.add(endDateText, 5, 12, 3, 1);
        userInfoGridPane.add(startDatePicker, 8, 11, 6, 1);
        userInfoGridPane.add(endDatePicker, 8, 12, 6, 1);
        userInfoGridPane.add(createAuction, 13, 16, 5, 1);
        userInfoGridPane.add(errorText, 7, 14, 10, 1);
        userInfoGridPane.setStyle("-fx-background-color: #ECD5DC;");
        createAuction.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startDatePicker.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                endDatePicker.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; ");
                errorText.setText("");
                Date startdate = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date enddate = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (checkStartTimeValid(startdate)) {
                    if (checkEndTimeValid(enddate, startdate)) {
                        AuctionController.getInstance().createNewAuction(new Auction(startdate, enddate, currentProduct, ClientController.getInstance().getCurrentUser().getUsername()));
                        stage.close();
                    } else {
                        endDatePicker.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                        errorText.setText("End time is invalid.");
                    }
                } else {
                    startDatePicker.setStyle("-fx-background-color: red;-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                    errorText.setText("Start date is invalid.");
                }
            }
        });
        return userInfoGridPane;
    }

    private boolean checkEndTimeValid(Date endTime, Date startTime) {
        if (endTime.after(new Date()) && endTime.after(startTime)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkStartTimeValid(Date startTime) {
        if (startTime.after(new Date())) {
            return true;
        } else {
            return false;
        }
    }

}


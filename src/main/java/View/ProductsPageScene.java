package View;

import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ManagerController;
import Controller.Client.ProductController;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ProductsPageScene extends Menu {
    GridPane productsPages;
    Product currentProduct;
    GridPane leftMenuGridPane, centerGridPaneTosh;
    private boolean offerChecker;

    public ProductsPageScene(Stage stage) {
        super(stage);
        this.stage = stage;
        productsPages = new GridPane();
        leftMenuGridPane = new GridPane();
        centerGridPaneTosh = new GridPane();
        if (ClientController.getInstance().getMediaPlayer() != null)
            ClientController.getInstance().getMediaPlayer().stop();
        ClientController.getInstance().setMediaPlayer(new MediaPlayer(productsSong));
        ClientController.getInstance().getMediaPlayer().setVolume(0.04);
        ClientController.getInstance().getMediaPlayer().play();
        ClientController.getInstance().getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
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
        //  Customer customer=(Customer) ClientController.getInstance().getCurrentUser();
        Text personalInfo = new Text("ali");
        Text pageTitle = new Text(ProductController.getInstance().getCurrentCategory().getName() + "  Count of products: " + ProductController.getInstance().getCurrentCategory().getAllProducts().size());
        personalInfo.setFont(Font.loadFont("file:src/BalooBhai2-Regular.ttf", 16));
        pageTitle.setStyle("-fx-font-weight: bold;");
        pageTitle.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
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
                new AddProductScene(stage).execute();
            }
        });
        String menuBarStyle = "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 0 0 0 0;";
        Category category1 = CategoryController.getInstance().getCurrentCategory();
        HashMap<Text, MenuButton> categoryFeatures = new HashMap<>();
        ArrayList<MenuButton> menuButtons = new ArrayList<>();
        for (String s1 : category1.getFeatures().keySet()) {
            Text text = new Text(s1);
            MenuButton menuButton = new MenuButton(s1);
            ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
            for (String s2 : category1.getFeatures().get(s1)) {
                Text text1 = new Text("");
                MenuItem menuItem1 = new MenuItem(s2);
                menuItemArrayList1.add(menuItem1);
                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (menuItem1.getText().startsWith("Used: ")) {
                            if (ProductController.getInstance().getCategoryFeaturesToFilter() == null) {
                                ProductController.getInstance().setCategoryFeaturesToFilter(new HashMap<String, ArrayList<String>>());
                            }
                            menuItem1.setText(menuItem1.getText().substring(6));
                            if (ProductController.getInstance().getCategoryFeaturesToFilter()
                                    .get(s1).contains(menuItem1.getText())) {
                                HashMap<String, ArrayList<String>> hashMap = ProductController.getInstance().getCategoryFeaturesToFilter();
                                hashMap.get(s1).remove(menuItem1.getText());
                                if (hashMap.get(s1).size() == 0) {
                                    hashMap.remove(s1);
                                }
                                ProductController.getInstance().setCategoryFeaturesToFilter(hashMap);
                            }
                            setProductsPart(buttomStyle);
                        } else {
                            if (ProductController.getInstance().getCategoryFeaturesToFilter() == null) {
                                ProductController.getInstance().setCategoryFeaturesToFilter(new HashMap<String, ArrayList<String>>());
                            }
                            if (ProductController.getInstance().getCategoryFeaturesToFilter()
                                    .containsKey(s1)) {
                                ProductController.getInstance().getCategoryFeaturesToFilter()
                                        .get(s1).add(menuItem1.getText());
                            } else {
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(menuItem1.getText());
                                ProductController.getInstance().getCategoryFeaturesToFilter().put(s1, arrayList);
                            }
                            setProductsPart(buttomStyle);

                            menuItem1.setText("Used: " + menuItem1.getText());
                        }
                    }
                });
            }
            for (MenuItem item : menuItemArrayList1) {
                menuButton.getItems().add(item);
            }
            categoryFeatures.put(text, menuButton);
        }
        scene.setFill(Color.GRAY);
        GridPane featuresGridPane = new GridPane();
        featuresGridPane.setAlignment(Pos.CENTER);
        int k1 = 0;
        for (Text text : categoryFeatures.keySet()) {
            categoryFeatures.get(text).setStyle(menuBarStyle);
            featuresGridPane.add(text, 0, k1 + 1);
            featuresGridPane.add(categoryFeatures.get(text), 1, k1 + 1);
            k1++;
        }
        Text text11 = new Text("Filters");
        text11.setStyle("-fx-font-weight: bold;");
        text11.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        featuresGridPane.add(text11, 1, 0);
        featuresGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, false));
        featuresGridPane.getColumnConstraints().add(new ColumnConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, HPos.CENTER, false));
        featuresGridPane.getColumnConstraints().add(new ColumnConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.ALWAYS, HPos.LEFT, false));
        featuresGridPane.setVgap(10);
        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.ALWAYS, HPos.LEFT, false));
        CheckBox checkBox = getOfferCheckBox(buttomStyle);
        MenuButton brandFilterButton = getBrandFilterButton(buttomStyle, menuBarStyle);
        MenuButton sellersFilterButton = getSellerFilterButton(buttomStyle, menuBarStyle);
        MenuButton statusFilterButton = getProductStatusFilterButton(buttomStyle, menuBarStyle);
        GridPane nameFilter = new GridPane();
        Label nameFilterText = new Label("Name:");
        TextField nameFilterTextField = new TextField();
        Button setNameFilter = new Button("Filter with name");
        nameFilter.add(nameFilterText, 0, 0);
        nameFilter.add(nameFilterTextField, 1, 0);
        nameFilterTextField.setMaxWidth(100);
        setNameFilter.setAlignment(Pos.CENTER);
        setNameFilter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (nameFilterTextField.getText().isEmpty()) {
                    ProductController.getInstance().disableNameFilter();
                } else {
                    ProductController.getInstance().setNameToFilter(nameFilterTextField.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        GridPane price = priceFilters(buttomStyle);
        MenuButton sortButton = getSortButton(buttomStyle);
        leftMenuGridPane.setVgap(5);
        leftMenuGridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.ALWAYS, HPos.CENTER, false));
        leftMenuGridPane.add(checkBox, 0, 3);
        leftMenuGridPane.add(featuresGridPane, 0, 2);
        leftMenuGridPane.add(price, 0, 4, 1, 2);
        leftMenuGridPane.add(brandFilterButton, 0, 6);
        leftMenuGridPane.add(sellersFilterButton, 0, 7);
        leftMenuGridPane.add(statusFilterButton, 0, 8);
        leftMenuGridPane.add(nameFilter, 0, 9);
        leftMenuGridPane.add(setNameFilter, 0, 10, 2, 1);
        leftMenuGridPane.add(sortButton, 0, 11, 2, 1);
        gridPane.add(leftMenuGridPane, 0, 0);
        centerGridPane.add(gridPane, 0, 1, 1, 6);
        centerGridPane.add(pageTitle, 0, 0, 2, 1);
        setProductsPart(buttomStyle);
        centerGridPane.add(centerGridPaneTosh, 1, 1);
    }

    private MenuButton getSortButton(String buttomStyle) {
        MenuButton sortButton = new MenuButton("Sort");
        MenuItem menuItem = new MenuItem("price ascending");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem.setText(menuItem.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem.setText("Used: " + menuItem.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("price", true);
                    menuItem.setText("Used: " + menuItem.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        MenuItem menuItem1 = new MenuItem("price descending");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem1.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem1.setText(menuItem1.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem1.setText("Used: " + menuItem1.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("price", false);
                    menuItem1.setText("Used: " + menuItem1.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        MenuItem menuItem2 = new MenuItem("score ascending");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem2.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem2.setText(menuItem2.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem2.setText("Used: " + menuItem2.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("score", true);
                    menuItem2.setText("Used: " + menuItem2.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        MenuItem menuItem3 = new MenuItem("score descending");
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem3.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem3.setText(menuItem3.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem3.setText("Used: " + menuItem3.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("score", false);
                    menuItem3.setText("Used: " + menuItem3.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        MenuItem menuItem4 = new MenuItem("newest ascending");
        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem4.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem4.setText(menuItem4.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem4.setText("Used: " + menuItem4.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("newest", true);
                    menuItem4.setText("Used: " + menuItem4.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        MenuItem menuItem5 = new MenuItem("newest descending");
        menuItem5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem5.getText().startsWith("Used: ")) {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem5.setText(menuItem5.getText().substring(6));
                        }
                    }
                    ProductController.getInstance().disableSort();
                } else {
                    for (MenuItem item : sortButton.getItems()) {
                        if (item.getText().startsWith("Used: ")) {
                            menuItem5.setText("Used: " + menuItem5.getText());
                        }
                    }
                    ProductController.getInstance().setCurrentSort("newest", false);
                    menuItem5.setText("Used: " + menuItem5.getText());
                }
                setProductsPart(buttomStyle);
            }
        });
        sortButton.getItems().add(menuItem);
        sortButton.getItems().add(menuItem1);
        sortButton.getItems().add(menuItem2);
        sortButton.getItems().add(menuItem3);
        sortButton.getItems().add(menuItem4);
        sortButton.getItems().add(menuItem5);
        return sortButton;
    }

    private CheckBox getOfferCheckBox(String buttonStyle) {
        CheckBox checkBox = new CheckBox("Offers");
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkBox.isSelected()) {
                    offerChecker = true;
                } else {
                    offerChecker = false;
                }
                setProductsPart(buttonStyle);
            }
        });
        return checkBox;
    }

    private MenuButton getBrandFilterButton(String buttonStyle, String menuBarStyle) {
        MenuButton brandFilterButton = new MenuButton("Brands");
        ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
        for (String s2 : ProductController.getInstance().getAllBrands()) {
            Text text1 = new Text("");
            MenuItem menuItem1 = new MenuItem(s2);
            menuItemArrayList1.add(menuItem1);
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (menuItem1.getText().startsWith("Used: ")) {
                        if (ProductController.getInstance().getAllBrandsToFilter() == null) {
                            ProductController.getInstance().setAllBrandsToFilter(new ArrayList<>());
                        }
                        menuItem1.setText(menuItem1.getText().substring(6));
                        if (ProductController.getInstance().getAllBrandsToFilter().contains(menuItem1.getText())) {
                            ArrayList<String> arrayList = ProductController.getInstance().getAllBrandsToFilter();
                            arrayList.remove(menuItem1.getText());
                            ProductController.getInstance().setAllBrandsToFilter(arrayList);
                        }
                        setProductsPart(buttonStyle);
                    } else {
                        if (ProductController.getInstance().getAllBrandsToFilter() == null) {
                            ProductController.getInstance().setAllBrandsToFilter(new ArrayList<>());
                        }
                        if (!ProductController.getInstance().getAllBrandsToFilter().contains(menuItem1.getText())) {
                            ArrayList<String> arrayList = ProductController.getInstance().getAllBrandsToFilter();
                            arrayList.add(menuItem1.getText());
                            ProductController.getInstance().setAllBrandsToFilter(arrayList);
                        }
                        menuItem1.setText("Used: " + menuItem1.getText());
                        setProductsPart(buttonStyle);

                    }
                }
            });
        }
        for (MenuItem item : menuItemArrayList1) {
            brandFilterButton.getItems().add(item);
        }
        brandFilterButton.setStyle(menuBarStyle);
        return brandFilterButton;
    }

    private MenuButton getSellerFilterButton(String buttonStyle, String menuBarStyle) {
        MenuButton sellersFilterButton = new MenuButton("Sellers");
        ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
        for (String s2 : ProductController.getInstance().getAllSellers()) {
            Text text1 = new Text("");
            MenuItem menuItem1 = new MenuItem(s2);
            menuItemArrayList1.add(menuItem1);
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (menuItem1.getText().startsWith("Used: ")) {
                        if (ProductController.getInstance().getAllSellersToFilter() == null) {
                            ProductController.getInstance().setAllSellersToFilter(new ArrayList<>());
                        }
                        menuItem1.setText(menuItem1.getText().substring(6));
                        if (ProductController.getInstance().getAllSellersToFilter().contains(menuItem1.getText())) {
                            ArrayList<String> arrayList = ProductController.getInstance().getAllSellersToFilter();
                            arrayList.remove(menuItem1.getText());
                            ProductController.getInstance().setAllSellersToFilter(arrayList);
                        }
                        setProductsPart(buttonStyle);
                    } else {
                        if (ProductController.getInstance().getAllSellersToFilter() == null) {
                            ProductController.getInstance().setAllSellersToFilter(new ArrayList<>());
                        }
                        if (!ProductController.getInstance().getAllSellersToFilter().contains(menuItem1.getText())) {
                            ArrayList<String> arrayList = ProductController.getInstance().getAllSellersToFilter();
                            arrayList.add(menuItem1.getText());
                            ProductController.getInstance().setAllSellersToFilter(arrayList);
                        }
                        menuItem1.setText("Used: " + menuItem1.getText());
                        setProductsPart(buttonStyle);

                    }
                }
            });
        }
        for (MenuItem item : menuItemArrayList1) {
            sellersFilterButton.getItems().add(item);
        }
        sellersFilterButton.setStyle(menuBarStyle);
        return sellersFilterButton;
    }

    private MenuButton getProductStatusFilterButton(String buttonStyle, String menuBarStyle) {
        MenuButton sellersFilterButton = new MenuButton("Status");
        ArrayList<MenuItem> menuItemArrayList1 = new ArrayList<>();
        ArrayList<ProductStatus> allProductStatusToFilter = new ArrayList<>();
        ArrayList<String> allAvailableProductStatus = new ArrayList<>();
        allAvailableProductStatus.add(ProductStatus.accepted.getName());
        allAvailableProductStatus.add(ProductStatus.inCreatingProgress.getName());
        allAvailableProductStatus.add(ProductStatus.editing.getName());
        ArrayList<ProductStatus> allAvailableProductStatus1 = new ArrayList<>();
        allAvailableProductStatus1.add(ProductStatus.accepted);
        allAvailableProductStatus1.add(ProductStatus.inCreatingProgress);
        allAvailableProductStatus1.add(ProductStatus.editing);
        for (String s2 : allAvailableProductStatus) {
            Text text1 = new Text("");
            MenuItem menuItem1 = new MenuItem(s2);
            menuItemArrayList1.add(menuItem1);
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (menuItem1.getText().startsWith("Used: ")) {
                        if (ProductController.getInstance().getAllProductStatusToFilter() == null) {
                            ProductController.getInstance().setAllStatusToFilter(new ArrayList<>());
                        }
                        menuItem1.setText(menuItem1.getText().substring(6));
                        for (ProductStatus status : allProductStatusToFilter) {
                            if (status.getName().equals(s2)) {
                                allProductStatusToFilter.remove(status);
                                ProductController.getInstance().setAllStatusToFilter(allProductStatusToFilter);
                                break;
                            }
                        }
                        setProductsPart(buttonStyle);
                    } else {
                        if (ProductController.getInstance().getAllProductStatusToFilter() == null) {
                            ProductController.getInstance().setAllStatusToFilter(new ArrayList<>());
                        }
                        for (ProductStatus status : allAvailableProductStatus1) {
                            if (status.getName().equals(s2)) {
                                if (!allProductStatusToFilter.contains(status)) {
                                    allProductStatusToFilter.add(status);
                                }
                                ProductController.getInstance().setAllStatusToFilter(allProductStatusToFilter);
                                break;
                            }
                        }
                        menuItem1.setText("Used: " + menuItem1.getText());
                        setProductsPart(buttonStyle);

                    }
                }
            });
        }
        for (MenuItem item : menuItemArrayList1) {
            sellersFilterButton.getItems().add(item);
        }
        sellersFilterButton.setStyle(menuBarStyle);
        return sellersFilterButton;
    }

    private GridPane priceFilters(String buttonStyle) {
        GridPane price = new GridPane();
        Label minValue = new Label("Min Price:");
        Label maxValue = new Label("Max Price:");
        TextField minPrice = new TextField();
        TextField maxPrice = new TextField();
        minPrice.setMaxWidth(90);
        maxPrice.setMaxWidth(90);
        price.add(minValue, 0, 0);
        price.add(minPrice, 1, 0);
        price.add(maxValue, 0, 1);
        price.add(maxPrice, 1, 1);
        GridPane gridPane = new GridPane();
        Button setPriceFilters = new Button("Set price Filter");
        setPriceFilters.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((Pattern.matches("\\d+\\.?\\d*", minPrice.getText()) || minPrice.getText().isEmpty()) && (Pattern.matches("\\d+\\.?\\d*", maxPrice.getText()) || maxPrice.getText().isEmpty())) {
                    ProductController.getInstance().setMaxAndMinAmount(minPrice.getText().isEmpty() ? 0 : Double.parseDouble(minPrice.getText().trim()),
                            maxPrice.getText().isEmpty() ? Double.POSITIVE_INFINITY : Double.parseDouble(maxPrice.getText().trim()));
                } else {
                    ProductController.getInstance().disablePriceFilter();
                }
                setProductsPart(buttonStyle);
            }
        });
        gridPane.add(price, 0, 0);
        gridPane.add(setPriceFilters, 0, 1, 2, 1);
        gridPane.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, 150, Priority.ALWAYS, HPos.CENTER, true));
        price.setVgap(2);
        gridPane.setVgap(2);
        return gridPane;
    }

    public ArrayList<Product> showProductsAfterFilterAndSort() {
        if (offerChecker) {
            return ProductController.getInstance().showOffedProductsAfterFilterAndSort();
        } else {
            return ProductController.getInstance().showProductsAfterFilterAndSort();
        }
    }

    private void setProductsPart(String buttonStyle) {
        ProductController.getInstance().getAllProductsFromServer();
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        ArrayList<GridPane> addGridPanes = new ArrayList<>();
        ManagerController.getInstance().getAllUserFromServer();
        System.out.println(ProductController.getInstance().getAllCommercializedProduct().size());
        centerGridPaneTosh.getChildren().clear();
        for (int kk = 0; kk < showProductsAfterFilterAndSort().size(); kk++) {
            Product product = showProductsAfterFilterAndSort().get(kk);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text();
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
            if (offerChecker) {
                text = new Text("   " + product.getProductName() + "\n" + "   " + product.getProductCost() + " $");
                Text text1 = new Text("   Cost after off: " + product.getCostAfterOff() + " $\n" + "   " + "Offer percent: " + product.getOffer().getAmount() + "%");
                Text text2 = new Text("   " + "Start time: \n" + product.getOffer().getStartTime().toString() + "\n"
                        + "   " + "End time: \n" + product.getOffer().getEndTime().toString());
                text1.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
                text2.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
                gridPane.add(imageView, 0, 0, 1, 1);
                gridPane.add(text, 0, 1, 1, 1);
                gridPane.add(text1, 0, 2, 1, 1);
                gridPane.add(text2, 0, 3, 1, 1);
                gridPane.add(scoreGridPane, 0, 4, 1, 1);
            } else {
                text = new Text("   " + product.getProductName() + "\n" + "   " + product.getCostAfterOff() + " $");
                gridPane.add(imageView, 0, 0, 2, 1);
                gridPane.add(text, 0, 1, 1, 1);
                gridPane.add(scoreGridPane, 0, 2, 1, 1);
            }
            GridPane options = new GridPane();
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
                            new ProductMenu(stage).execute();
                            break;
                        }
                    }
                }
            });
        }
        GridPane addGridPane = new GridPane();
        createAddGridPanes(addGridPanes);
        createAddGridPane(addGridPane, addGridPanes, buttonStyle);
        ArrayList<GridPane> productsPages = new ArrayList<>();
        for (int j = 0; j < (gridPanes.size() / 8) + (gridPanes.size() % 8 == 0 ? 0 : 1); j++) {
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
            for (int i = j * 8; i < (j) * 8 + (j == ((gridPanes.size() / 8) + (gridPanes.size() % 8 == 0 ? 0 : 1) - 1) ? gridPanes.size() % 8 : 8); i++) {
                productsPages.get(j).add(gridPanes.get(i), 2 * ((i % 8) % 4) + 1, ((i % 8) / 4), 1, 1);
            }
        }
        ArrayList<Button> buttons = new ArrayList<>();
        for (int i = 0; i < productsPages.size(); i++) {
            buttons.add(new Button(Integer.toString(i + 1)));
            buttons.get(i).setStyle(buttonStyle);
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
                    for (Node child : centerGridPaneTosh.getChildren()) {
                        if (productsPages.contains(child)) {
                            centerGridPaneTosh.getChildren().remove(child);
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
                    centerGridPaneTosh.add(productsPages.get(j[0]), 1, 3);
                }
            });
        }
        GridPane buttons1 = new GridPane();
        buttons1.setHgap(3);
        for (int i = 0; i < buttons.size(); i++) {
            buttons1.add(buttons.get(i), i + 1, 0);
        }
        for (GridPane productsPage : productsPages) {
            productsPage.setHgap(10);
        }
        buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
        if (productsPages.size() > 0) {
            productsPages.get(0).add(buttons1, 1, 5, 7, 1);
            if (!addGridPane.getChildren().isEmpty()) {
                System.out.println(1);
                centerGridPaneTosh.setVgap(5);
                centerGridPaneTosh.add(addGridPane, 1, 1, 1, 1);
                Text text= new Text(" Products");
                text.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
                centerGridPaneTosh.add(text, 1, 2, 1, 1);
                centerGridPaneTosh.add(productsPages.get(0), 1, 3, 1, 1);
            } else {
                Text text= new Text(" Products");
                centerGridPaneTosh.add(text, 1, 1, 1, 1);
                centerGridPaneTosh.add(productsPages.get(0), 1, 2, 1, 1);
            }
        } else {
            ImageView imageView = new ImageView(new Image("file:src/empty.png"));
            Text text = new Text("                             No product to show.");
            GridPane gridPane = new GridPane();
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            gridPane.add(imageView, 0, 0);
            gridPane.add(text, 0, 1,2,1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(400, Control.USE_COMPUTED_SIZE, 400, Priority.NEVER, HPos.RIGHT, false));
            gridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 200, Priority.NEVER, HPos.RIGHT, false));
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 32));
            centerGridPaneTosh.add(gridPane, 1, 1, 1, 1);
        }
    }

    private void createAddGridPane(GridPane gridPane, ArrayList<GridPane> gridPanes, String buttonStyle) {
        if (gridPanes.isEmpty()) {
            return;
        }
        ArrayList<GridPane> productsPages = new ArrayList<>();
        for (int j = 0; j < (gridPanes.size() / 4) + (gridPanes.size() % 4 == 0 ? 0 : 1); j++) {
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
            for (int i = j * 4; i < (j) * 4 + (j == ((gridPanes.size() / 4) + (gridPanes.size() % 4 == 0 ? 0 : 1) - 1) ? gridPanes.size() % 4 : 4); i++) {
                productsPages.get(j).add(gridPanes.get(i), 2 * ((i % 4) % 4) + 1, ((i % 4) / 4), 1, 1);
            }
        }
        ArrayList<Button> buttons = new ArrayList<>();
        System.out.println("AAAAAa              "+productsPages.size());
        for (int i = 0; i < productsPages.size(); i++) {
            buttons.add(new Button(Integer.toString(i + 1)));
            buttons.get(i).setStyle(buttonStyle);
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
                    for (Node child : centerGridPaneTosh.getChildren()) {
                        if (productsPages.contains(child)) {
                            centerGridPaneTosh.getChildren().remove(child);
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
                    centerGridPaneTosh.add(productsPages.get(j[0]), 1, 1);
                }
            });
        }
        GridPane buttons1 = new GridPane();
        buttons1.setHgap(3);
        for (int i = 0; i < buttons.size(); i++) {
            buttons1.add(buttons.get(i), i + 1, 0);
        }
        buttons1.getColumnConstraints().add(new ColumnConstraints(310 - (buttons.size() / 2) * 20, Control.USE_COMPUTED_SIZE, 310 - (buttons.size() / 2) * 20, Priority.NEVER, HPos.LEFT, false));
        System.out.println("Aaaaaaaaaa" + "      " + productsPages.size());
        Text text = new Text(" Adds");
        text.setFont(Font.loadFont("file:src/Bangers.ttf", 24));
        gridPane.add(text, 0, 0);
        gridPane.add(productsPages.get(0), 0, 1);

    }

    private void createProductsGridPanes(ArrayList<GridPane> gridPanes) {
        for (int kk = 0; kk < showProductsAfterFilterAndSort().size(); kk++) {
            Product product = showProductsAfterFilterAndSort().get(kk);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text();
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
            if (offerChecker) {
                text = new Text("   " + product.getProductName() + "\n" + "   " + product.getProductCost() + " $");
                Text text1 = new Text("   Cost after off: " + product.getCostAfterOff() + " $\n" + "   " + "Offer percent: " + product.getOffer().getAmount() + "%");
                Text text2 = new Text("   " + "Start time: \n" + product.getOffer().getStartTime().toString() + "\n"
                        + "   " + "End time: \n" + product.getOffer().getEndTime().toString());
                text1.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
                text2.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 12));
                gridPane.add(imageView, 0, 0, 1, 1);
                gridPane.add(text, 0, 1, 1, 1);
                gridPane.add(text1, 0, 2, 1, 1);
                gridPane.add(text2, 0, 3, 1, 1);
                gridPane.add(scoreGridPane, 0, 4, 1, 1);
            } else {
                text = new Text("   " + product.getProductName() + "\n" + "   " + product.getCostAfterOff() + " $");
                gridPane.add(imageView, 0, 0, 2, 1);
                gridPane.add(text, 0, 1, 1, 1);
                gridPane.add(scoreGridPane, 0, 2, 1, 1);
            }
            GridPane options = new GridPane();
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
        }
    }

    private void createAddGridPanes(ArrayList<GridPane> addGridPanes) {
        for (int i = 0; i < ProductController.getInstance().getAllCommercializedProduct().size(); i++) {
            Product product = ProductController.getInstance().getAllCommercializedProduct().get(i);
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(product.getImagePath()));
            Text text = new Text();
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
            text = new Text("   " + product.getProductName() + "\n" + "   " + product.getCostAfterOff() + " $");
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 1, 1);
            GridPane options = new GridPane();
            addGridPanes.add(gridPane);
            gridPane.setStyle("-fx-background-color: rgba(206, 186, 9, 0.62); -fx-effect: innershadow( gaussian ,rgba(231, 171, 206, 0.62) ,45,.25,0,10);-fx-background-radius: 20px;");
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
                    for (int i = 0; i < addGridPanes.size(); i++) {
                        if (addGridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                    for (int i = 0; i < addGridPanes.size(); i++) {
                        if (addGridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
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
                    for (int i = 0; i < addGridPanes.size(); i++) {
                        if (addGridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(showProductsAfterFilterAndSort().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
        }
    }

    protected void setUpGridPane() {
        Label label = new Label("        Pms.com");
        label.setStyle("-fx-font-weight: bold;");
        label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 28));
        upGridPane.getRowConstraints().add(new RowConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, VPos.CENTER, true));
        upGridPane.add(label, 0, 0);
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
        title.setStyle("-fx-font-weight: bold;");
        title.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 20));
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
        HashMap<String, String> categoryFeaturesForProduct = new HashMap<>();
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
                    gridPane.getRowConstraints().add(new RowConstraints(200, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.ALWAYS, HPos.CENTER, true));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(250, Control.USE_COMPUTED_SIZE, 250, Priority.NEVER, HPos.CENTER, true));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(45, Control.USE_COMPUTED_SIZE, 45, Priority.ALWAYS, HPos.CENTER, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(120, Control.USE_COMPUTED_SIZE, 120, Priority.ALWAYS, HPos.LEFT, false));
                    featuresGridPane.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, 90, Priority.ALWAYS, HPos.LEFT, false));
                    featuresGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
                    featuresGridPane.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.CENTER, true));
                    Scene scene1 = new Scene(gridPane, 320, 240);
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
        // userInfoGridPane.add(title,0,0);
        HBox hBox = new HBox();
        hBox.setMinWidth(230);
        HBox hBox1 = new HBox();
        hBox1.setMinWidth(250);
        //  userInfoGridPane.setGridLinesVisible(true);
        upGridPane.add(hBox1, 0, 0, 1, 1);
        // upGridPane.setGridLinesVisible(true);
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

}

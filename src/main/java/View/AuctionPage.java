package View;

import Controller.Client.AuctionController;
import Controller.Client.CategoryController;
import Controller.Client.ClientController;
import Controller.Client.ProductController;
import Models.Comment;
import Models.CommentStatus;
import Models.Product.Product;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AuctionPage extends Menu {
    private boolean isScored = false;
    private Product product;
    private GridPane productInfoGridPane = new GridPane();

    public AuctionPage(Stage stage) {
        super(stage);
        setScene();
    }

    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pageGridPane);
        scene.setRoot(scrollPane);
    }

    private void setCenterGridPane() {
        ProductController.getInstance().getAllProductsFromServer();
        product = ProductController.getInstance().
                getProductWithId(AuctionController.getInstance().getCurrentAuction().getProduct().getProductId());
        if (product == null) {
            ClientController.getInstance().back();
        }
        ImageView productImage = new ImageView(new Image(product.getImagePath()));
        ImageView productZoomedImage = new ImageView(new Image(product.getImagePath()));
        productZoomedImage.setTranslateX(250);
        productZoomedImage.setTranslateY(0);
        productZoomedImage.setFitHeight(400);
        productZoomedImage.setFitWidth(400);
        productZoomedImage.setVisible(false);
        productImage.setFitHeight(200);
        productImage.setFitWidth(200);
        productInfoGridPane.setVgap(10);
        productInfoGridPane.setHgap(20);
        productInfoGridPane.setMinWidth(850);
        productInfoGridPane.setStyle("-fx-background-color: #ECD5DC");
        HBox hbox = new HBox();
        HBox hbox2 = new HBox();
        hbox.setMinWidth(100);
        VBox vbox2 = new VBox();
        vbox2.setMinHeight(20);
        hbox2.setMinWidth(20);
        centerGridPane.setVgap(20);
        centerGridPane.setHgap(20);
        Text title = new Text("Auction Menu");
        Text productName = new Text(product.getProductName());
        productName.setFont(Font.loadFont("file:src/FredokaOne-Regular.ttf", 20));
        WebView attributes = new WebView();
        attributes.getEngine().loadContent(product.showAttributes());
        title.setStyle("-fx-font-size: 30;-fx-font-weight: bold ");
        ImageView userIcon = new ImageView(new Image("file:src/user_icon.png"));
        userIcon.setFitHeight(20);
        userIcon.setFitWidth(20);
        productInfoGridPane.add(vbox2, 0, 0);
        productInfoGridPane.add(hbox2, 0, 1);
        productInfoGridPane.add(productImage, 1, 1, 12, 13);
        Button addToCartButton = new Button("Add to Cart");
        TextField offer = new TextField();
        Text bestOffer = new Text("Best Offer:" + String.valueOf(AuctionController.getInstance().getCurrentAuction().getBestOffer()));
        offer.setPromptText("Offer");
        addToCartButton.setStyle("-fx-background-color: rgba(45, 156, 240, 1);-fx-font-size: 15;");
        addToCartButton.setMinWidth(200);
        addToCartButton.setMinHeight(30);
        addToCartButton.setTextFill(Color.WHITE);
        Button videoButton = new Button("Show Video");
        videoButton.setStyle("-fx-background-color: rgba(45, 156, 240, 1);-fx-font-size: 15;");
        videoButton.setMinWidth(200);
        videoButton.setMinHeight(30);
        videoButton.setTextFill(Color.WHITE);
        if (product.getVideoPath() == null || product.getVideoPath().equals("")) {
            videoButton.setDisable(true);
        }
        if (product.getNumberOfAvailableProducts() == 0 || !(ClientController.getInstance().getCurrentUser() == null || ClientController.getInstance().getCurrentUser() instanceof Customer)) {
            addToCartButton.setDisable(true);
        }
        productImage.setOnMouseEntered(e -> {
            productZoomedImage.setVisible(true);
        });
        productImage.setOnMouseExited(e -> {
            productZoomedImage.setVisible(false);
        });
        GridPane commentPane = new GridPane();
        commentPane.setVgap(5);
        for (int i = 0; i < product.getCommentList().size(); i++) {
            Comment comment = product.getCommentList().get(i);
            ImageView userImage = new ImageView(new Image(comment.getuserImagePath()));
            userImage.setFitWidth(50);
            userImage.setFitHeight(50);
            Text commentText = new Text();
            commentText.setStyle("-fx-font-size: 15 ");
            if (comment.isDidCustomerBuyProduct())
                commentText.setText(comment.getUsername() + "/bought\n" + comment.getTitle() + "\n" + comment.getContent());
            else
                commentText.setText(comment.getUsername() + "/not bought\n" + comment.getTitle() + "\n" + comment.getContent());
        }
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        CategoryController.getInstance().updateAllCategories();
        for (int kk = 0; kk < 3; kk++) {
            if (kk == CategoryController.getInstance().getCategoryWithName(product.getProductsCategory()).getAllProducts().size()) {
                break;
            }
            Product productSim = CategoryController.getInstance().getCategoryWithName(product.getProductsCategory()).getAllProducts().get(kk);
            if (productSim.getProductId().equals(product.getProductId())) {
                continue;
            }
            GridPane gridPane = new GridPane();
            ImageView imageView = new ImageView(new Image(productSim.getImagePath()));
            Text text = new Text("   " + productSim.getProductName() + "\n" + "   " + productSim.getCostAfterOff() + " $");
            Label label = new Label("   " + Double.toString(productSim.getAverageScore()));
            ImageView star = new ImageView(new Image("file:src/star.png"));
            imageView.setFitHeight(125);
            imageView.setFitWidth(125);
            star.setFitWidth(20);
            star.setFitHeight(20);
            GridPane scoreGridPane = new GridPane();
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.getColumnConstraints().add(new ColumnConstraints(15, Control.USE_COMPUTED_SIZE, 20, Priority.NEVER, HPos.LEFT, false));
            scoreGridPane.setHgap(2);
            scoreGridPane.add(label, 0, 0);
            scoreGridPane.add(star, 1, 0);
            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.add(text, 0, 1, 1, 1);
            gridPane.add(scoreGridPane, 0, 2, 1, 1);
            GridPane options = new GridPane();
            options.getColumnConstraints().add(new ColumnConstraints(117, Control.USE_COMPUTED_SIZE, 117, Priority.NEVER, HPos.RIGHT, false));
            options.getColumnConstraints().add(new ColumnConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, HPos.LEFT, false));
            options.getRowConstraints().add(new RowConstraints(30, Control.USE_COMPUTED_SIZE, 30, Priority.NEVER, VPos.TOP, false));
            options.setHgap(2);
            gridPane.add(options, 0, 3, 2, 1);
            gridPanes.add(gridPane);
            gridPane.setStyle("-fx-background-color: rgb(124,124,213);-fx-background-radius: 20px;");
            text.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            label.setFont(Font.loadFont("file:src/BalooBhai2-Bold.ttf", 14));
            gridPane.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.HAND);

                }
            });
            gridPane.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < gridPanes.size(); i++) {
                        if (gridPanes.get(i).equals(gridPane)) {
                            ClientController.getInstance().setCurrentProduct(((Seller) ClientController.getInstance().getCurrentUser()).getAllProducts().get(i));
                            new ProductMenu(stage).execute();
                        }
                    }
                }
            });
        }
        productInfoGridPane.add(bestOffer, 1, 14, 12, 1);
        productInfoGridPane.add(offer, 1, 15, 12, 1);
        productInfoGridPane.add(addToCartButton, 1, 16, 12, 1);
        productInfoGridPane.add(videoButton, 1, 17, 12, 1);
        productInfoGridPane.add(productName, 12, 1);
        productInfoGridPane.add(attributes, 12, 2, 5, 12);
        productInfoGridPane.add(commentPane, 12, 22);
        centerGridPane.add(title, 2, 0, 1, 4);
        centerGridPane.add(productInfoGridPane, 0, 3, 5, 5);
        Circle redCircle = new Circle();
        GridPane offGridPane = new GridPane();
        redCircle.setFill(Color.rgb(222, 0, 0));
        redCircle.setRadius(30);
        Text offPercent = new Text();
        productInfoGridPane.add(productZoomedImage, 0, 0, 20, 20);
        Text addCommentText = new Text("Add Comment");
        addCommentText.setFont(Font.loadFont("file:src/FredokaOne-Regular.ttf", 20));
        ImageView commentIcon = new ImageView(new Image("file:src/comment_icon.png"));
        commentIcon.setFitWidth(20);
        commentIcon.setFitHeight(20);
        commentIcon.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        commentIcon.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        videoButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        videoButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        addToCartButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        addToCartButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        addToCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (offer.getText() != null && checkStringIsNumberFormat(offer.getText())) {
                    if (Double.parseDouble(offer.getText().trim()) > AuctionController.getInstance().getCurrentAuction().getBestOffer()) {
                        if (ClientController.getInstance().getCurrentUser().getCredit() >= Double.parseDouble(offer.getText().trim())) {
                            AuctionController.getInstance().addOfferToList
                                    (ClientController.getInstance().getCurrentUser().getUsername()
                                            ,Double.parseDouble(offer.getText().trim()));
                        } else {
                            showMessage("You don't have enough money to offer this much.", MessageKind.ErrorWithoutBack);
                        }
                    } else {
                        showMessage("You can just enter number.\na number bigger than best price.", MessageKind.ErrorWithoutBack);
                    }
                } else {
                    showMessage("You can just enter number.\na number bigger than best price.", MessageKind.ErrorWithoutBack);
                }
            }
        });
        commentIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage popupwindow = new Stage();
                GridPane gridPane = new GridPane();
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
                gridPane.setStyle("-fx-background-color: rgba(255,145,200,0.85);");
                GridPane commentPane = new GridPane();
                gridPane.add(commentPane, 1, 1);
                Text titleText = new Text("Title:");
                Text contentText = new Text("Content:");
                TextField getTitle = new TextField();
                TextArea getContent = new TextArea();
                getTitle.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 110px;");
                getContent.setStyle("-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;");
                getContent.setMinHeight(100);
                getContent.setMaxWidth(300);

                Button addCommentButton = new Button("Add Comment");
                addCommentButton.setStyle("-fx-background-color: #E85D9E;");
                addCommentButton.setMinWidth(100);
                commentPane.setVgap(10);
                commentPane.setHgap(10);
                addCommentButton.setTextFill(Color.WHITE);
                commentPane.add(titleText, 0, 0);
                commentPane.add(contentText, 0, 1);
                commentPane.add(getTitle, 1, 0);
                commentPane.add(getContent, 1, 1, 1, 5);
                commentPane.add(addCommentButton, 1, 6);
                addCommentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (ClientController.getInstance().getCurrentUser() != null)
                            ProductController.getInstance().addComment(new Comment(product.getProductId(), CommentStatus.unChecked, product.didUserBuyThis(ClientController.getInstance().getCurrentUser().getUsername()), getTitle.getText(), getContent.getText(), ClientController.getInstance().getCurrentUser().getUsername(), ClientController.getInstance().getCurrentUser().getImagePath()));
                        else
                            ProductController.getInstance().addComment(new Comment(product.getProductId(), CommentStatus.unChecked, product.didUserBuyThis(""), getTitle.getText(), getContent.getText(), "guest", ""));
                        popupwindow.hide();
                    }
                });
                Scene scene1 = new Scene(gridPane, 400, 300);
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.initStyle(StageStyle.UNDECORATED);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });
        videoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage popupwindow = new Stage();
                GridPane gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color: Blue");
                Media media = new Media(product.getVideoPath());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                Button button = new Button("X");
                button.setStyle("-fx-background-color: rgba(236, 213, 220, 0.85);-fx-background-radius: 3,2,2,2;-fx-font-size: 12px;-fx-background-radius: 30; -fx-pref-height: 18px;-fx-pref-width: 25px; -fx-padding: 3,3,3,3;-fx-font-weight: bold;-fx-text-fill: Red");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.stop();
                        popupwindow.hide();
                        scene.setFill(null);
                    }
                });
                gridPane.add(button, 0, 0);
                gridPane.add(new Text(""), 1, 0);
                gridPane.setStyle("-fx-background-color: rgba(255,145,200,0.85);");
                GridPane commentPane = new GridPane();
                gridPane.add(commentPane, 1, 1);
                commentPane.setVgap(10);
                commentPane.setHgap(5);
                mediaPlayer.setAutoPlay(true);
                mediaView.setFitHeight(380);
                mediaView.setFitWidth(280);
                ImageView playButton = new ImageView(new Image("file:src/play.png"));
                ImageView pauseButton = new ImageView(new Image("file:src/pause.png"));
                ImageView stopButton = new ImageView(new Image("file:src/stop.png"));
                playButton.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.HAND);

                    }
                });
                playButton.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                });
                playButton.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        mediaPlayer.play();
                    }
                });
                pauseButton.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.HAND);

                    }
                });
                pauseButton.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                });
                pauseButton.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        mediaPlayer.pause();
                    }
                });
                stopButton.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.HAND);

                    }
                });
                stopButton.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                });
                stopButton.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        mediaPlayer.stop();
                    }
                });
                playButton.setFitHeight(20);
                pauseButton.setFitHeight(20);
                stopButton.setFitHeight(20);
                playButton.setFitWidth(20);
                pauseButton.setFitWidth(20);
                stopButton.setFitWidth(20);
                commentPane.add(mediaView, 0, 0, 4, 1);
                commentPane.add(playButton, 0, 1);
                commentPane.add(pauseButton, 2, 1);
                commentPane.add(stopButton, 3, 1);
                Scene scene1 = new Scene(gridPane, 400, 300);
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.initStyle(StageStyle.UNDECORATED);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });
        if (product.getOffer() != null) {
            offPercent.setText("   " + ((int) product.getOffer().getAmount()) + "%");
            offPercent.setFont(Font.loadFont("file:src/Bangers.ttf", 20));
            offPercent.setFill(Color.WHITE);
            offPercent.setStyle("-fx-font-weight: bold;-fx-font-size: 20");
            offGridPane.add(redCircle, 0, 0);
            offGridPane.add(offPercent, 0, 0);
            offGridPane.translateZProperty().set(100);
            productInfoGridPane.add(offGridPane, 8, 12);
        }
    }

    private boolean checkStringIsNumberFormat(String string) {
        if (Pattern.matches("\\d+", string) || Pattern.matches("\\d+\\.\\d+", string)) {
            return true;
        } else {
            return false;
        }
    }
}

package View;

import Controller.Client.CartController;
import Controller.Client.ClientController;
import Models.Offer;
import Models.Product.Product;
import Models.Score;
import Models.UserAccount.Seller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;

public class ProductMenu extends Menu{
    private boolean isScored=false;
    private Image goldStar=new Image("file:src/gold_star.png");
    private Rectangle fiveStarBar=new Rectangle(),fourStarBar=new Rectangle(),threeStarBar=new Rectangle(),twoStarBar=new Rectangle(),oneStarBar=new Rectangle();
    Product product;
    Text avarageScore=new Text();
    Text numberOfScores=new Text();
    private GridPane productInfoGridPane=new GridPane();
    public ProductMenu(Stage stage) {
        super(stage);
        setScene();
    }
    public void setScene() {
        setPageGridPain();
        setUpGridPane();
        setMenuBarGridPane();
        setCenterGridPane();
        bottomGridPane.getRowConstraints().add(new RowConstraints(100, Control.USE_COMPUTED_SIZE, 100, Priority.NEVER, VPos.CENTER, true));
        scene.setRoot(pageGridPane);
    }
    private void setCenterGridPane() {

      product= ClientController.getInstance().getCurrentProduct();
        HashMap<String,String> features=new HashMap<>();
        features.put("Color","Yellow");
        features.put("Mass","5 kg");
//        product=new Product("Mive-TareBar","@p10003","Banana",new Seller("Mamooti","123456","Mahmood","ahmadi","mamooti@gmail.com","09124569966",1000,"dolat",true),600,"Fruits","eat the banana and fell the power",50,features);
        Date date=new Date();
        date.setMonth(8);
        product.setImagePath("file:C:\\Users\\USER\\Desktop\\moz.jpg");
//        Offer offer=new Offer(35,"Mamooti",null,new Date(),date);
//        product.setOffer(offer);
//        product.addScore(new Score("mamal",product.getProductId(),1));
//        product.addScore(new Score("mamal",product.getProductId(),1));
//        product.addScore(new Score("mamal",product.getProductId(),3));
//        product.addScore(new Score("mamal",product.getProductId(),5));
//        product.addScore(new Score("mamal",product.getProductId(),2));
//        product.addScore(new Score("mamal",product.getProductId(),3));

        ImageView productImage=new ImageView(new Image(product.getImagePath()));
        productImage.setFitHeight(200);
        productImage.setFitWidth(200);
        productInfoGridPane.setVgap(10);
        productInfoGridPane.setHgap(20);
        productInfoGridPane.setMinWidth(850);
      //  productInfoGridPane.setMinHeight(500);
        productInfoGridPane.setStyle("-fx-background-color: #ECD5DC");
  //      productInfoGridPane.setGridLinesVisible(true);
 //       centerGridPane.setGridLinesVisible(true);

        HBox hbox=new HBox();
        HBox hbox2=new HBox();
        hbox.setMinWidth(100);
        VBox vbox2=new VBox();
        vbox2.setMinHeight(20);
        hbox2.setMinWidth(20);
        centerGridPane.setVgap(20);
        centerGridPane.setHgap(20);
        Text title=new Text("Product Menu");
        Text productName=new Text(product.getProductName());
        productName.setFont(Font.loadFont("file:src/FredokaOne-Regular.ttf",20));
        WebView attributes=new WebView();
        attributes.getEngine().loadContent(product.showAttributes());
        title.setStyle("-fx-font-size: 30;-fx-font-weight: bold ");
        GridPane rateGridPane=new GridPane();
        Text rating=new Text("Rating:");
        rating.setFont(Font.loadFont("file:src/FredokaOne-Regular.ttf",16));
        rateGridPane.add(rating,0,0,4,1);
        ImageView star1=new ImageView(new Image("file:src/gray_star.png"));
        ImageView star2=new ImageView(new Image("file:src/gray_star.png"));
        ImageView star3=new ImageView(new Image("file:src/gray_star.png"));
        ImageView star4=new ImageView(new Image("file:src/gray_star.png"));
        ImageView star5=new ImageView(new Image("file:src/gray_star.png"));
        star1.setFitWidth(20);
        star2.setFitWidth(20);
        star3.setFitWidth(20);
        star4.setFitWidth(20);
        star5.setFitWidth(20);
        star1.setFitHeight(20);
        star2.setFitHeight(20);
        star3.setFitHeight(20);
        star4.setFitHeight(20);
        star5.setFitHeight(20);
        avarageScore.setStyle("-fx-font-size: 40;-fx-font-weight: bold");
        ImageView userIcon=new ImageView(new Image("file:src/user_icon.png"));
        userIcon.setFitHeight(20);
        userIcon.setFitWidth(20);
        numberOfScores.setStyle("-fx-font-size: 20");
     //   rateGridPane.setGridLinesVisible(true);
        rateGridPane.add(avarageScore,0,1,9,3);
        rateGridPane.add(userIcon,0,4,2,1);
        rateGridPane.add(numberOfScores,2,4,3,1);
        rateGridPane.add(star1,5,0);
        rateGridPane.add(star2,6,0);
        rateGridPane.add(star3,7,0);
        rateGridPane.add(star4,8,0);
        rateGridPane.add(star5,9,0);
        productInfoGridPane.add(vbox2,0,0);
        productInfoGridPane.add(hbox2,0,1);
        productInfoGridPane.add(productImage,1,1,12,13);
        Button addToCartButton=new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: rgba(45, 156, 240, 1);-fx-font-size: 15;");
        addToCartButton.setMinWidth(200);
        addToCartButton.setMinHeight(30);
        addToCartButton.setTextFill(Color.WHITE);
        productInfoGridPane.add(addToCartButton,1,14,12,5);
        productInfoGridPane.add(productName,10,1);
        productInfoGridPane.add(attributes,10,2,5,12);
        productInfoGridPane.add(rateGridPane,10,13,8,8);
        centerGridPane.add(title,2,0,1,4);
        centerGridPane.add(productInfoGridPane,0,3,5,5);
        Circle redCircle=new Circle();
        GridPane offGridPane=new GridPane();
        redCircle.setFill(Color.rgb(222,0,0));
        redCircle.setRadius(30);
        Text offPercent=new Text();
        setLengthOfStarBars();
        rateGridPane.setVgap(10);
        rateGridPane.setHgap(5);
        rateGridPane.add(fiveStarBar,6,1,10,1);
        rateGridPane.add(fourStarBar,6,2,10,1);
        rateGridPane.add(threeStarBar,6,3,10,1);
        rateGridPane.add(twoStarBar,6,4,10,1);
        rateGridPane.add(oneStarBar,6,5,10,1);
        rateGridPane.add(new Text("5"),5,1);
        rateGridPane.add(new Text("4"),5,2);
        rateGridPane.add(new Text("3"),5,3);
        rateGridPane.add(new Text("2"),5,4);
        rateGridPane.add(new Text("1"),5,5);
        GridPane commentGridPane = new GridPane();
        Text addCommentText=new Text("Add Comment");
        addCommentText.setFont(Font.loadFont("file:src/FredokaOne-Regular.ttf",20));
        commentGridPane.add(addCommentText,0,0);
        ImageView commentIcon=new ImageView(new Image("file:src/comment_icon.png"));
        commentGridPane.add(commentIcon,1,0);
        commentIcon.setFitWidth(20);
        commentIcon.setFitHeight(20);
        Button showCommentsButton =new Button("Show Comments");
        showCommentsButton.setStyle("-fx-background-color: #E85D9E;");
        showCommentsButton.setMinWidth(100);
        showCommentsButton.setTextFill(Color.WHITE);
        rateGridPane.add(showCommentsButton,10,0,5,1);
        addToCartButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        addToCartButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        showCommentsButton.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        showCommentsButton.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        star1.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        star1.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        star2.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        star2.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        star3.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        star3.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        star4.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        star4.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        star5.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        star5.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
        addToCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CartController.getInstance().getCurrentCart().addProduct(product);
            }
        });
        showCommentsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                ImageView seller = new ImageView(new Image("file:src/seller.png"));
                ImageView customer = new ImageView(new Image("file:src/customer.png"));
                customer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new RegisterMenu(stage).execute();
                    }
                });
                seller.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new SellerRegisterMenu(stage).execute();
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
                Label customer1 = new Label("Customer");
                customer1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popupwindow.hide();
                        new RegisterMenu(stage).execute();
                    }
                });

                Label seller1 = new Label("Seller");
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
        star1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isScored) {
                    star1.setImage(goldStar);
                    product.addScore(new Score("mamal",product.getProductId(),1));
                    setLengthOfStarBars();
                    isScored=true;
                }
            }
        });
        star2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isScored) {
                    star1.setImage(goldStar);
                    star2.setImage(goldStar);
                    product.addScore(new Score("mamal",product.getProductId(),2));
                    setLengthOfStarBars();
                    isScored=true;
                }
            }
        });
        star3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isScored) {
                    star1.setImage(goldStar);
                    star2.setImage(goldStar);
                    star3.setImage(goldStar);
                    product.addScore(new Score("mamal",product.getProductId(),3));
                    setLengthOfStarBars();
                    isScored=true;
                }
            }
        });
        star4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isScored) {
                    star1.setImage(goldStar);
                    star2.setImage(goldStar);
                    star3.setImage(goldStar);
                    star4.setImage(goldStar);
                    product.addScore(new Score("mamal",product.getProductId(),4));
                    setLengthOfStarBars();
                    isScored=true;
                }
            }
        });
        star5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isScored) {
                    star1.setImage(goldStar);
                    star2.setImage(goldStar);
                    star3.setImage(goldStar);
                    star4.setImage(goldStar);
                    star5.setImage(goldStar);
                    product.addScore(new Score("mamal",product.getProductId(),5));
                    setLengthOfStarBars();
                    isScored=true;
                }
            }
        });

        if(product.getOffer()!=null){
            offPercent.setText("   "+((int)product.getOffer().getAmount())+"%");
            offPercent.setFont(Font.loadFont("file:src/Bangers.ttf",20));
            offPercent.setFill(Color.WHITE);
            offPercent.setStyle("-fx-font-weight: bold;-fx-font-size: 20");
            offGridPane.add(redCircle,0,0);
            offGridPane.add(offPercent,0,0);
            offGridPane.translateZProperty().set(100);
            productInfoGridPane.add(offGridPane,8,12);
        }
    }
    private void setLengthOfStarBars(){
        double oneStar=0,twoStar=0,threeStar=0,fourStar=0,fiveStar=0;
        for (Score score : product.getAllScores()) {
            if(score.getRate()==1)
                oneStar++;
            else if(score.getRate()==2)
                twoStar++;
            else if(score.getRate()==3)
                threeStar++;
            else if(score.getRate()==4)
                fourStar++;
            else if(score.getRate()==5)
                fiveStar++;
        }
        numberOfScores.setText(Integer.valueOf(product.getAllScores().size()).toString());
        avarageScore.setText(Double.valueOf(round(product.getAverageScore())).toString());
        double sum=oneStar+twoStar+threeStar+fourStar+fiveStar;
        oneStarBar.setWidth((oneStar/sum)*400);
        twoStarBar.setWidth((twoStar/sum)*400);
        threeStarBar.setWidth((threeStar/sum)*400);
        fourStarBar.setWidth((fourStar/sum)*400);
        fiveStarBar.setWidth((fiveStar/sum)*400);
        oneStarBar.setHeight(10);
        twoStarBar.setHeight(10);
        threeStarBar.setHeight(10);
        fourStarBar.setHeight(10);
        fiveStarBar.setHeight(10);
        fiveStarBar.setFill(Color.rgb(0,150,0));
        fourStarBar.setFill(Color.rgb(100,250,100));
        threeStarBar.setFill(Color.rgb(255,255,0));
        twoStarBar.setFill(Color.rgb(255,100,0));
        oneStarBar.setFill(Color.rgb(255,0,0));
    }
    private double round(double value){
        value = value*10;
        value = Math.round(value);
        value = value /10;
        return value;

    }
}

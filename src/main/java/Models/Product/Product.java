package Models.Product;

import Models.Comment;
import Models.Offer;
import Models.Score;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    String productId;
    ProductStatus productStatus;
    String productName;
    String productCompany;
    Seller seller;
    ArrayList<Score> allScores;
    double productCost, costAfterOff;
    Category productsCategory;
    String describtion;
    ArrayList<Comment> commentList;
    int numberOfAvailableProducts;
    HashMap<String, ArrayList<String>> featuresOfCategoryThatHas;
    ArrayList<Customer> allBuyers;
    Offer offer;

    public Product(String productId, ProductStatus productStatus, String productName, Seller seller, double productCost, Category productsCategory, String describtion, int numberOfAvailableProducts, HashMap<String, ArrayList<String>> featuresOfCategoryThatHas) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.productName = productName;
        this.seller = seller;
        this.productCost = productCost;
        this.productsCategory = productsCategory;
        this.describtion = describtion;
        this.numberOfAvailableProducts = numberOfAvailableProducts;
        this.featuresOfCategoryThatHas = featuresOfCategoryThatHas;
    }

    public void addScore(Score score) {
        allScores.add(score);
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public double getAverageScore() {
        double averageScore = 0;
        for (Score score : allScores) {
            averageScore += score.getRate();
        }
        averageScore /= allScores.size();
        return averageScore;
    }

    public double getCostAfterOff() {
        if (offer != null) {
            costAfterOff = this.productCost * ((double) 100 - offer.getAmount()) / (double) 100;
        }
        return this.productCost;
    }

    public String viewProduct() {
        String projectInformation = "";
        projectInformation += this.productName + "\\*\\";
        projectInformation += this.describtion + "\\*\\";
        projectInformation += this.productCost + "\\*\\";
        projectInformation += this.productsCategory.getName() + "\\*\\";
        projectInformation += this.seller.getUsername() + "\\*\\";
        projectInformation += this.getCostAfterOff() + "\\*\\";
        projectInformation += this.getAverageScore() + "\\*\\";

        return projectInformation;

    }

    public ArrayList<String> getComments() {
        if (commentList != null) {
            ArrayList<String> allCommentsInStringForm = new ArrayList<>();
            for (Comment comment : commentList) {
                allCommentsInStringForm.add(comment.toString());
            }
            return allCommentsInStringForm;
        }
        return null;
    }

    public String getProductId() {
        return productId;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public Seller getSeller() {
        return seller;
    }

    public double getProductCost() {
        return productCost;
    }

    public Category getProductsCategory() {
        return productsCategory;
    }

    public String getDescribtion() {
        return describtion;
    }

    public int getNumberOfAvailableProducts() {
        return numberOfAvailableProducts;
    }

    public HashMap<String, ArrayList<String>> getFeaturesOfCategoryThatHas() {
        return featuresOfCategoryThatHas;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public void setProductsCategory(Category productsCategory) {
        this.productsCategory = productsCategory;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setNumberOfAvailableProducts(int numberOfAvailableProducts) {
        this.numberOfAvailableProducts = numberOfAvailableProducts;
    }

    public void addFeaturesOfCategoryThatHas(String feature, String featursType) {
        if (!this.featuresOfCategoryThatHas.containsKey(feature)) {
            ArrayList<String> featuresTypes=new ArrayList<>();
            featuresTypes.add(featursType);
            this.featuresOfCategoryThatHas.put(feature, featuresTypes);
        } else {
            if (!this.featuresOfCategoryThatHas.get(feature).contains(featursType)) {
                this.featuresOfCategoryThatHas.get(feature).add(featursType);
            }
        }
    }

    public void deleteFeaturesOfCategoryThatHas(String feature, String featursType) {
        if (this.featuresOfCategoryThatHas.containsKey(feature)) {
            if (this.featuresOfCategoryThatHas.get(feature).contains(featursType)) {
                this.featuresOfCategoryThatHas.get(feature).remove(featursType);
            }
        }
    }

    public void addToAllBuyers(Customer buyer) {
        this.allBuyers.add(buyer);
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
        costAfterOff=((100-offer.getAmount())/100)*productCost;
    }
}

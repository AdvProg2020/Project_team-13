package Models.Product;

import Controller.Client.ClientController;
import Models.Comment;
import Models.Offer;
import Models.Score;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Product {

    private boolean existInOfferRegistered;
    private String productId;
    private ProductStatus productStatus;
    private String productName;
    private String productCompany;
    private String sellerUsername;
    private ArrayList<Score> allScores;
    private double productCost, costAfterOff;
    private String productsCategory;
    private String description;
    private ArrayList<Comment> commentList;
    private int numberOfAvailableProducts;
    private HashMap<String, String> featuresOfCategoryThatHas;
    private ArrayList<Customer> allBuyers = new ArrayList<>();
    private Offer offer;
    private String imagePath="";

    public Product(String productCompany, String productId, String productName, Seller seller, double productCost, String productsCategory, String description, int numberOfAvailableProducts, HashMap<String, String> featuresOfCategoryThatHas) {
        this.productCompany = productCompany;
        this.productId = productId;
        this.productStatus = ProductStatus.inCreatingProgress;
        this.productName = productName;
        this.sellerUsername = seller.getUsername();
        this.productCost = productCost;
        this.costAfterOff = productCost;
        this.productsCategory = productsCategory;
        this.description = description;
        this.numberOfAvailableProducts = numberOfAvailableProducts;
        this.featuresOfCategoryThatHas = featuresOfCategoryThatHas;
    }

    public Product(Product product) {
        this.productCompany = product.productCompany;
        this.productId = product.productId;
        this.productStatus = product.productStatus;
        this.productName = product.productName;
        this.sellerUsername = product.getSeller();
        this.productCost = product.productCost;
        this.costAfterOff = product.productCost;
        this.productsCategory = product.productsCategory;
        this.description = product.description;
        this.numberOfAvailableProducts = product.numberOfAvailableProducts;
        this.featuresOfCategoryThatHas = (HashMap<String, String>) (product.featuresOfCategoryThatHas).clone();
    }

    public ArrayList<Customer> getAllBuyers() {
        return allBuyers;
    }

    public void setCostAfterOff(double costAfterOff) {
        this.costAfterOff = costAfterOff;
    }
    public String getImagePath() {
        if(imagePath.isEmpty()) {
            return "file:src/product_icon.png";
        }
        return imagePath;
    }
    public void setAllBuyers(ArrayList<Customer> allBuyers) {
        this.allBuyers = allBuyers;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }

    public void addScore(Score score) {
        if(allScores==null){
            allScores=new ArrayList<>();
        }
        allScores.add(score);
    }

    public void addComment(Comment comment) {
        if(commentList==null){
            commentList=new ArrayList<>();
        }
        this.commentList.add(comment);
    }

    public boolean isExistInOfferRegistered() {
        return existInOfferRegistered;
    }

    public void setExistInOfferRegistered(boolean existInOfferRegistered) {
        this.existInOfferRegistered = existInOfferRegistered;
    }

    public double getAverageScore() {
        if (allScores != null) {
            double averageScore = 0;
            for (Score score : allScores) {
                averageScore += score.getRate();
            }
            averageScore /= allScores.size();
            return averageScore;
        }
        return 0.0;
    }

    public Offer getOffer() {
        return offer;
    }

    public double getCostAfterOff() {
        if (offer != null) {
            costAfterOff = this.productCost * ((double) 100 - offer.getAmount()) / (double) 100;
        }
        return this.costAfterOff;
    }


    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public String viewProduct() {
        String projectInformation = "";
        projectInformation += this.productName + "\n";
        projectInformation += this.description + "\n";
        projectInformation += this.productCost + "\n";
        projectInformation += this.productId + "\n";
        projectInformation += this.productsCategory + "\n";
        projectInformation += this.sellerUsername + "\n";
        projectInformation += this.getCostAfterOff() + "\n";
        projectInformation += this.getAverageScore() + "\n\n";

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

    public String getSeller() {
        return sellerUsername;
    }

    public double getProductCost() {
        return productCost;
    }

    public String getProductsCategory() {
        return productsCategory;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfAvailableProducts() {
        return numberOfAvailableProducts;
    }

    public HashMap<String, String> getFeaturesOfCategoryThatHas() {
        return featuresOfCategoryThatHas;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void addScore(String customerID,int rate){
        allScores.add(new Score(customerID,productId,rate));
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public void setProductsCategory(String productsCategory) {
        this.productsCategory = productsCategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberOfAvailableProducts(int numberOfAvailableProducts) {
        this.numberOfAvailableProducts = numberOfAvailableProducts;
    }

    public void addFeaturesOfCategoryThatHas(String feature, String featureDetail) {
        if (!this.featuresOfCategoryThatHas.containsKey(feature)) {
            this.featuresOfCategoryThatHas.put(feature, featureDetail);
        }
    }

    public void deleteFeaturesOfCategoryThatHas(String feature) {
        if (!this.featuresOfCategoryThatHas.containsKey(feature)) {
            this.featuresOfCategoryThatHas.remove(feature);
        }
    }



    public String viewAllBuyers(){
        String allBuyer="";
        for (Customer buyer : allBuyers) {
            allBuyer+=buyer.getUsername()+"\n";
        }
        return allBuyer;
    }
    public void addToAllBuyers(Customer buyer) {
        if (allBuyers == null) {
            allBuyers = new ArrayList<>();
        }
        this.allBuyers.add(buyer);
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
        if (offer != null) {
            costAfterOff = ((100 - offer.getAmount()) / 100) * productCost;
        }
    }



    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productStatus=" + productStatus +
                ", productName='" + productName + '\'' +
                ", productCompany='" + productCompany + '\'' +
                ", seller=" + sellerUsername +
                ", allScores=" + allScores +
                ", productCost=" + productCost +
                ", costAfterOff=" + costAfterOff +
                ", productsCategory='" + productsCategory + '\'' +
                ", description='" + description + '\'' +
                ", commentList=" + commentList +
                ", numberOfAvailableProducts=" + numberOfAvailableProducts +
                ", featuresOfCategoryThatHas=" + featuresOfCategoryThatHas +
                ", allBuyers=" + allBuyers +
                '}';
    }

    public String productInfoFor() {
        return "Product{" +
                "productId='" + productId + "\n" +
                "productStatus= " + productStatus +"\n"+
                "productName= " + productName + "\n" +
                "productCompany= " + productCompany + "\n" +
                "productCost= " + productCost + "\n" +
                "costAfterOff= " + costAfterOff +"\n" +
                "productsCategory= " + productsCategory + "\n" +
                "description= " + description + "\n" +
                "numberOfAvailableProducts= " + numberOfAvailableProducts;
    }

    public void showDigest() {
        String digest = "";
        digest += description + "\n";
        digest += productCost + "\n";
        if (offer != null)
            digest += offer.getAmount() + "%\n";
        digest += productsCategory + "%\n";
        digest += sellerUsername + "\n";
        digest += getAverageScore() + "\n";
     //   ClientController.getInstance().getCurrentMenu().showMessage(digest);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String showAttributes() {
        String attributes = "";
        attributes += "<html style=\"background-color: #ECD5DC;\">Company: "+ getProductCompany() + "<br>";
        attributes +="Seller: "+ getSeller() + "<br>";
        if(getOffer()!=null) {
            attributes += "Cost: <del>"+getProductCost()+ "</del>$<font color='red'> "+costAfterOff + "$</font></html><br>";
            attributes += "Offs remaning time: <html><font color='red'>"+new Date(getOffer().getEndTime().getTime()- new Date().getTime()).getDay() + "</font><br>";
        }else {
            attributes += "Cost: "+getProductCost()+ "$<br>";

        }
        attributes +="Category: "+ getProductsCategory() + "<br>";
        for (String feature : featuresOfCategoryThatHas.keySet()) {
            attributes += feature + ": " + featuresOfCategoryThatHas.get(feature) + "<br>";
        }
        attributes += "ِDescriptions: "+getDescription()+"<br></html>";
        return attributes;
      //  ClientController.getInstance().getCurrentMenu().showMessage(attributes);
    }

    public void setFeaturesOfCategoryThatHas(HashMap<String, String> featuresOfCategoryThatHas) {
        this.featuresOfCategoryThatHas = featuresOfCategoryThatHas;
    }
}

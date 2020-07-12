package Models.Product;

import Models.Comment;
import Models.Offer;
import Models.Score;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;

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
    private ArrayList<Offer> offers = new ArrayList<>();
    private String imagePath = "", videoPath = "";

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
        this.costAfterOff = product.costAfterOff;
        this.productsCategory = product.productsCategory;
        this.description = product.description;
        this.videoPath = "";
        this.numberOfAvailableProducts = product.numberOfAvailableProducts;
        this.featuresOfCategoryThatHas = (HashMap<String, String>) (product.featuresOfCategoryThatHas).clone();
    }

    public ArrayList<Customer> getAllBuyers() {
        return allBuyers;
    }

    public void setCostAfterOff(double costAfterOff) {
        this.costAfterOff = costAfterOff;
    }

    public boolean didUserBuyThis(String username) {
        for (Customer buyer : allBuyers) {
            if (buyer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Comment> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public String getImagePath() {
        if (imagePath != null && !imagePath.isEmpty()) {
            if (numberOfAvailableProducts > 0) {
                return imagePath;
            } else {
                return "file:src/sold_out.png";
            }
        }else if(numberOfAvailableProducts ==0) {
            return "file:src/sold_out.png";
        }
        return "file:src/product_icon.png";
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setAllBuyers(ArrayList<Customer> allBuyers) {
        this.allBuyers = allBuyers;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ArrayList<Score> getAllScores() {
        if (allScores == null) {
            allScores = new ArrayList<>();
        }
        return allScores;
    }

    public int getPointThatBeforeRated(String username) {
        if (allScores == null) {
            allScores = new ArrayList<>();
        }
        for (Score score : allScores) {
            if (score.getCustomerID().equals(username)) {
                return score.getRate();
            }
        }
        return 0;
    }

    public void addScore(Score score) {
        if (allScores == null) {
            allScores = new ArrayList<>();
        }
        allScores.add(score);
    }

    public void addComment(Comment comment) {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        this.commentList.add(comment);
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
        if (offers == null || offers.isEmpty()) {
            return null;
        } else {
            ArrayList<Offer> offers = new ArrayList<>();
            for (Offer offer : this.offers) {
                if (offer.getStartTime().before(new Date()) && offer.getEndTime().after(new Date())) {
                    offers.add(offer);
                }
            }
            Offer offer = null;
            int i = 0;
            for (Offer offer1 : offers) {
                if (i == 0) {
                    offer = offer1;
                    i++;
                } else if (offer1.getAmount() > offer.getAmount()) {
                    offer = offer1;
                }
            }
            return offer;
        }
    }

    public double getCostAfterOff() {
        if (getOffer() != null) {
            costAfterOff = this.productCost * ((double) 100 - getOffer().getAmount()) / (double) 100;
        }
        return this.costAfterOff;
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

    public void addScore(String customerID, int rate) {
        allScores.add(new Score(customerID, productId, rate));
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


    public void deleteFeaturesOfCategoryThatHas(String feature) {
        if (!this.featuresOfCategoryThatHas.containsKey(feature)) {
            this.featuresOfCategoryThatHas.remove(feature);
        }
    }

    public void addToAllBuyers(Customer buyer) {
        if (allBuyers == null) {
            allBuyers = new ArrayList<>();
        }
        this.allBuyers.add(buyer);
    }

    public void setOffer(Offer offer) {
        if (offers == null) {
            offers = new ArrayList<>();
        }
        if (offer != null) {
            offers.add(offer);
        }
        if (getOffer() != null) {
            costAfterOff = ((100 - getOffer().getAmount()) / 100) * productCost;
        }
    }


    @Override
    public String toString() {
        return "productId: " + productId + '\n' +
                "productStatus: " + productStatus +
                "productName: " + productName + '\n' +
                "productCompany: '" + productCompany + '\n' +
                "seller: " + sellerUsername + '\n' +
                "allScores: " + allScores + '\n' +
                "productCost: " + productCost + '\n' +
                "costAfterOff: " + costAfterOff + '\n' +
                "productsCategory: " + productsCategory + '\n' +
                "description: " + description + '\n' +
                "commentList: " + commentList + '\n' +
                "numberOfAvailableProducts: " + numberOfAvailableProducts + '\n' +
                "featuresOfCategoryThatHas: " + featuresOfCategoryThatHas + '\n' +
                "allBuyers: " + allBuyers + '\n' +
                "--------------------";
    }

    public String productInfoFor() {
        return "Product{" +
                "productId='" + productId + "\n" +
                "productStatus= " + productStatus + "\n" +
                "productName= " + productName + "\n" +
                "productCompany= " + productCompany + "\n" +
                "productCost= " + productCost + "\n" +
                "costAfterOff= " + costAfterOff + "\n" +
                "productsCategory= " + productsCategory + "\n" +
                "description= " + description + "\n" +
                "numberOfAvailableProducts= " + numberOfAvailableProducts;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String showAttributes() {
        String attributes = "";
        attributes += "<html style=\"background-color: #ECD5DC;\">Company: " + getProductCompany() + "<br>";
        attributes += "Seller: " + getSeller() + "<br>";
        if (getOffer() != null) {
            attributes += "Cost: <del>" + getProductCost() + "</del>$<font color='red'> " + costAfterOff + "$</font></html><br>";
            attributes += "Offs remaning time: <html><font color='red'>" + new Date(getOffer().getEndTime().getTime() - new Date().getTime()).getDay() + "</font><br>";
        } else {
            attributes += "Cost: " + getProductCost() + "$<br>";

        }
        attributes += "Category: " + getProductsCategory() + "<br>";
        attributes += "Available Numbers: " + getNumberOfAvailableProducts() + "<br>";
        attributes += "Status: " + getProductStatus() + "<br>";
        for (String feature : featuresOfCategoryThatHas.keySet()) {
            attributes += feature + ": " + featuresOfCategoryThatHas.get(feature) + "<br>";
        }
        attributes += "ِDescriptions: " + getDescription() + "<br></html>";
        return attributes;
    }

}

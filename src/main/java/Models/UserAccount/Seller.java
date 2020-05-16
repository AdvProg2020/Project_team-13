package Models.UserAccount;

import Models.Log;
import Models.Offer;
import Models.Product.Product;
import Models.Request;

import java.util.ArrayList;

public class Seller extends UserAccount {
    private String companyName;
    private boolean isAccepted;
    private ArrayList<Product> allProducts;
    private ArrayList<Offer> allOffer;
    private ArrayList<Request> allRequests;

    public Seller(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit, String companyName, boolean isAccepted) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.companyName = companyName;
        this.isAccepted = isAccepted;
        this.type = "@Seller";
        allProducts = new ArrayList<>();
    }

    public void addProduct(Product product) {

        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product products : allProducts) {
                if (products.getProductId().equals(product.getProductId())) {
                    return;
                }
            }
            allProducts.add(product);
        } else {
            allProducts = new ArrayList<>();
            allProducts.add(product);
        }
    }

    public void addOffer(Offer offer){
        if (allOffer != null && !allOffer.isEmpty()) {
            for (Offer offers : allOffer) {
                if (offers.getOfferId().equals(offer.getOfferId())) {
                    return;
                }
            }
        } else {
            allOffer = new ArrayList<>();
        }
        allOffer.add(offer);
        for (Product product : offer.getProducts()) {
            product.setOffer(offer);
        }
    }

    public void removeProduct(String productId) {
        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if(product.getProductId().equals(productId)) {
                    allProducts.remove(product);
                    break;
                }
            }
        }
    }

    public void editOffer(Offer newOffer){
        for (Offer offer : allOffer) {
            if(offer.getOfferId().equals(newOffer.getOfferId())){
                allOffer.set(allOffer.indexOf(offer), newOffer);
                break;
            }
        }
        for (Product product : newOffer.getProducts()) {
             product.setOffer(newOffer);
        }
    }

    public boolean productExistsInOtherOffer(String productId) {
        if (!hasAnyOffer()) {
            return false;
        }
        for (Offer offer : allOffer) {
            for (Product product : offer.getProducts()) {
                if (product.getProductId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ArrayList<Offer> getAllOffer() {
        return allOffer;
    }

    public void setAllOffer(ArrayList<Offer> allOffer) {
        this.allOffer = allOffer;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += this.username + "\n";
        personalInfo += this.firstName + "\n";
        personalInfo += this.lastName + "\n";
        personalInfo += this.email + "\n";
        personalInfo += this.phoneNumber + "\n";
        personalInfo += this.companyName;
        return personalInfo;
    }

    public String viewSalesHistory() {
        String history = "";
        for (Log sellLog : this.historyOfTransaction) {
            history += sellLog.getId() + "\\*\\";
            history += sellLog.getReceiverUserName() + "\\*\\";
            history += sellLog.getPrice() + "\\*\\";
            history += '\n';
        }
        return history;
    }

    public String viewAllProducts() {
        String products = "";
        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : this.allProducts) {
                products += product.getProductId() + " ";
                products += product.getProductName() + " ";
                products += product.getProductCost() + " ";
                products += '\n';
            }
        }
        return products;
    }

    public String viewProduct(String productID) {
        if (getProductByID(productID) == null) {
            return "The Product Does Not Exist";
        }
        String productInfo = "";
        Product product = getProductByID(productID);
        productInfo += product.getProductId() + "\n";
        productInfo += product.getProductName() + "\n";
        productInfo += product.getProductCompany() + "\n";
        productInfo += product.getProductCost() + "\n";
        productInfo += product.getProductStatus() + "\n";
        productInfo += product.getProductsCategory() + "\n";
        productInfo += product.getNumberOfAvailableProducts() + "\n";
        productInfo += product.getDescription() + "\n";
        productInfo += product.getAverageScore() + "\n";
        productInfo += product.getCostAfterOff() + "\n";
        productInfo += product.getComments() + "\n";
        productInfo += product.getFeaturesOfCategoryThatHas() + "\n";
        return productInfo;

    }

    public Product getProductByID(String productID) {
        if (!hasAnyProduct()) {
            return null;
        }
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID))
                return product;
        }
        return null;
    }

    public boolean productExists(String productId) {
        return getProductByID(productId) != null;
    }

    public Offer getOfferById(String offerId) {
        if (!hasAnyOffer()) {
            return null;
        }
        for (Offer offer : allOffer) {
            if (offer.getOfferId().equals(offerId)) {
                return offer;
            }
        }
        return null;
    }

    public boolean offerExists(String offerId) {
        return getOfferById(offerId) != null;
    }

    public boolean hasAnyProduct() {
        return allProducts != null;
    }

    public boolean hasAnyOffer() {
        return allOffer != null;
    }
}

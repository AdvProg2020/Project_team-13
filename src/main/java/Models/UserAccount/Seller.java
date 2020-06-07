package Models.UserAccount;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
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


    public void removeOffer(Offer offer) {
        if (allOffer != null && !allOffer.isEmpty()) {
            for (Offer offer1 : allOffer) {
                if (offer1.getOfferId().equals(offer.getOfferId())) {
                    allOffer.remove(offer1);
                    return;
                }
            }
        }
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

    public void reduceProductCount(String productID, int count) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID)) {
                product.setNumberOfAvailableProducts(product.getNumberOfAvailableProducts() - count);
            }
        }
    }

    public void addOffer(Offer offer) {
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
        ProductController.getInstance().getAllProductsFromServer();
        for (String product : offer.getProducts()) {
            this.getProductByID(product).setCostAfterOff(this.getProductByID(product).getProductCost() * (1 - (offer.getAmount() / 100)));
        }
    }


    public Product findProductWithID(String productID) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public void removeProduct(String productId) {
        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (product.getProductId().equals(productId)) {
                    allProducts.remove(product);
                    break;
                }
            }
        }
    }

    public void editProduct(Product product) {
        if (allProducts != null) {
            for (Product product1 : this.allProducts) {
                if (product1.getProductId().equals(product.getProductId())) {
                    allProducts.set(allProducts.indexOf(product1), product);
                }
            }
        }
    }

    public void editOffer(Offer newOffer) {
        for (Offer offer : allOffer) {
            if (offer.getOfferId().equals(newOffer.getOfferId())) {
                allOffer.set(allOffer.indexOf(offer), newOffer);
                break;
            }
        }
        for (String product : newOffer.getProducts()) {
            this.getProductByID(product).setCostAfterOff(this.getProductByID(product).getProductCost() * (1 - (newOffer.getAmount() / 100)));
        }

    }


    public boolean productExistsInOtherOffer(String productId) {
        if (sAnyOffer()) {
            return false;
        }
        for (Offer offer : allOffer) {
            for (String product : offer.getProducts()) {
                if (product.equals(productId)) {
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
        String personalInfo = "Seller\n";
        personalInfo += "User name: " + this.username + "\n";
        personalInfo += "First name: " + this.firstName + "\n";
        personalInfo += "Last name: " + this.lastName + "\n";
        personalInfo += "Email: " + this.email + "\n";
        personalInfo += "PhoneNumber: " + this.phoneNumber + "\n";
        personalInfo += "Credit: " + this.credit + "$\n";
        personalInfo += "Company name: " + this.companyName;
        return personalInfo;
    }

    public String viewSalesHistory() {
        StringBuilder history = new StringBuilder();
        for (Log sellLog : this.historyOfTransaction) {
            history.append(sellLog.getId()).append(" ");
            history.append(sellLog.getOtherSideUserName()).append(" ");
            history.append(sellLog.getPrice()).append(" ");
            history.append(sellLog.getDate()).append(" ");
            history.append(sellLog.getReduceCostForOffs()).append(" ");
            for (Product product : allProducts) {
                history.append(product.getProductId()).append(" ").append(product.getProductCost()).append("\n");
            }
            history.append("\n");
        }
        return history.toString();
    }

    public String viewAllProducts() {
        StringBuilder products = new StringBuilder();
        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : this.allProducts) {
                products.append(product.getProductId()).append(" ");
                products.append(product.getProductName()).append(" ");
                products.append(product.getProductCost()).append(" ");
                products.append("\n");
            }
        }
        return products.toString();
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
        if (AnyOffer()) {
            return null;
        }
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID))
                return product;
        }
        return null;
    }

    public boolean productExists(String productId) {
        return getProductByID(productId) == null;
    }

    public Offer getOfferById(String offerId) {
        if (sAnyOffer()) {
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

    public boolean AnyOffer() {
        return allProducts == null || allProducts.isEmpty();
    }

    public boolean sAnyOffer() {
        return allOffer == null;
    }
}

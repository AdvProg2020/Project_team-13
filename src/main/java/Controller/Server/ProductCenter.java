package Controller.Server;

import Controller.Client.CategoryController;
import Controller.Client.MessageController;
import Models.Offer;
import Models.Product.Category;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ProductCenter {

    private String lastProductId;
    private ArrayList<Product> allProducts;
    private static ProductCenter productCenter;

    private ProductCenter() {

    }

    public static ProductCenter getInstance() {
        if (productCenter == null) {
            productCenter = new ProductCenter();
        }
        return productCenter;
    }

    public String getLastProductId() {
        DataBase.getInstance().setLastProductIdFromDataBase();
        return lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }

    public void decreaseProductCount(String productId, int count) {
        Product product = findProductWithID(productId);
        if (product.getNumberOfAvailableProducts() - count > 1) {
            product.setNumberOfAvailableProducts(product.getNumberOfAvailableProducts() - count);
            UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).reduceProductCount(productId, count);
            if (OffCenter.getInstance().findProductWithID(productId) != null) {
                OffCenter.getInstance().findProductWithID(productId).setNumberOfAvailableProducts(OffCenter.getInstance().findProductWithID(productId).getNumberOfAvailableProducts() - count);
            }
        } else {
            allProducts.remove(product);
            UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).removeProduct(productId);
            OffCenter.getInstance().removeProduct(productId);

        }

    }

    public Product findProductWithID(String productId) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public String getProductIdForCreateInProduct() {
        DataBase.getInstance().setLastProductIdFromDataBase();
        this.lastProductId = "@p" + (Integer.parseInt(lastProductId.substring(2)) + 1);
        DataBase.getInstance().replaceProductId(lastProductId);
        return this.lastProductId;
    }

    public void createProductRequest(Product product) {
        Gson gson = new Gson();
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddProduct", gson.toJson(product)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productCreating", "ProductCreating Request has been sent."));
    }

    public Product getProductWithId(String productId) {
        if (allProducts != null) {
            for (Product product : allProducts) {
                if (product.getProductId().equals(productId)) {
                    return product;
                }
            }
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void createProduct(Product product) {
        product.setProductId(getProductIdForCreateInProduct());
        product.setProductStatus(ProductStatus.accepted);
        if (allProducts != null) {
            allProducts.add(product);
        } else {
            allProducts = new ArrayList<>();
            allProducts.add(product);
        }
        UserCenter.getIncstance().addProductToSeller(product);
        CategoryCenter.getIncstance().addProductToCategory(product);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public void deleteProduct(String productId) {
        for (Product product1 : allProducts) {
            if (product1.getProductId().equals(productId)) {
                allProducts.remove(product1);
                UserCenter.getIncstance().removeProductFromSellerProductList(product1);
                CategoryCenter.getIncstance().removeProductFromCategory(product1);
                break;
            }
        }
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public void removeProduct(Product product) {
        for (Product product1 : allProducts) {
            if (product1.getProductId().equals(product.getProductId())) {
                allProducts.remove(product1);
                UserCenter.getIncstance().removeProductFromSellerProductList(product);
                break;
            }
        }
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public void update() {
        DataBase.getInstance().setAllProductsFormDataBase();
    }

    public void updateAllProducts() {
        DataBase.getInstance().setAllProductsFormDataBase();
    }

    public void setAllProducts() {
        DataBase.getInstance().updateAllProducts(new Gson().toJson(this.allProducts));
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void createEditProductRequest(Product product) {
        //

    }

    public void editProduct(Product product) {
        //
    }

    public void addOfferToProduct(String productId, Offer offer) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                product.setOffer(offer);
                DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
                break;
            }
        }

    }
}

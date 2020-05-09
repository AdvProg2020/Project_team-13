package Controller.Server;

import Controller.Client.MessageController;
import Models.Product.Category;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

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

    public String getProductIdForCreateInProduct() {
        DataBase.getInstance().setLastProductIdFromDataBase();
        this.lastProductId = "@p" + (Integer.parseInt(lastProductId.substring(2, lastProductId.length())) + 1);
        DataBase.getInstance().replaceProductId(lastProductId);
        return this.lastProductId;
    }

    public void createProductRequest(Product product, String message) {
        product.setProductId(ProductCenter.getInstance().getProductIdForCreateInProduct());
        Gson gson = new Gson();
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddProduct", gson.toJson(product)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productCreating", "ProductCreating Request has been sent."));

    }

    public void deleteProduct(String productId, String sellerObject) {
        Gson gson = new Gson();
        Seller seller = gson.fromJson(sellerObject, Seller.class);
        if (seller.productExists(productId)) {
            seller.getAllProducts().remove(seller.getProductByID(productId));
            allProducts.remove(seller.getProductByID(productId));
            String updatedProducts = gson.toJson(allProducts);
            DataBase.getInstance().updateAllProducts(updatedProducts);
            ServerController.getInstance().sendMessageToClient(MessageController.getInstance().makeMessage("@removedSuccessful@", "The Product removed Successfully"));
        } else {
            ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("@Error@", "There is no Product with this Id"));
        }
    }

    public void removeProduct(Product product) {
        for (Product product1 : allProducts) {
            if (product1.getProductId().equals(product.getProductId())) {
                allProducts.remove(product1);
                break;
            }

        }
    }

    public void update() {
        DataBase.getInstance().setAllProductsFormDataBase();
    }

    public void updateAllProducts() {
        DataBase.getInstance().setAllProductsFormDataBase();
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }
}

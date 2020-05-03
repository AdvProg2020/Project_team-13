package Controller.Server;

import Controller.Client.MessageController;
import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class ProductCenter {

    private String lastProductId;
    private ArrayList<Product> products;
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
        DataBase.getIncstance().setLastProductIdFromDataBase();
        return lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }

    public String getProductIdForCreateInProduct() {
        DataBase.getIncstance().setLastProductIdFromDataBase();
        this.lastProductId = "@p" + (Integer.parseInt(lastProductId.substring(2, lastProductId.length())) + 1);
        DataBase.getIncstance().replaceProductId(lastProductId);
        return this.lastProductId;
    }

    public void createProductRequest(Product product,String message) {
        boolean productCreated = false;
        for (Category category : CategoryCenter.getIncstance().getAllCategories()) {
            if (category.getName().equals(product.getProductsCategory())) {
                product.setProductId(ProductCenter.getInstance().getProductIdForCreateInProduct());
                product.getSeller().addProduct(product);
                productCreated = true;
                Gson gson = new Gson();
                RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddProduct", gson.toJson(product)));
                ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productCreating", "ProductCreating Request has been sent."));
                break;
            }
        }
        if (!productCreated) {
            ServerController.getIncstance().sendMessageToClient(MessageController.getInstance().makeMessage("Error", "There is no category with this name"));
        }
    }




}

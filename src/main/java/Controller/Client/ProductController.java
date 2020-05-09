package Controller.Client;

import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductController {
    private ArrayList<Product> allProducts;
    private static ProductController productController;

    private ProductController() {
    }

    public static ProductController getInstance() {
        if (productController == null) {
            productController = new ProductController();
        }
        return productController;
    }

    public void addProduct(ArrayList<String> fields, HashMap<String, String> featuresOfCategory, Category category) {
        Product product = new Product(fields.get(0), null, fields.get(1), (Seller) ClientController.getInstance().getCurrentUser()
                , Double.parseDouble(fields.get(3)), category.getName(), fields.get(2), Integer.parseInt(fields.get(4))
                , featuresOfCategory);
        Gson gson = new Gson();
        String product0 = gson.toJson(product);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddProduct", product0));
    }

    /*public void editProduct(String productId, String userName, String field, String newValue){

        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage());
    }*/

    public void removeProduct(String productId, String seller) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("deleteProduct", productId + "/" + seller));
    }

    public void printAllProducts(String json) {
        Type productListType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        allProducts = new Gson().fromJson(json, productListType);
        for (Product product : allProducts) {
            ClientController.getInstance().getCurrentMenu().showMessage(product.viewProduct());
        }
    }

    public void getAllProductsFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllProductsForManager@");
    }

    public void removeProductForManager(String productId) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("@removeProductForManager@", productId));
    }
}

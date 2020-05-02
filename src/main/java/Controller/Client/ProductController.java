package Controller.Client;

import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

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

    public void addProduct(ArrayList<String>fields,ArrayList<String> featuresOfCategory,String category) {
        Product product=new Product(fields.get(0),null,fields.get(1),(Seller) ClientController.getInstance().getCurrentUser()
                ,Double.parseDouble(fields.get(3)),category,fields.get(2),Integer.parseInt(fields.get(4))
                ,featuresOfCategory);
        Gson gson=new Gson();
        String product0=gson.toJson(product);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddProduct",product0));
    }

    public void editProduct(String productId, String userName, String field, String newValue){

    }


}

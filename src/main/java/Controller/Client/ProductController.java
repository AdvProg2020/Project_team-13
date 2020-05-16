package Controller.Client;

import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductController {
    private ArrayList<Product> allProducts,allProductsAfterFilter;
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

    public void printAllProducts() {
        getAllProductsFromServer();
        for (Product product : allProducts) {
            ClientController.getInstance().getCurrentMenu().showMessage(product.viewProduct());
        }
    }

    public void showProductsAfterFilterAndSort() {
        filterProducts();
        ArrayList<Product> allProducts=sortProducts();
        String productsInViewFormat="";
        for (Product product : allProducts) {
            productsInViewFormat+=product.getProductId() + "\t" + product.getProductName() + "\t" + product.getProductCost()+ "\t" + product.getProductStatus().toString() + "\n" ;
        }
        if(allProducts!=null&&!allProducts.isEmpty()) {
            ClientController.getInstance().getCurrentMenu().showMessage(productsInViewFormat.substring(0,productsInViewFormat.length()-1));
        }

    }

    public void filterProducts() {
        allProductsAfterFilter=allProducts;

    }

    public ArrayList<Product> sortProducts() {
        ArrayList<Product> allProductsAfterSort=allProductsAfterFilter;
        return allProductsAfterSort;
    }

    public void updateAllProducts(String json) {
        Type productListType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        allProducts = new Gson().fromJson(json, productListType);
    }

    public void getAllProductsFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllProductsForManager@");
    }

    public void removeProductForManager(String productId) {
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("removeProductForManager", productId));
    }

    public String getTheProductDetails(ArrayList<Product> allProducts){
        String allDetails="";
        for (Product product : allProducts) {
            allDetails+=product.productInfoFor()+"\n\n";
        }
        return allDetails;
    }

    public void makeProductsViewForm() {
//        String productsInviewForm
        for (Product product : allProducts) {

        }
    }
}

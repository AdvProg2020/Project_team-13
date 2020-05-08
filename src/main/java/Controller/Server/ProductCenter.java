package Controller.Server;

import Controller.Client.MessageController;
import Models.Product.Category;
import Models.Product.Product;
import Models.Request;
import Models.UserAccount.Seller;
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

    public void deleteProductForSeller(String productId, String sellerObject){
        Gson gson=new Gson();
        Seller seller=gson.fromJson(sellerObject, Seller.class);
        if(seller.productExists(productId)){
          seller.getAllProducts().remove(seller.getProductByID(productId));
          products.remove(seller.getProductByID(productId));
          // delete the product from category must added
          String updatedProducts=gson.toJson(products);
          DataBase.getIncstance().updateAllProducts(updatedProducts);
          ServerController.getIncstance().sendMessageToClient(MessageController.getInstance().makeMessage("@removedSuccessful@", "The Product removed Successfully"));
        }else{
            ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("@Error@","There is no Product with this Id"));
        }
    }
    public void deleteProductForManager(String productId){
        if(products.contains(getProductById(productId))){
            Gson gson=new Gson();
            products.remove(getProductById(productId));
            products.trimToSize();
            DataBase.getIncstance().updateAllProducts(gson.toJson(products));
            ServerController.getIncstance().sendMessageToClient(MessageController.getInstance().makeMessage("@removedSuccessful@", "The Product removed Successfully"));
        }else{
            ServerController.getIncstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("@Error@","There is no Product with this Id"));
        }
    }

    private Product getProductById(String productId){
        for (Product product : products) {
            if(product.getProductId().equals(productId)){
                return product;
            }
        }
        return null;
    }
}

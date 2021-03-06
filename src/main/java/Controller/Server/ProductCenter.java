package Controller.Server;

import Models.Comment;
import Models.Offer;
import Models.Product.Product;
import Models.Product.ProductStatus;
import Models.Score;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class ProductCenter {

    private String lastProductId;
    private ArrayList<Product> allProducts;
    private static ProductCenter productCenter;

    private ProductCenter() {

    }

    public static ProductCenter getInstance() {
        if (productCenter == null) {
            synchronized (ProductCenter.class) {
                if (productCenter == null) {
                    productCenter = new ProductCenter();
                }
            }
        }
        return productCenter;
    }

    public synchronized void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }

    public synchronized void decreaseProductCount(String productId, int count) {
        Product product = findProductWithID(productId);
        if (product.getNumberOfAvailableProducts() - count >= 0) {
            product.setNumberOfAvailableProducts(product.getNumberOfAvailableProducts() - count);
            UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).reduceProductCount(productId, count);
            if (OffCenter.getInstance().findProductWithID(productId) != null) {
                OffCenter.getInstance().findProductWithID(productId).setNumberOfAvailableProducts(OffCenter.getInstance().findProductWithID(productId).getNumberOfAvailableProducts() - count);
            }
        }
    }

    public synchronized void rating(String json) {
        Score score = new Gson().fromJson(json, Score.class);
        Product product = ProductCenter.getInstance().findProductWithID(score.getProductID());
        product.addScore(score);
        UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).findProductWithID(product.getProductId()).addScore(score);
        UserCenter.getIncstance().findCustomerWithUsername(score.getCustomerID()).findProductWithId(product.getProductId()).addScore(score);
        if (OffCenter.getInstance().findProductWithID(product.getProductId()) != null)
            OffCenter.getInstance().findProductWithID(product.getProductId()).addScore(score);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
        DataBase.getInstance().updateAllOffers(new Gson().toJson(OffCenter.getInstance().getAllOffers()));
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        DataBase.getInstance().updateAllSellers(new Gson().toJson(UserCenter.getIncstance().getAllSeller()));
    }

    public synchronized void commenting(String json) {
        Comment comment = new Gson().fromJson(json, Comment.class);
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("Commenting", new Gson().toJson(comment)));
    }

    public synchronized void addComment(Comment comment, DataOutputStream dataOutputStream) {
        Product product = ProductCenter.getInstance().findProductWithID(comment.getProductId());
        product.addComment(comment);
        UserCenter.getIncstance().findSellerWithUsername(product.getSeller()).findProductWithID(product.getProductId()).addComment(comment);
        if (OffCenter.getInstance().findProductWithID(product.getProductId()) != null)
            OffCenter.getInstance().findProductWithID(product.getProductId()).addComment(comment);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
        DataBase.getInstance().updateAllOffers(new Gson().toJson(OffCenter.getInstance().getAllOffers()));
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        DataBase.getInstance().updateAllSellers(new Gson().toJson(UserCenter.getIncstance().getAllSeller()));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@successfully rating", dataOutputStream);
    }

    public Product findProductWithID(String productId) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public synchronized String getProductIdForCreateInProduct() {
        DataBase.getInstance().setLastProductIdFromDataBase();
        this.lastProductId = "@p" + (Integer.parseInt(lastProductId.substring(2)) + 1);
        DataBase.getInstance().replaceProductId(lastProductId);
        return this.lastProductId;
    }

    public synchronized void createProductRequest(Product product, DataOutputStream dataOutputStream) {
        Gson gson = new Gson();
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("AddProduct", gson.toJson(product)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("productCreating", "Product Creating Request has been sent."), dataOutputStream);
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

    public synchronized void createProduct(Product product) {
        product.setProductId(getProductIdForCreateInProduct());
        product.setProductStatus(ProductStatus.accepted);
        if (allProducts == null) {
            allProducts = new ArrayList<>();
        }
        allProducts.add(product);
        UserCenter.getIncstance().addProductToSeller(product);
        CategoryCenter.getIncstance().addProductToCategory(product);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public synchronized void deleteProduct(String productId, DataOutputStream dataOutputStream) {
        for (Product product1 : allProducts) {
            if (product1.getProductId().equals(productId)) {
                allProducts.remove(product1);
                OffCenter.getInstance().removeProduct(product1.getProductId(), dataOutputStream);
                UserCenter.getIncstance().removeProductFromSellerProductList(product1);
                CategoryCenter.getIncstance().removeProductFromCategory(product1);
                break;
            }
        }
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public synchronized void removeProduct(Product product, DataOutputStream dataOutputStream) {
        for (Product product1 : allProducts) {
            if (product1.getProductId().equals(product.getProductId())) {
                allProducts.remove(product1);
                OffCenter.getInstance().removeProduct(product1.getProductId(), dataOutputStream);
                UserCenter.getIncstance().removeProductFromSellerProductList(product1);
                CategoryCenter.getIncstance().removeProductFromCategory(product1);
                break;
            }
        }
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public synchronized void updateAllProducts() {
        DataBase.getInstance().setAllProductsFormDataBase();
    }

    public synchronized void setAllProducts() {
        DataBase.getInstance().updateAllProducts(new Gson().toJson(this.allProducts));
    }

    public synchronized void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public synchronized void createEditProductRequest(Product product, DataOutputStream dataOutputStream) {
        getProductWithId(product.getProductId()).setProductStatus(ProductStatus.editing);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("EditProduct", new Gson().toJson(product)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful", "Product edit request has been send to the manager."), dataOutputStream);
    }

    public synchronized void createDeleteProductRequest(Product product, DataOutputStream dataOutputStream) {
        getProductWithId(product.getProductId()).setProductStatus(ProductStatus.editing);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
        RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("deleteRequest", new Gson().toJson(product)));
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Successful", "Product delete request has been send to the manager."), dataOutputStream);
    }

    public synchronized void editProduct(Product product) {
        product.setProductStatus(ProductStatus.accepted);
        if (allProducts != null) {
            allProducts.set(allProducts.indexOf(ProductCenter.getInstance()
                    .getProductWithId(product.getProductId())), product);
        } else {
            allProducts = new ArrayList<>();
            allProducts.add(product);
        }
        product.setProductStatus(ProductStatus.accepted);
        UserCenter.getIncstance().editProductInSeller(product);
        CategoryCenter.getIncstance().editProductInCategory(product);
        CategoryCenter.getIncstance().addProductToCategory(product);
        DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
    }

    public synchronized void addCommercialRequest(String productId, DataOutputStream dataOutputStream) {
        updateAllProducts();
        for (Product product : allProducts) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                UserCenter.getIncstance().reduceSellerCreditForAnAdd(product);
                RequestCenter.getIncstance().addRequest(RequestCenter.getIncstance().makeRequest("Commercial", new Gson().toJson(product)));
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Product commercialized request has been sended."), dataOutputStream);
            }
        }
    }

    public synchronized void addOfferToProduct(String productId, Offer offer) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId)) {
                product.setOffer(offer);
                DataBase.getInstance().updateAllProducts(new Gson().toJson(allProducts));
                break;
            }
        }

    }


}

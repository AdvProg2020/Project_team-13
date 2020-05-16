package Controller.Server;

import Models.BuyLog;
import Models.DiscountCode;
import Models.Product.Cart;
import Models.Product.Product;
import Models.ReceivingStatus;
import Models.SellLog;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class CartCenter {
    private static CartCenter cartCenter;
    String lastLogId="";

    public String getLastLogId() {
        return lastLogId;
    }

    public void setLastLogId(String lastLogId) {
        this.lastLogId = lastLogId;
    }

    private CartCenter() {
    }
    public String makeLogID() {
        lastLogId = "@l" + (Integer.parseInt(lastLogId.substring(2, 7)) + 1);
        DataBase.getInstance().replaceRequestId(lastLogId);
        return lastLogId;
    }
    public static CartCenter getInstance() {
        if (cartCenter == null) {
            cartCenter = new CartCenter();
        }
        return cartCenter;
    }
    public void buyProduct(String productID,int count){

    }
    public void pay(Cart cart){
        Customer customer=UserCenter.getIncstance().findCustomerWithUsername(cart.getCustomerID());
        double price=cart.getTotalPrice(),reducedPrice=0;
        DiscountCode discountCode=cart.getDiscountCode();
        if(discountCode!=null){
            if(price * ((double) discountCode.getDiscountPercent() / 100)<discountCode.getMaxDiscountAmount()) {
                reducedPrice=price * ((double) discountCode.getDiscountPercent() / 100);
                price -= reducedPrice;
            }else {
                reducedPrice=discountCode.getMaxDiscountAmount();
                price-=reducedPrice;

            }
        }
        if(customer.getCredit()-price>=0){
            if(discountCode!=null) {
                DiscountCodeCenter.getIncstance().usedDiscountCode(discountCode.getDiscountCodeID(), customer.getUsername());
            }
            ArrayList<String> sellers=new ArrayList<>();
            for (Product product : cart.getAllproduct()) {
                ProductCenter.getInstance().decreaseProductCount(product.getProductId(),cart.getCountOfEachProduct().get(product.getProductId()));
                if(!sellers.contains(product.getSeller())){
                    sellers.add(product.getSeller());
                }
            }
            String sellerAndProducts="";
            for (String seller : sellers) {
                ArrayList<Product> allProducts=new ArrayList<>();
                sellerAndProducts+=seller+": ";
                for (Product product : cart.getAllproduct()) {
                    if(seller.equals(product.getSeller())) {
                       allProducts.add(product);
                       sellerAndProducts+=product.getProductId()+" ";
                    }
                }
                sellerAndProducts+="\n";
                SellLog sellLog = new SellLog(makeLogID(), new Date(),seller,customer.getUsername(),allProducts, ReceivingStatus.DeliveredToThePost, reducedPrice);
                UserCenter.getIncstance().findSellerWithUsername(seller).addLog(sellLog);
            }
            BuyLog buyLog =new BuyLog(makeLogID(),price,new Date(),customer.getUsername(),sellerAndProducts,cart.getAllproduct(),ReceivingStatus.DeliveredToThePost,reducedPrice);
            customer.addLog(buyLog);
            DataBase.getInstance().updateAllSellers(new Gson().toJson(UserCenter.getIncstance().getAllSeller()));
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
            DataBase.getInstance().updateAllProducts(new Gson().toJson(ProductCenter.getInstance().getAllProducts()));
            DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(DiscountCodeCenter.getIncstance().getAllDiscountCodes()));
            DataBase.getInstance().updateAllOffers(new Gson().toJson(OffCenter.getInstance().getAllOffers()));
            ServerController.getInstance().sendMessageToClient("@Successful@Successfully purchase");
        }else {
            ServerController.getInstance().sendMessageToClient("@Error@You don't have enough credit");
        }
    }
}

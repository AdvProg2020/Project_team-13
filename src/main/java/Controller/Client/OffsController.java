package Controller.Client;

import Models.Offer;
import Models.OfferStatus;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class OffsController {
    private static OffsController offsController;
    private ArrayList<Offer> allOffs;

    public OffsController() {
    }

    public static OffsController getInstance(){
        if(offsController==null){
            offsController=new OffsController();
        }
        return offsController;
    }
    public void  printAllOffs(Seller seller){
        if(!seller.getAllOffer().isEmpty()) {
            String str="";
            for (Offer offer : seller.getAllOffer()) {
                str+=offer.toStringForSummery();
            }
            System.out.println(str+'\n');
        }else{
            System.out.println("There is no Offer for this seller");
        }
    }
    public void printOffById(Seller seller, String offerId){
        if(seller.offerExists(offerId)){
            String str="";
            str+=seller.getOfferById(offerId).toString();
            System.out.println(str+'\n');
        }else {
            System.out.println("There is no Offer With This Id");
        }
    }
    public void editOff(Offer offer){
      ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("editOffer", new Gson().toJson(offer)));
    }
    public void addOff(double amount, double maxDiscountAmount, ArrayList<Product> allProducts, Date startDate, Date endDate){
        Gson gson=new Gson();
        Offer offer=new Offer(amount, (Seller)ClientController.getInstance().getCurrentUser(), allProducts, maxDiscountAmount, startDate, endDate);
        String offerJson=gson.toJson(offer);
        ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("AddOffer",offerJson));
    }
    public void viewAllProducts(Offer offer){
        for (Product product : offer.getProducts()) {
            System.out.println(product.viewProduct());
        }
    }
}

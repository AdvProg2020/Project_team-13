package Controller.Server;

import Models.DiscountCode;
import Models.Request;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DiscountCodeCenter {
    private static DiscountCodeCenter discountCodeCenter;
    private ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String lastDiscountCodeID = "";

    private DiscountCodeCenter() {

    }

    public static DiscountCodeCenter getIncstance() {
        if (discountCodeCenter == null) {
            discountCodeCenter = new DiscountCodeCenter();
        }
        return discountCodeCenter;
    }

    public void setAllDiscountCodes(ArrayList<DiscountCode> allDiscountCodes) {
        this.allDiscountCodes = allDiscountCodes;
    }

    public void createDiscountCode(String json){
        DiscountCode discountCode=new Gson().fromJson(json,DiscountCode.class);
        discountCode.setDiscountCodeID(makeCode());
        allDiscountCodes.add(discountCode);
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            UserCenter.getIncstance().findCustomerWithUsername(username).addDiscountCode(discountCode);
        }
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        ServerController.getInstance().sendMessageToClient("@Successful@discount code with code:"+lastDiscountCodeID+" successfully created");
    }

    public void setLastDiscountCodeID(String lastDiscountCodeID) {
        this.lastDiscountCodeID = lastDiscountCodeID;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    private String makeCode() {
        for (int i = 6; i >= 0; i--) {
            char character = lastDiscountCodeID.charAt(i);
            if (character < 'z') {
                lastDiscountCodeID = lastDiscountCodeID.substring(0, i) + (++character) + lastDiscountCodeID.substring(i + 1, 7);
                break;
            } else {
                lastDiscountCodeID = lastDiscountCodeID.substring(0, i) + 'a' + lastDiscountCodeID.substring(i + 1, 7);
            }
        }
        DataBase.getInstance().replaceDiscountCodeId(lastDiscountCodeID);
        return lastDiscountCodeID;
    }
}

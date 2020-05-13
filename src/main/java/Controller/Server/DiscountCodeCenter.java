package Controller.Server;

import Models.DiscountCode;
import Models.Request;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.net.UnknownServiceException;
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

    public void editDiscountCode(DiscountCode discountCode){
        DiscountCode oldDiscountCode=findDiscountCodeWithThisId(discountCode.getDiscountCodeID());
        int index=allDiscountCodes.indexOf(oldDiscountCode);
        allDiscountCodes.remove(oldDiscountCode);
        allDiscountCodes.add(index,discountCode);
        boolean customeradded=false;
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            Customer customer=UserCenter.getIncstance().findCustomerWithUsername(username);
            int flag=0;
            for (DiscountCode code : customer.getAllDiscountCodes()) {
                if(code.getDiscountCodeID().equals(discountCode.getDiscountCodeID())){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                customeradded=true;
                customer.addDiscountCode(discountCode);
            }
        }
        if(customeradded){
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        }
        ServerController.getInstance().sendMessageToClient("@Successful@discount code successfully edited");
    }
    public void removeDiscountCode(String code){
        DiscountCode discountCode=findDiscountCodeWithThisId(code);
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            Customer customer=UserCenter.getIncstance().findCustomerWithUsername(username);
            if(customer!=null) {
                customer.removeDiscountCode(code);
            }
        }
        allDiscountCodes.remove(discountCode);
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        ServerController.getInstance().sendMessageToClient("@Successful@discount code successfully removed");
    }
    public DiscountCode findDiscountCodeWithThisId(String codeId){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountCodeID().equals(codeId)){
                return discountCode;
            }
        }
        return null;
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

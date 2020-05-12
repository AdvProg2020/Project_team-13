package Controller.Client;

import Models.DiscountCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DiscountController {
    private static DiscountController discountController;
    private ArrayList<DiscountCode> allDiscountCodes=new ArrayList<>();
    private DiscountController() {
    }

    public static DiscountController getInstance() {
        if (discountController == null) {
            discountController = new DiscountController();
        }
        return discountController;
    }
    public void createDiscountCode(DiscountCode discountCode){
        ClientController.getInstance().sendMessageToServer("@createDiscountCode@"+new Gson().toJson(discountCode));
    }
    public void getAllDiscountCodesFromServer() {
        ClientController.getInstance().sendMessageToServer("@getAllDiscountCodes@");
    }

    public DiscountCode findDiscountCodeWithThisId(String codeId){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountCodeID().equals(codeId)){
                return discountCode;
            }
        }
        return null;
    }
    public void viewDiscountCode(String code){
        DiscountCode discountCode=findDiscountCodeWithThisId(code);
        if(discountCode!=null){
            ClientController.getInstance().getCurrentMenu().showMessage(discountCode.view());
        }else{
            ClientController.getInstance().getCurrentMenu().printError("there is no discount code with this code");
        }
    }
    public void printAllDiscountCodes(String json) {
        Type discountCodeListType = new TypeToken<ArrayList<DiscountCode>>() {
        }.getType();
        allDiscountCodes = new Gson().fromJson(json, discountCodeListType);
        String showAllDiscountCodes = "";
        for (DiscountCode discountCode :allDiscountCodes ) {
            showAllDiscountCodes += discountCode.getDiscountCodeID() + " " +discountCode.getDiscountPercent()+"% "+ discountCode.getStartTime()+" "+discountCode.getEndTime() + "\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllDiscountCodes);
    }
}

package Controller.Client;

import Models.DiscountCode;
import com.google.gson.Gson;

public class DiscountController {
    private static DiscountController discountController;

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
}

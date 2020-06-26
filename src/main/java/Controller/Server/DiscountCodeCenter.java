package Controller.Server;

import Models.DiscountCode;
import Models.UserAccount.Customer;
import com.google.gson.Gson;

import java.util.*;

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

    public void createDiscountCode(String json) {
        DiscountCode discountCode = new Gson().fromJson(json, DiscountCode.class);
        discountCode.setDiscountCodeID(makeCode());
        allDiscountCodes.add(discountCode);
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            UserCenter.getIncstance().findCustomerWithUsername(username).addDiscountCode(discountCode);
        }
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        ServerController.getInstance().sendMessageToClient("@Successful@discount code with code:" + lastDiscountCodeID + "\nsuccessfully created");
    }

    public void createDiscountCodeForGift(DiscountCode discountCode) {
        discountCode.setDiscountCodeID(makeCode());
        allDiscountCodes.add(discountCode);
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            UserCenter.getIncstance().findCustomerWithUsername(username).addDiscountCode(discountCode);
        }
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
    }

    public void setLastDiscountCodeID(String lastDiscountCodeID) {
        this.lastDiscountCodeID = lastDiscountCodeID;
    }

    public void makeDiscountCodeForRandomCustomer() {
        Random random = new Random();
        String username = UserCenter.getIncstance().getAllCustomer().get(random.nextInt(UserCenter.getIncstance().getAllCustomer().size())).getUsername();
        Date endDate = new Date();
        endDate.setYear(Calendar.getInstance().getTime().getYear() + 1);
        ArrayList<String> alluser = new ArrayList<>();
        alluser.add(username);
        HashMap<String, Integer> maxusingTime = new HashMap<String, Integer>();
        maxusingTime.put(username, 1);
        DiscountCode discountCodeForGift = new DiscountCode(new Date(), endDate, alluser, 90, 20000, maxusingTime, maxusingTime);
        DiscountCodeCenter.getIncstance().createDiscountCodeForGift(discountCodeForGift);
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public void editDiscountCode(DiscountCode discountCode) {
        DiscountCode oldDiscountCode = findDiscountCodeWithThisId(discountCode.getDiscountCodeID());
        int index = allDiscountCodes.indexOf(oldDiscountCode);
        allDiscountCodes.set(index, discountCode);
        boolean customeradded = false;
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            Customer customer = UserCenter.getIncstance().findCustomerWithUsername(username);
            int flag = 0;
            for (DiscountCode code : customer.getAllDiscountCodes()) {
                if (code.getDiscountCodeID().equals(discountCode.getDiscountCodeID())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                customeradded = true;
                customer.addDiscountCode(discountCode);
            }
        }
        if (customeradded) {
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        }
        ServerController.getInstance().sendMessageToClient("@Successful@discount code successfully edited");
    }

    public void removeDiscountCode(String code) {
        DiscountCode discountCode = findDiscountCodeWithThisId(code);
        for (String username : discountCode.getAllUserAccountsThatHaveDiscount()) {
            Customer customer = UserCenter.getIncstance().findCustomerWithUsername(username);
            if (customer != null) {
                customer.removeDiscountCode(code);
            }
        }
        allDiscountCodes.remove(discountCode);
        DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
        DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@discount code successfully removed");
    }

    public void usedDiscountCode(String code, String username) {
        DiscountCode discountCode = findDiscountCodeWithThisId(code);
        if (discountCode != null) {
            if (discountCode.getRemainingTimesForEachCustomer().get(username) > 1) {
                discountCode.usedOneTime(username);
                UserCenter.getIncstance().findCustomerWithUsername(username).useDiscountCode(code);
            } else {
                removeDiscountCode(code);
            }
            allDiscountCodes.remove(discountCode);
            DataBase.getInstance().updateAllDiscountCode(new Gson().toJson(allDiscountCodes));
            DataBase.getInstance().updateAllCustomers(new Gson().toJson(UserCenter.getIncstance().getAllCustomer()));
        }
    }

    public DiscountCode findDiscountCodeWithThisId(String codeId) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getDiscountCodeID().equals(codeId)) {
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

    public void passTime() {
        Calendar calendar = Calendar.getInstance();
        DataBase.getInstance().setAllDiscountCodesListFromDateBase();
        for (DiscountCode discountCode : allDiscountCodes) {
            if (calendar.getTime().after(discountCode.getEndTime())) {
                removeDiscountCode(discountCode.getDiscountCodeID());
                break;
            }
        }
        if ((calendar.get(Calendar.DAY_OF_MONTH) == 28 && calendar.get(Calendar.MONTH) == 8
                && calendar.get(Calendar.HOUR) == 7 && calendar.get(Calendar.MINUTE) == 33)
                ||( calendar.getTime().getDay() == calendar.getTime().getMonth() + 1 &&
                calendar.get(Calendar.HOUR) == 7 && calendar.get(Calendar.MINUTE) == 33)) {
            makeDiscountCodeForRandomCustomer();
        }
    }

}

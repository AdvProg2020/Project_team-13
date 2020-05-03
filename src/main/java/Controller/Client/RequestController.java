package Controller.Client;

import Models.Request;
import Models.RequestType;
import Models.UserAccount.Customer;
import Models.UserAccount.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RequestController {
    private static RequestController requestController;
    private ArrayList<Request> allRequests = new ArrayList<>();

    private RequestController() {
    }

    public static RequestController getInstance() {
        if (requestController == null) {
            requestController = new RequestController();
        }
        return requestController;
    }

    public void getAllRequestsFromServer() {
        ClientController.getInstance().sendMessageToServer("getAllRequests");
    }

    public void printAllRequests(String json) {
        Type requestListType = new TypeToken<ArrayList<Request>>() {
        }.getType();
        allRequests = new Gson().fromJson(json, requestListType);
        String showAllRequests = "";
        for (Request request : allRequests) {
            showAllRequests += request.getRequestId() + " " + request.getType() + "\n";
        }
        ClientController.getInstance().getCurrentMenu().showMessage(showAllRequests);
    }

    public void viewRequestDetail(String requestId) {
        String viewDetail = "";
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                if (request.getType() == RequestType.sellerRegister) {
                    viewDetail = requestId + " " + request.getType() + " " + new Gson().fromJson(request.getDetails(), Seller.class).viewPersonalInfo();
                }
                ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().printError("there is no request with this id");
    }
    public void acceptRequest(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                ClientController.getInstance().sendMessageToServer("@acceptRequest@" + requestId);
                allRequests.remove(request);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().printError("there is no request with this id");
    }
}

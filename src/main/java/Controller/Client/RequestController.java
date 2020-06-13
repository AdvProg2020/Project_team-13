package Controller.Client;

import Models.*;
import Models.Product.Product;
import Models.UserAccount.Seller;
import View.MessageKind;
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
        ClientController.getInstance().sendMessageToServer("@getAllRequests@");
    }


    public void printAllRequests(String json) {
        Type requestListType = new TypeToken<ArrayList<Request>>() {
        }.getType();
        allRequests = new Gson().fromJson(json, requestListType);
        String showAllRequests = "";
        if (allRequests != null && !allRequests.isEmpty()) {
            for (Request request : allRequests) {
                showAllRequests += request.getRequestId() + " " + request.getType() + "\n";
            }
        }
     //   ClientController.getInstance().getCurrentMenu().showMessage(showAllRequests);
    }

    public void viewRequestDetail(String requestId) {
        String viewDetail = "";
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                switch (request.getType()) {
                    case addOff:
                    case editOff:
                        viewDetail = "requestId: " + requestId + "\nrequestType: " + request.getType() + "\n" + new Gson().fromJson(request.getDetails(), Offer.class).toString();
                        break;
                    case sellerRegister:
                        viewDetail = "requestId: " + requestId + "\nrequestType: " + request.getType() + "\n" + new Gson().fromJson(request.getDetails(), Seller.class).viewPersonalInfo();
                        break;
                    case addProduct:
                    case EditProduct:
                        viewDetail = "requestId: " + requestId + "\nrequestType: " + request.getType() + "\n" + new Gson().fromJson(request.getDetails(), Product.class).productInfoFor();
                        break;
                    case scoring:
                        viewDetail =  "requestId: " +requestId + "\nrequestType: " + request.getType() + "\n" + new Gson().fromJson(request.getDetails(), Score.class).toString();
                        break;
                    case commenting:
                        viewDetail = "requestId: " + requestId + "\nrequestType: " + request.getType() + "\n" + new Gson().fromJson(request.getDetails(), Comment.class).toString();
                        break;
                    default:
                        break;
                }
         //       ClientController.getInstance().getCurrentMenu().showMessage(viewDetail);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().showMessage("there is no request with this id", MessageKind.ErrorWithoutBack);
    }

    public void acceptRequest(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                ClientController.getInstance().sendMessageToServer("@acceptRequest@" + requestId);
                allRequests.remove(request);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().showMessage("there is no request with this id", MessageKind.ErrorWithoutBack);
    }

    public void declineRequest(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                ClientController.getInstance().sendMessageToServer(MessageController.getInstance().makeMessage("declineRequest", requestId));
                allRequests.remove(request);
                return;
            }
        }
        ClientController.getInstance().getCurrentMenu().showMessage("there is no request with this id", MessageKind.ErrorWithoutBack);
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }
}

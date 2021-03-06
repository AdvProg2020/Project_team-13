package Controller.Client;

import Models.Comment;
import Models.Offer;
import Models.Product.Product;
import Models.Request;
import Models.Score;
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
        System.out.println(json);
        allRequests = new Gson().fromJson(json, requestListType);
        StringBuilder showAllRequests = new StringBuilder();
        if (allRequests != null && !allRequests.isEmpty()) {
            for (Request request : allRequests) {
                showAllRequests.append(request.getRequestId()).append(" ").append(request.getType()).append("\n");
            }
        }
    }

    public String viewRequestDetail(String requestId) {
        Request selectedRequest = null;
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                selectedRequest = request;
            }
        }
        assert selectedRequest != null;
        switch (selectedRequest.getType()) {
            case addOff:
            case editOff:
                return "requestId: " + requestId + "\nrequestType: " + selectedRequest.getType() + "\n" + new Gson().fromJson(selectedRequest.getDetails(), Offer.class).toString();
            case sellerRegister:
                return "requestId: " + requestId + "\nrequestType: " + selectedRequest.getType() + "\n" + new Gson().fromJson(selectedRequest.getDetails(), Seller.class).viewPersonalInfo();
            case addProduct:
            case EditProduct:
            case commercial:
                return "requestId: " + requestId + "\nrequestType: " + selectedRequest.getType() + "\n" + new Gson().fromJson(selectedRequest.getDetails(), Product.class).productInfoFor();
            case scoring:
                return "requestId: " + requestId + "\nrequestType: " + selectedRequest.getType() + "\n" + new Gson().fromJson(selectedRequest.getDetails(), Score.class).toString();
            case commenting:
                return "requestId: " + requestId + "\nrequestType: " + selectedRequest.getType() + "\n" + new Gson().fromJson(selectedRequest.getDetails(), Comment.class).toString();
            default:
                return null;

        }
    }

    public void acceptRequest(String requestId) {
        System.out.println("aaaaaaaaaaaaaaaa");
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                System.out.println("bbbbbbbbbbbbbbb");
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

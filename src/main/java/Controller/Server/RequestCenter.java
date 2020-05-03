package Controller.Server;

import Models.Request;
import Models.RequestStatus;
import Models.RequestType;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RequestCenter {
    private static RequestCenter requestCenter;
    private ArrayList<Request> allRequests = new ArrayList<>();
    private String lastRequestID = "@r100000";

    private RequestCenter() {

    }

    public static RequestCenter getIncstance() {
        if (requestCenter == null) {
            requestCenter = new RequestCenter();
        }
        return requestCenter;
    }

    public void addRequest(Request request) {
        allRequests.add(request);
    }

    public String makeRequestID() {
        lastRequestID = "@r" + (Integer.parseInt(lastRequestID.substring(2, 8)) + 1);
        return lastRequestID;
    }

    public Request makeRequest(String type, String details) {
        Request request = new Request(RequestType.sellerRegister, RequestStatus.onReview, makeRequestID(), details);
        return request;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public void acceptRequest(String requestID) {
        Request request = findRequestWithID(requestID);
        if (request.getType() == RequestType.sellerRegister) {
            acceptSellerRegisterRequest(request);
        }
    }

    public void acceptSellerRegisterRequest(Request request) {
        if (UserCenter.getIncstance().canAcceptSellerRegister(new Gson().fromJson(request.getDetails(), Seller.class).getUsername())) {
            allRequests.remove(request);
            ServerController.getIncstance().sendMessageToClient("@Successful@" + "request accepted successfully");
        }
    }

    public Request findRequestWithID(String requestID) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestID)) {
                return request;
            }
        }
        return null;
    }
}

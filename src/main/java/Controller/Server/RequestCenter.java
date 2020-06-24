package Controller.Server;

import Models.*;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RequestCenter {
    private static RequestCenter requestCenter;
    private ArrayList<Request> allRequests = new ArrayList<>();
    private String lastRequestID = "";

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
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
    }

    public String makeRequestID() {
        lastRequestID = "@r" + (Integer.parseInt(lastRequestID.substring(2, 7)) + 1);
        DataBase.getInstance().replaceRequestId(lastRequestID);
        return lastRequestID;
    }

    public Request makeRequest(String type, String details) {
        switch (type) {
            case "AcceptSellerAccount":
                return new Request(RequestType.sellerRegister, RequestStatus.onReview, makeRequestID(), details);
            case "AddProduct":
                return new Request(RequestType.addProduct, RequestStatus.onReview, makeRequestID(), details);
            case "AddOffer":
                return new Request(RequestType.addOff, RequestStatus.onReview, makeRequestID(), details);
            case "EditOffer":
                return new Request(RequestType.editOff, RequestStatus.onReview, makeRequestID(), details);
            case "Commenting":
                return new Request(RequestType.commenting, RequestStatus.onReview, makeRequestID(), details);
            case "EditProduct":
                return new Request(RequestType.EditProduct, RequestStatus.onReview, makeRequestID(), details);
            case "deleteRequest":
                return new Request(RequestType.deleteProduct, RequestStatus.onReview, makeRequestID(), details);
            case "Commercial":
                return new Request(RequestType.commercial, RequestStatus.onReview, makeRequestID(), details);
            default:
                return null;
        }
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public void acceptRequest(String requestID) {
        Request request = findRequestWithID(requestID);
        if (request.getType() == RequestType.sellerRegister) {
            acceptSellerRegisterRequest(request);
        } else if (request.getType() == RequestType.addProduct) {
            acceptAddProductRequest(request);
        } else if (request.getType().equals(RequestType.editOff)) {
            acceptEditOfferRequest(request);
        } else if (request.getType().equals(RequestType.addOff)) {
            acceptAddOfferRequest(request);
        } else if (request.getType().equals(RequestType.EditProduct)) {
            acceptEdiProductRequest(request);
        } else if (request.getType().equals(RequestType.commenting)) {
            acceptCommentRequest(request);
        } else if (request.getType().equals(RequestType.deleteProduct)) {
            acceptDeleteProductRequest(request);
        }
    }

    public void acceptAddProductRequest(Request request) {
        allRequests.remove(request);
        ProductCenter.getInstance().createProduct(new Gson().fromJson(request.getDetails(), Product.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully");
    }

    public void acceptCommentRequest(Request request) {
        allRequests.remove(request);
        ProductCenter.getInstance().addComment(new Gson().fromJson(request.getDetails(), Comment.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully");
    }

    public void acceptSellerRegisterRequest(Request request) {
        if (UserCenter.getIncstance().canAcceptSellerRegister(new Gson().fromJson(request.getDetails(), Seller.class).getUsername())) {
            allRequests.remove(request);
            String arrayData = new Gson().toJson(allRequests);
            DataBase.getInstance().updateAllRequests(arrayData);
            ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully");
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

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    public void setLastRequestID(String lastRequestID) {
        this.lastRequestID = lastRequestID;
    }

    public void acceptEditOfferRequest(Request request) {
        allRequests.remove(request);
        OffCenter.getInstance().editOffer(new Gson().fromJson(request.getDetails(), Offer.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"));
    }

    public void acceptAddOfferRequest(Request request) {
        allRequests.remove(request);
        OffCenter.getInstance().createNewOff(new Gson().fromJson(request.getDetails(), Offer.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"));
    }

    public void acceptEdiProductRequest(Request request) {
        Product product = new Gson().fromJson(request.getDetails(), Product.class);
        for (Product product1 : ProductCenter.getInstance().getAllProducts()) {
            if (product1.getProductId().equals(product.getProductId())) {
                allRequests.remove(request);
                ProductCenter.getInstance().editProduct(product);
                String arrayData = new Gson().toJson(allRequests);
                DataBase.getInstance().updateAllRequests(arrayData);
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"));
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no product with this Id."));
    }

    public void acceptDeleteProductRequest(Request request) {
        Product product = new Gson().fromJson(request.getDetails(), Product.class);
        for (Product product1 : ProductCenter.getInstance().getAllProducts()) {
            if (product1.getProductId().equals(product.getProductId())) {
                allRequests.remove(request);
                ProductCenter.getInstance().deleteProduct(product.getProductId());
                String arrayData = new Gson().toJson(allRequests);
                DataBase.getInstance().updateAllRequests(arrayData);
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"));
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no product with this Id."));
    }

    public void declineRequest(String requestId) {
        Request request = findRequestWithID(requestId);
        allRequests.remove(request);
        if (request.getType().equals(RequestType.addOff) || request.getType().equals(RequestType.editOff)) {
            OffCenter.getInstance().setProductStatusForOffer(new Gson().fromJson(request.getDetails(), Offer.class));
        }
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request declined successfully"));
    }
}
